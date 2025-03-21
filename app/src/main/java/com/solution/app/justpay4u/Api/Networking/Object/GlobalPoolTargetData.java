package com.solution.app.justpay4u.Api.Networking.Object;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GlobalPoolTargetData {
    @SerializedName("levelID")
    @Expose
    private int levelID;
    @SerializedName("target")
    @Expose
    private int target;
    @SerializedName("totalTeam")
    @Expose
    private int totalTeam;
    @SerializedName("remainingTeam")
    @Expose
    private int remainingTeam;

    public int getLevelID() {
        return levelID;
    }

    public void setLevelID(int levelID) {
        this.levelID = levelID;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getTotalTeam() {
        return totalTeam;
    }

    public void setTotalTeam(int totalTeam) {
        this.totalTeam = totalTeam;
    }

    public int getRemainingTeam() {
        return remainingTeam;
    }

    public void setRemainingTeam(int remainingTeam) {
        this.remainingTeam = remainingTeam;
    }
}

