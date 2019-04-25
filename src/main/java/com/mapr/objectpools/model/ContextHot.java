package com.mapr.objectpools.model;

public class ContextHot {

    private String volumeName;
    private Long tierLocal, tierRecall, logicalUsed, totalUsed;

    public String getVolumeName() {
        return volumeName;
    }

    public void setVolumeName(String volumeName) {
        this.volumeName = volumeName;
    }

    public Long getTierLocal() {
        return tierLocal;
    }

    public void setTierLocal(Long tierLocal) {
        this.tierLocal = tierLocal;
    }

    public Long getTierRecall() {
        return tierRecall;
    }

    public void setTierRecall(Long tierRecall) {
        this.tierRecall = tierRecall;
    }

    public Long getLogicalUsed() {
        return logicalUsed;
    }

    public void setLogicalUsed(Long logicalUsed) {
        this.logicalUsed = logicalUsed;
    }

    public Long getTotalUsed() {
        return totalUsed;
    }

    public void setTotalUsed(Long totalUsed) {
        this.totalUsed = totalUsed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContextHot that = (ContextHot) o;

        if (volumeName != null ? !volumeName.equals(that.volumeName) : that.volumeName != null) return false;
        if (tierLocal != null ? !tierLocal.equals(that.tierLocal) : that.tierLocal != null) return false;
        if (tierRecall != null ? !tierRecall.equals(that.tierRecall) : that.tierRecall != null) return false;
        if (logicalUsed != null ? !logicalUsed.equals(that.logicalUsed) : that.logicalUsed != null) return false;
        return totalUsed != null ? totalUsed.equals(that.totalUsed) : that.totalUsed == null;
    }

    @Override
    public int hashCode() {
        int result = volumeName != null ? volumeName.hashCode() : 0;
        result = 31 * result + (tierLocal != null ? tierLocal.hashCode() : 0);
        result = 31 * result + (tierRecall != null ? tierRecall.hashCode() : 0);
        result = 31 * result + (logicalUsed != null ? logicalUsed.hashCode() : 0);
        result = 31 * result + (totalUsed != null ? totalUsed.hashCode() : 0);
        return result;
    }
}
