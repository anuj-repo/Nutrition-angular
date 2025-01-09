package com.fertilizer.enums.converter;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CronType {

    SYNCCLIENTDATAFROMTABLE("syncClientDataFromTable",1L,"Sync new client details daily"),
    SENDREMINDEREMAIL("sendReminderEmail", 2L, "send reminder email to sales person for opportunity creation"),
	DELETE_DATA_FROM_ERROR_LOG_TABLE("deleteDataFromErrorLogTable", 3L, "to delete past 2 days data from error log table"),

    SEND_REMINDER_EMAIL_SUB_USER_REGISTRATION("sendReminderEmailSubUserRegistration", 4L, "send reminder email to sub user to register themself before link get expire.");
	
    private final String name;
    private final String remarks;
    private final Long id;

    CronType(String name, Long id, String remarks) {
        this.name = name;
        this.remarks = remarks;
        this.id = id;
    }

    @JsonValue
    public String getName() {
        return this.name;
    }

    @JsonValue
    public String getRemarks() {
        return this.remarks;
    }

    @JsonValue
    public Long getId() {
        return this.id;
    }
}
