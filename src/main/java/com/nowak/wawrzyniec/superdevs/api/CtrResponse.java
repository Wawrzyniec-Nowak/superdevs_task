package com.nowak.wawrzyniec.superdevs.api;

import io.swagger.annotations.ApiModelProperty;

class CtrResponse {

    @ApiModelProperty(notes = "Datasource from the data store")
    private final String datasource;

    @ApiModelProperty(notes = "Campaign from the data store")
    private final String campaign;

    @ApiModelProperty(notes = "Calculated CTR")
    private final double ctr;

    CtrResponse(String datasource, String campaign, double ctr) {
        this.datasource = datasource;
        this.campaign = campaign;
        this.ctr = ctr;
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
}
