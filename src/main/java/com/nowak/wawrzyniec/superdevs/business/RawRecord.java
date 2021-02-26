package com.nowak.wawrzyniec.superdevs.business;

import java.time.LocalDate;

public class RawRecord {

    private final String datasource;

    private final String campaign;

    private final LocalDate daily;

    private final long clicks;

    private final long impressions;

    public RawRecord(String datasource, String campaign, LocalDate daily, long clicks, long impressions) {
        this.datasource = datasource;
        this.campaign = campaign;
        this.daily = daily;
        this.clicks = clicks;
        this.impressions = impressions;
    }

    public String getDatasource() {
        return datasource;
    }

    public String getCampaign() {
        return campaign;
    }

    public LocalDate getDaily() {
        return daily;
    }

    public long getClicks() {
        return clicks;
    }

    public long getImpressions() {
        return impressions;
    }
}
