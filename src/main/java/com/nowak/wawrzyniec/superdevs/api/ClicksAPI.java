package com.nowak.wawrzyniec.superdevs.api;

import com.nowak.wawrzyniec.superdevs.business.ClicksService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Api(value = "clicks API", description = "Provides operations executed on clicks")
@RestController
class ClicksAPI {

    private final ClicksService service;

    @Autowired
    public ClicksAPI(ClicksService service) {
        this.service = service;
    }

    @ApiOperation(value = "Get clicks per datasource in between date period", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully counted clicks per datasource"),
            @ApiResponse(code = 400, message = "Cannot validate proviced parameters correctly. Date format should be yyyy-MM-dd and datasource cannot be empty")
    })
    @GetMapping("/clicks/{datasource}")
    long getClicksForDatasource(@PathVariable String datasource, @RequestParam(required = false) String since, @RequestParam(required = false) String till) {
        if (!areValid(datasource, since, till)) {
            throw new NotValidParametersException("Provided request parameters don't match the contract");
        }
        String startDate = Optional.ofNullable(since).filter(StringUtils::isNotBlank).orElse("1900-01-01");
        String endDate = Optional.ofNullable(till).filter(StringUtils::isNotBlank).orElse(LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return (long) service.calculateClicksPerDatasource(datasource, startDate, endDate).orElse(0L);
    }

    private boolean areValid(String datasource, String since, String till) {
        return StringUtils.isNotBlank(datasource) && hasValidFormat(since) && hasValidFormat(till);
    }

    private boolean hasValidFormat(String date) {
        return StringUtils.isBlank(date) || date.matches("\\d{4}-\\d{2}-\\d{2}");
    }
}
