package com.mapr.objectpools.model;

public class TierInfo {
    private String tierid;
    private String tiername;
    private String tiertype;
    private String url;
    private String bucketname;
    private String region;

    public TierInfo(String tierid, String tiername, String tiertype, String url, String bucketname, String region) {
        this.tierid = tierid;
        this.tiername = tiername;
        this.tiertype = tiertype;
        this.url = url;
        this.bucketname = bucketname;
        this.region = region;
    }

    public String getTierid() {
        return tierid;
    }

    public void setTierid(String tierid) {
        this.tierid = tierid;
    }

    public String getTiername() {
        return tiername;
    }

    public void setTiername(String tiername) {
        this.tiername = tiername;
    }

    public String getTiertype() {
        return tiertype;
    }

    public void setTiertype(String tiertype) {
        this.tiertype = tiertype;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBucketname() {
        return bucketname;
    }

    public void setBucketname(String bucketname) {
        this.bucketname = bucketname;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TierInfo tierInfo = (TierInfo) o;

        if (tierid != null ? !tierid.equals(tierInfo.tierid) : tierInfo.tierid != null) return false;
        if (tiername != null ? !tiername.equals(tierInfo.tiername) : tierInfo.tiername != null) return false;
        if (tiertype != null ? !tiertype.equals(tierInfo.tiertype) : tierInfo.tiertype != null) return false;
        if (url != null ? !url.equals(tierInfo.url) : tierInfo.url != null) return false;
        if (bucketname != null ? !bucketname.equals(tierInfo.bucketname) : tierInfo.bucketname != null) return false;
        return region != null ? region.equals(tierInfo.region) : tierInfo.region == null;
    }

    @Override
    public int hashCode() {
        int result = tierid != null ? tierid.hashCode() : 0;
        result = 31 * result + (tiername != null ? tiername.hashCode() : 0);
        result = 31 * result + (tiertype != null ? tiertype.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (bucketname != null ? bucketname.hashCode() : 0);
        result = 31 * result + (region != null ? region.hashCode() : 0);
        return result;
    }
}
