package com.mapr.objectpools.model;

/**
 *  tierid
 *  tiername
 *  tiertype
 *  url
 *  bucketname
 *  region
 *
 *  tierPurged
 *
 * @return
 */
public class ContextCold {

    private String tierId, tierName, tierType, url, bucketName, region;
    private Long tierPurged;

    public String getTierId() {
        return tierId;
    }

    public void setTierId(String tierId) {
        this.tierId = tierId;
    }

    public String getTierName() {
        return tierName;
    }

    public void setTierName(String tierName) {
        this.tierName = tierName;
    }

    public String getTierType() {
        return tierType;
    }

    public void setTierType(String tierType) {
        this.tierType = tierType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Long getTierPurged() {
        return tierPurged;
    }

    public void setTierPurged(Long tierPurged) {
        this.tierPurged = tierPurged;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContextCold that = (ContextCold) o;

        if (tierId != null ? !tierId.equals(that.tierId) : that.tierId != null) return false;
        if (tierName != null ? !tierName.equals(that.tierName) : that.tierName != null) return false;
        if (tierType != null ? !tierType.equals(that.tierType) : that.tierType != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (bucketName != null ? !bucketName.equals(that.bucketName) : that.bucketName != null) return false;
        if (region != null ? !region.equals(that.region) : that.region != null) return false;
        return tierPurged != null ? tierPurged.equals(that.tierPurged) : that.tierPurged == null;
    }

    @Override
    public int hashCode() {
        int result = tierId != null ? tierId.hashCode() : 0;
        result = 31 * result + (tierName != null ? tierName.hashCode() : 0);
        result = 31 * result + (tierType != null ? tierType.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (bucketName != null ? bucketName.hashCode() : 0);
        result = 31 * result + (region != null ? region.hashCode() : 0);
        result = 31 * result + (tierPurged != null ? tierPurged.hashCode() : 0);
        return result;
    }
}
