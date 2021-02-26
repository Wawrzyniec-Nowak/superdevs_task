package com.nowak.wawrzyniec.superdevs.api;

import com.google.common.collect.Lists;
import com.nowak.wawrzyniec.superdevs.business.ClickThroughRateService;
import org.apache.spark.sql.catalyst.expressions.GenericRow;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClickThroughRateAPI.class)
class ClickThroughRateAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClickThroughRateService service;

    @Test
    public void shouldBeAbleToCallCtrEndpointAndReceiveStatusOkWithTheListOfCtrPerDatasourceAndCampaign() throws Exception {
        when(service.calculateCTRPerDatasourceAndCampaign()).thenReturn(Lists.newArrayList(new GenericRow(new Object[]{"Twitter Ads", "Pickerl-Erinnerung", 0.01})));
        this.mockMvc.perform(get("/ctr")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("[{\"datasource\" : \"Twitter Ads\", \"campaign\" : \"Pickerl-Erinnerung\", \"ctr\" : 0.01}]"));
    }
}