package com.nowak.wawrzyniec.superdevs.api;

import com.google.common.collect.Lists;
import com.nowak.wawrzyniec.superdevs.business.ClickThroughRateService;
import org.apache.spark.sql.catalyst.expressions.GenericRow;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
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

    @Test
    public void shouldBeAbleToCallCtrEndpointWithDatasourceParameterAndReceiveListOfCtrPerDaily() throws Exception {
        when(service.calculateCTRPerDaily(anyString())).thenReturn(Lists.newArrayList(new GenericRow(new Object[]{"Twitter Ads", 0.01, "01/01/20"})));
        this.mockMvc.perform(get("/ctr/Google")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("[{\"datasource\" : \"Twitter Ads\", \"daily\" : \"2020-01-01\", \"ctr\" : 0.01}]"));
    }

    @Test
    public void shouldReturn400StatusCodeWhenDatasourceParameterIsMissing() throws Exception {
        this.mockMvc.perform(get("/ctr/ /")).andDo(print()) //
                .andExpect(status().is4xxClientError()) //
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotValidParametersException));
    }
}