package com.mapr.objectpools.service;

import com.mapr.objectpools.model.*;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MaprService {

    @Value("${api.host}")
    private String host;

    @Value("${api.user}")
    private String user;

    @Value("${api.pass}")
    private String pass;

    private RestTemplate createTemplate() {

        try {
            TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

            SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                    .loadTrustMaterial(null, acceptingTrustStrategy)
                    .build();

            SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

            CloseableHttpClient httpClient = HttpClients.custom()
                    .setSSLSocketFactory(csf)
                    .build();

            HttpComponentsClientHttpRequestFactory requestFactory =
                    new HttpComponentsClientHttpRequestFactory();

            requestFactory.setHttpClient(httpClient);



            RestTemplateBuilder builder = new RestTemplateBuilder();
            return builder.basicAuthorization(user, pass)
                .requestFactory(requestFactory)
                .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /*
     "data":[
         {
             "cluster":{
                 "name":"devel",
                 "secure":false,
                 "ip":"10.0.0.87",
                 "id":"382992728244918795",
                 "nodesUsed":1,
                 "totalNodesAllowed":-1
             },
             "multi_cluster_info":[
                 {
                     "name":"prod",
                     "ip":"10.0.0.88"
                 },
                 {
                     "name":"staging",
                     "ip":"10.0.0.89"
                 }
             ]
         }
     ]
     */
    public List<Cluster> getClusters() {
        ArrayList<Cluster> clusters = new ArrayList<>();

        RestTemplate template = createTemplate();
        String string = template.getForObject(host + "/rest/dashboard/info", String.class);

        JSONObject json = new JSONObject(string);
        if (json.has("data")) {
            JSONArray data = json.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {

                JSONObject dashboardInfo = data.getJSONObject(i);
                String clusterName = dashboardInfo.getJSONObject("cluster").getString("name");
                clusters.add( new Cluster(clusterName) );

                if(dashboardInfo.has("multi_cluster_info")) {
                    JSONArray multi_cluster_info = json.getJSONArray("multi_cluster_info");

                    for (int j = 0; j < multi_cluster_info.length(); j++) {
                        JSONObject clusterInfo = multi_cluster_info.getJSONObject(i);
                        clusters.add( new Cluster(clusterInfo.getString("name")) );
                    }
                }
            }
        }


        return clusters;
    }

    /**
     * Tabelle
     *  Cluster         param
     *  VolumeName      volumeInfos
     *  TierName        tierInfos
     *  TierLocal       volumeInfos
     *  TierPurged      volumeInfos
     *  TierTotal       calc(volumeInfos)
     *  JobType         tierStatus
     *  State           tierStatus
     *  Start           tierStatus
     *  End             tierStatus
     *  Bucket          tierInfos
     * @return
     */
    public List<TierDetail> tierDetails(String cluster) {
        List<TierDetail> tierDetails = new ArrayList<>();

        List<VolumeInfo> volumeInfos = volumeList(cluster);
        List<TierInfo> tierInfos = tierInfoList(cluster);

        for(VolumeInfo vi : volumeInfos) {
            TierDetail tierDetail = new TierDetail();
            tierDetail.setCluster(cluster);

            String volumeName = vi.getVolumeName();
            Long tierLocal = vi.getTierLocal();
            Long tierPurged = vi.getTierPurged();
            Long tierTotal = tierLocal + tierPurged;

            tierDetail.setVolumeName(volumeName);
            tierDetail.setTierLocal(tierLocal);
            tierDetail.setTierPurged(tierPurged);
            tierDetail.setTierTotal(tierTotal);


            TierInfo tierInfo = tierInfos.stream().filter(ti -> ti.getTierid().equals(vi.getTierId())).findFirst().orElse(new TierInfo("","","","","", ""));
            tierDetail.setTierName( tierInfo.getTiername() );
            tierDetail.setBucket( tierInfo.getBucketname() );


            TierStatus tierStatus = volumeTierStatus(cluster, volumeName);
            tierDetail.setJobType( tierStatus.getType() );
            tierDetail.setState( tierStatus.getState() );
            tierDetail.setStart( tierStatus.getStart() );
            tierDetail.setEnd( tierStatus.getEnd() );

            tierDetails.add(tierDetail);
        }

        return tierDetails;
    }

    public List<TierInfo> tierInfoList(String cluster) {
        List<TierInfo> tierInfos = new ArrayList<>();
        RestTemplate template = createTemplate();
        String string = template.getForObject(host + "/rest/tier/list", String.class);

        JSONObject json = new JSONObject(string);

        if (json.has("data")) {
            JSONArray data = json.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {

                JSONObject tierInfo = data.getJSONObject(i);
                String tierId = tierInfo.getString("tierid");
                String tierName = tierInfo.getString("tiername");
                String tiertype = tierInfo.getString("tiertype");

                // TODO: ErasureCoding stuff
                String bucketname = tierInfo.getString("bucketname");
                String region = tierInfo.getString("region");
                String url = tierInfo.getString("url");

                TierInfo tierInfoObj = new TierInfo(tierId, tierName, tiertype, url, bucketname, region);
                tierInfos.add(tierInfoObj);
            }
        }
        return tierInfos;
    }

    /**
     * @param cluster
     * @param name
     * @return
     */
    public TierStatus volumeTierStatus(String cluster, String name) {
        TierStatus tierStatus = new TierStatus("", "", "", "");
        RestTemplate template = createTemplate();
        String string = template.getForObject(host + "/rest/volume/tierjobstatus?name=" + name, String.class);

        JSONObject json = new JSONObject(string);

        if (json.has("data")){
            JSONArray data = json.getJSONArray("data");

            JSONObject tierDataObject = data.getJSONObject(0);

            String tierType = (String) tierDataObject.keys().next();
            JSONObject tierDataObjectData = tierDataObject.getJSONObject(tierType);

            String state = "";
            if(tierDataObjectData.has("state")) {
                state = tierDataObjectData.getString("state");
            }

            String start = "";
            if(tierDataObjectData.has("startTime")) {
                start = tierDataObjectData.getString("startTime");
            }

            String end = "";
            if(tierDataObjectData.has("endTime")) {
                end = tierDataObjectData.getString("endTime");
            }

            tierStatus = new TierStatus(tierType, state, start, end);
        }

        return tierStatus;
    }

    public List<VolumeInfo> volumeList(String cluster) {
        List<TierInfo> tierInfos = tierInfoList(cluster);

        List<VolumeInfo> volumeInfos = new ArrayList<>();

        RestTemplate template = createTemplate();
        String string = template.getForObject(host + "/rest/volume/list", String.class);
        JSONObject json = new JSONObject(string);
        JSONArray data = json.getJSONArray("data");
        for( int i = 0; i < data.length(); i++ ){
            JSONObject volume = data.getJSONObject(i);

            // if tiering is enabled
            if(volume.getJSONObject("tier").getBoolean("enable")) {
                VolumeInfo volumeInfo = new VolumeInfo();
                volumeInfo.setVolumeId(volume.getLong("volumeid"));
                volumeInfo.setVolumeName(volume.getString("volumename"));
                String tierId = volume.getJSONObject("tier").getString("tierId");
                volumeInfo.setTierId(tierId);
                TierInfo tierInfo = tierInfos.stream().filter(ti -> ti.getTierid().equals(tierId)).findFirst().get();
                volumeInfo.setTierName(tierInfo.getTiername());
                volumeInfo.setTierLocal(Long.parseLong(volume.getString("tierLocal")));
                volumeInfo.setTierPurged(Long.parseLong(volume.getString("tierPurged")));
                volumeInfo.setTierRecall(Long.parseLong(volume.getString("tierRecall")));

                volumeInfos.add(volumeInfo);
            }
        }

        return volumeInfos;
    }

    public ContextHot contextHot(String cluster, String volumeName) {
        ContextHot context = new ContextHot();
        context.setVolumeName(volumeName);
        context.setTierLocal(-1l);
        context.setTierRecall(-1l);
        context.setLogicalUsed(-1l);
        context.setTotalUsed(-1l);

        RestTemplate template = createTemplate();
        String volumeInfoResult = template.getForObject(host + "/rest/volume/info?name="+volumeName, String.class);

        JSONObject volumeInfoJson = new JSONObject(volumeInfoResult);

        if(volumeInfoJson.has("data")) {
            JSONObject data = volumeInfoJson.getJSONArray("data").getJSONObject(0);

            Long tierLocal = data.optLong("tierLocal", -1);
            Long tierRecall = data.optLong("tierRecall", -1);

            Long logicalUsed = data.optLong("logicalUsed", -1);
            Long totalUsed = data.optLong("totalused", -1);

            context.setTierLocal(tierLocal);
            context.setTierRecall(tierRecall);
            context.setLogicalUsed(logicalUsed);
            context.setTotalUsed(totalUsed);
        }
        return context;
    }

    public ContextCold contextCold(String cluster, String volumeName) {
        RestTemplate template = createTemplate();
        String volumeInfoResult = template.getForObject(host + "/rest/volume/info?name="+volumeName, String.class);

        JSONObject volumeInfoJson = new JSONObject(volumeInfoResult);
        JSONObject volumeInfoData = volumeInfoJson.getJSONArray("data").getJSONObject(0);

        String tierId = volumeInfoData.getJSONObject("tier").optString("tierId", "");
        Long tierPurged = volumeInfoData.optLong("tierPurged", -1);

        List<TierInfo> tierInfos = tierInfoList(cluster);
        TierInfo tierInfo = tierInfos.stream().filter(ti -> ti.getTierid().equals(tierId)).findFirst().orElse(new TierInfo("","","","","", ""));

        ContextCold context = new ContextCold();

        context.setTierPurged(tierPurged);
        context.setTierId(tierId);
        context.setBucketName(tierInfo.getBucketname());
        context.setRegion(tierInfo.getRegion());
        context.setTierName(tierInfo.getTiername());
        context.setTierType(tierInfo.getTiertype());
        context.setUrl(tierInfo.getUrl());

        return context;
    }
}
