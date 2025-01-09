package com.fertilizer.enums.converter;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fertilizer.enums.Status;

import java.util.Date;

public enum CronMasterUpdate {

    UPDATECRONMASTER("0","Cron runs every day","Action: syncClientDataFromDumpTable() class: SyncClientScheduler",
            "Sync new client details daily", Status.ACTIVE);
    private final String cronTitle;
    private final String cronFrequency;
    private final String techDetails;
    private final String remarks;
    private final Status status;


    CronMasterUpdate(String cronTitle, String cronFrequency, String techDetails, String remarks, Status status) {
        this.cronTitle = cronTitle;
        this.cronFrequency = cronFrequency;
        this.techDetails = techDetails;
        this.remarks = remarks;
        this.status = status;
    }

    @JsonValue
    public String getCronTitle() {
        return this.cronTitle;
    }

    @JsonValue
    public String getCronFrequency() {
        return this.cronFrequency;
    }

    @JsonValue
    public String getTechDetails() {
        return this.techDetails;
    }

    @JsonValue
    public String getRemarks() {return this.remarks;}

    @JsonValue
    public Status getStatus() {
        return this.status;
    }



}
