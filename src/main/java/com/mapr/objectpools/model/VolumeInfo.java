package com.mapr.objectpools.model;

import java.math.BigDecimal;

public class VolumeInfo {

    private Long volumeId;
    private String volumeName;

    private String tierId;
    private String tierName;
    private long tierLocal;
    private long tierPurged;
    private long tierRecall;

    public Long getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(Long volumeId) {
        this.volumeId = volumeId;
    }

    public String getVolumeName() {
        return volumeName;
    }

    public void setVolumeName(String volumeName) {
        this.volumeName = volumeName;
    }

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

    public long getTierLocal() {
        return tierLocal;
    }

    public void setTierLocal(long tierLocal) {
        this.tierLocal = tierLocal;
    }

    public long getTierPurged() {
        return tierPurged;
    }

    public void setTierPurged(long tierPurged) {
        this.tierPurged = tierPurged;
    }

    public long getTierRecall() {
        return tierRecall;
    }

    public void setTierRecall(long tierRecall) {
        this.tierRecall = tierRecall;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VolumeInfo that = (VolumeInfo) o;

        if (tierLocal != that.tierLocal) return false;
        if (tierPurged != that.tierPurged) return false;
        if (tierRecall != that.tierRecall) return false;
        if (volumeId != null ? !volumeId.equals(that.volumeId) : that.volumeId != null) return false;
        if (volumeName != null ? !volumeName.equals(that.volumeName) : that.volumeName != null) return false;
        if (tierId != null ? !tierId.equals(that.tierId) : that.tierId != null) return false;
        return tierName != null ? tierName.equals(that.tierName) : that.tierName == null;
    }

    @Override
    public int hashCode() {
        int result = volumeId != null ? volumeId.hashCode() : 0;
        result = 31 * result + (volumeName != null ? volumeName.hashCode() : 0);
        result = 31 * result + (tierId != null ? tierId.hashCode() : 0);
        result = 31 * result + (tierName != null ? tierName.hashCode() : 0);
        result = 31 * result + (int) (tierLocal ^ (tierLocal >>> 32));
        result = 31 * result + (int) (tierPurged ^ (tierPurged >>> 32));
        result = 31 * result + (int) (tierRecall ^ (tierRecall >>> 32));
        return result;
    }

    public Double getTierLocalPct() {
        if (( tierLocal == 0 && tierPurged == 0) || (tierLocal + tierPurged == 0) ) {
            return 0d;
        }
        return ((double)tierLocal/(double)(tierLocal + tierPurged)) * 100;
    }

    public Double getTierPurgedPct() {
        if (( tierLocal == 0 && tierPurged == 0) || (tierLocal + tierPurged == 0) ) {
            return 0d;
        }
        return ((double)tierPurged/(double)(tierLocal + tierPurged)) * 100;
    }
}
