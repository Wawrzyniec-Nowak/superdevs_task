package com.nowak.wawrzyniec.superdevs.api;

import io.swagger.annotations.ApiModelProperty;

class ClicksResponse {

    @ApiModelProperty(notes = "Datasource from the data store")
    private final String datasource;

    @ApiModelProperty(notes = "Campaign from the data store")
    private final String campaign;

    @ApiModelProperty(notes = "Clicks from the data store")
    private final long clicks;

    ClicksResponse(String datastore, String campaign, long clicks) {
        this.datasource = datastore;
        this.campaign = campaign;
        this.clicks = clicks;
    }

    public String getDatasource() {
        return datasource;
    }

    public String getCampaign() {
        return campaign;
    }

    public long getClicks() {
        return clicks;
    }
}
