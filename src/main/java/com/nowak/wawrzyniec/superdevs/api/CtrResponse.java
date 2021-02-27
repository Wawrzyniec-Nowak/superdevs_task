package com.nowak.wawrzyniec.superdevs.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@JsonInclude(JsonInclude.Include.NON_NULL)
class CtrResponse {

    @ApiModelProperty(notes = "Datasource from the data store")
    private final String datasource;

    @ApiModelProperty(notes = "Campaign from the data store")
    private final String campaign;

    @ApiModelProperty(notes = "Calculated CTR")
    private final double ctr;

    @ApiModelProperty(notes = "Daily from the data store")
    private final LocalDate daily;

    CtrResponse(String datasource, String campaign, double ctr) {
        this.datasource = datasource;
        this.campaign = campaign;
        this.ctr = ctr;
        this.daily = null;
    }

    public CtrResponse(String datasource, double ctr, String daily) {
        this.datasource = datasource;
        this.campaign = null;
        this.ctr = ctr;
        this.daily = LocalDate.parse(daily, DateTimeFormatter.ofPattern("MM/dd/yy"));
    }

    public String getDatasource() {
        return datasource;
    }

    public String getCampaign() {
        return campaign;
    }

    public double getCtr() {
        return ctr;
    }

    public LocalDate getDaily() {
        return daily;
    }
}
