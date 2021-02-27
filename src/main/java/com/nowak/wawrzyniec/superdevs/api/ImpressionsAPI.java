package com.nowak.wawrzyniec.superdevs.api;

import com.nowak.wawrzyniec.superdevs.business.ImpressionsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Api(value = "Impressions API", description = "Provides operations executed on impressions")
@RestController
class ImpressionsAPI {

    private final ImpressionsService service;

    @Autowired
    ImpressionsAPI(ImpressionsService service) {
        this.service = service;
    }

    @ApiOperation(value = "Get impressions through time (per day)", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully summed impressions per days")
    })
    @GetMapping("/impressions")
    List<ImpressionsResponse> getImpressionsDaily() {
        return service.calculateImpressionsOverTime() //
                .stream() //
                .map(row -> new ImpressionsResponse(row.getString(0), row.getLong(1)))//
                .collect(Collectors.toList());
    }

}
