package com.mapr.objectpools.controller;

import com.mapr.objectpools.model.*;
import com.mapr.objectpools.service.MaprService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MapRRestWrapper {

    @Autowired
    List<Cluster> clusters;

    @Autowired
    MaprService maprService;

    @RequestMapping("/rest/tree")
    TreeNode getFoo() {
        TreeNode tree = new TreeNode("/mapr");
        for(Cluster cluster : clusters) {
            TreeNode clusterNode = new TreeNode("/" + cluster.toString());
            tree.addChild(clusterNode);

            for(VolumeInfo vi : maprService.volumeList(cluster.toString())){
                TreeNode vol = new TreeNode( vi.getVolumeName() , vi.getTierLocalPct(), "volume");
                vol.setId( vi.getVolumeName() );
                TreeNode tier = new TreeNode( vi.getTierName(), vi.getTierPurgedPct(), "object-pool" );
                tier.setId( vi.getVolumeName() + "_" + vi.getTierName() );

                vol.addChild(tier);
                clusterNode.addChild(vol);
            }
        }
        return tree;
    }

    @RequestMapping("/rest/tree/update")
    Map<String, Double> foo() {
        Map<String, Double> update = new HashMap<>();
        for(Cluster cluster : clusters) {
            for(VolumeInfo vi : maprService.volumeList(cluster.toString())){
                update.put(vi.getVolumeName(), vi.getTierLocalPct());
                update.put(vi.getVolumeName() + "_" + vi.getTierName(), vi.getTierPurgedPct());
            }
        }
        return update;
    }

    @RequestMapping("/rest/volumes")
    List<TierDetail> tierDetails() {
        return maprService.tierDetails("");
    }

    @RequestMapping("/rest/context/hot/{volumeName}")
    ContextHot contextHot(@PathVariable("volumeName") String volumeName) {
        return maprService.contextHot("", volumeName);
    }

    @RequestMapping("/rest/context/cold/{volumeName}")
    ContextCold contextCold(@PathVariable("volumeName") String volumeName) {
        return maprService.contextCold("", volumeName);
    }
}
