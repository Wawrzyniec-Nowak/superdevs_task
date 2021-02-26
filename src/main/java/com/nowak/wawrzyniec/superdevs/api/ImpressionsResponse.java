package com.nowak.wawrzyniec.superdevs.api;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class ImpressionsResponse {

    private final LocalDate daily;

    private final long impressions;

    ImpressionsResponse(String daily, double impressions) {
        this.daily = LocalDate.parse(daily, DateTimeFormatter.ofPattern("MM/dd/yy"));
        this.impressions = Double.valueOf(impressions).longValue();
    }

    public LocalDate getDaily() {
        return daily;
    }

    public long getImpressions() {
        return impressions;
    }
}
