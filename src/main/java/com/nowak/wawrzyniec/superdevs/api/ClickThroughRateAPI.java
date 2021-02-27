package com.nowak.wawrzyniec.superdevs.api;

import com.nowak.wawrzyniec.superdevs.business.ClickThroughRateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Api(value = "Click Through Rate API", description = "Provides operations calculating CTR")
@RestController
class ClickThroughRateAPI {

    private final ClickThroughRateService service;

    @Autowired
    public ClickThroughRateAPI(ClickThroughRateService service) {
        this.service = service;
    }

    @ApiOperation(value = "Get CTR list per datasource and campaign", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully calculated ctr")
    })
    @GetMapping("/ctr")
    List<CtrResponse> getCTRPerDatasourceAndCampaign() {
        return service.calculateCTRPerDatasourceAndCampaign().stream() //
                .map(row -> new CtrResponse(row.getString(0), row.getString(1), row.getDouble(2))) //
                .collect(Collectors.toList());
    }

}
