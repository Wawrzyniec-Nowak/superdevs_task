package com.nowak.wawrzyniec.superdevs.api;

class CtrResponse {

    private final String datasource;

    private final String campaign;

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
