package com.nowak.wawrzyniec.superdevs.api;

import com.nowak.wawrzyniec.superdevs.business.ClicksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ClicksAPI {

    private final ClicksService service;

    @Autowired
    public ClicksAPI(ClicksService service) {
        this.service = service;
    }

    @GetMapping("/clicks/{datasource}")
    long getClicksForDatasource(@PathVariable String datasource, @RequestParam String since, @RequestParam String till) {
        return Double.valueOf(service.calculateClicksPerDatasource(datasource, since, till)).longValue();
    }

}
