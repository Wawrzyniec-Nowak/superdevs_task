package com.nowak.wawrzyniec.superdevs.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ClickThroughRateAPI {

    @GetMapping("/ctr/{datasource}/{campaign}")
    double getCTRPerDatasourceAndCampaign(@PathVariable String datasource, @PathVariable String campaign){

    }

}
