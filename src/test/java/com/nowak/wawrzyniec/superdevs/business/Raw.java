package com.nowak.wawrzyniec.superdevs.business;

public class Raw {

    private final String Datasource;

    private final String Campaign;

    private final String Daily;

    private final int Clicks;

    private final long Impressions;

    public Raw(String datasource, String campaign, String daily, int clicks, long impressions) {
        this.Datasource = datasource;
        this.Campaign = campaign;
        this.Daily = daily;
        this.Clicks = clicks;
        this.Impressions = impressions;
    }

    public String getDatasource() {
        return Datasource;
    }

    public String getCampaign() {
        return Campaign;
    }

    public String getDaily() {
        return Daily;
    }

    public int getClicks() {
        return Clicks;
    }

    public long getImpressions() {
        return Impressions;
    }
}
