package com.nowak.wawrzyniec.superdevs.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
class ImpressionsAPI {

    @GetMapping("/impressions")
    Map<LocalDate, Long> getImpressionsDaily() {

    }

}
