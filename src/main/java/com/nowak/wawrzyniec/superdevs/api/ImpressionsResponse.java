package com.nowak.wawrzyniec.superdevs.api;

import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class ImpressionsResponse {

    @ApiModelProperty(notes = "Daily from the data store")
    private final LocalDate daily;

    @ApiModelProperty(notes = "Summed impressions")
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
