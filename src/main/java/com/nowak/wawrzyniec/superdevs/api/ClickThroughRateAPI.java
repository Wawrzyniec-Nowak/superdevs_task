package com.nowak.wawrzyniec.superdevs.api;

import com.nowak.wawrzyniec.superdevs.business.ClickThroughRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
class ClickThroughRateAPI {

    private final ClickThroughRateService service;

    @Autowired
    public ClickThroughRateAPI(ClickThroughRateService service) {
        this.service = service;
    }

    @GetMapping("/ctr")
    List<CtrResponse> getCTRPerDatasourceAndCampaign() {
        return service.calculateCTRPerDatasourceAndCampaign().stream() //
                .map(row -> new CtrResponse(row.getString(0), row.getString(1), row.getDouble(2))) //
                .collect(Collectors.toList());
    }

}
