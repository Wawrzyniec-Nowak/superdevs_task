package com.nowak.wawrzyniec.superdevs.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ClicksAPI {

    @GetMapping("/clicks/{datasource}")
    long getClicksForDatasource(@RequestParam String since, @RequestParam String till) {

    }

}
