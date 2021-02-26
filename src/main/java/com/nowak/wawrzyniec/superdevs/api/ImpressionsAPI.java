package com.nowak.wawrzyniec.superdevs.api;

import com.nowak.wawrzyniec.superdevs.business.ImpressionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
class ImpressionsAPI {

    private final ImpressionsService service;

    @Autowired
    ImpressionsAPI(ImpressionsService service) {
        this.service = service;
    }

    @GetMapping("/impressions")
    List<ImpressionsResponse> getImpressionsDaily() {
        return service.calculateImpressionsOverTime() //
                .stream() //
                .map(row -> new ImpressionsResponse(row.getString(0), row.getDouble(1)))//
                .collect(Collectors.toList());
    }

}
