package com.mapr.objectpools.model;

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
 */
public class TierDetail {

    private String cluster, volumeName, tierName, jobType, state, start, end, bucket;
    private Long tierLocal, tierPurged, tierTotal;

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getVolumeName() {
        return volumeName;
    }

    public void setVolumeName(String volumeName) {
        this.volumeName = volumeName;
    }

    public String getTierName() {
        return tierName;
    }

    public void setTierName(String tierName) {
        this.tierName = tierName;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public Long getTierLocal() {
        return tierLocal;
    }

    public void setTierLocal(Long tierLocal) {
        this.tierLocal = tierLocal;
    }

    public Long getTierPurged() {
        return tierPurged;
    }

    public void setTierPurged(Long tierPurged) {
        this.tierPurged = tierPurged;
    }

    public Long getTierTotal() {
        return tierTotal;
    }

    public void setTierTotal(Long tierTotal) {
        this.tierTotal = tierTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TierDetail that = (TierDetail) o;

        if (cluster != null ? !cluster.equals(that.cluster) : that.cluster != null) return false;
        if (volumeName != null ? !volumeName.equals(that.volumeName) : that.volumeName != null) return false;
        if (tierName != null ? !tierName.equals(that.tierName) : that.tierName != null) return false;
        if (jobType != null ? !jobType.equals(that.jobType) : that.jobType != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (start != null ? !start.equals(that.start) : that.start != null) return false;
        if (end != null ? !end.equals(that.end) : that.end != null) return false;
        if (bucket != null ? !bucket.equals(that.bucket) : that.bucket != null) return false;
        if (tierLocal != null ? !tierLocal.equals(that.tierLocal) : that.tierLocal != null) return false;
        if (tierPurged != null ? !tierPurged.equals(that.tierPurged) : that.tierPurged != null) return false;
        return tierTotal != null ? tierTotal.equals(that.tierTotal) : that.tierTotal == null;
    }

    @Override
    public int hashCode() {
        int result = cluster != null ? cluster.hashCode() : 0;
        result = 31 * result + (volumeName != null ? volumeName.hashCode() : 0);
        result = 31 * result + (tierName != null ? tierName.hashCode() : 0);
        result = 31 * result + (jobType != null ? jobType.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (end != null ? end.hashCode() : 0);
        result = 31 * result + (bucket != null ? bucket.hashCode() : 0);
        result = 31 * result + (tierLocal != null ? tierLocal.hashCode() : 0);
        result = 31 * result + (tierPurged != null ? tierPurged.hashCode() : 0);
        result = 31 * result + (tierTotal != null ? tierTotal.hashCode() : 0);
        return result;
    }
}
