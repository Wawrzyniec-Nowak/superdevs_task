package com.nowak.wawrzyniec.superdevs.api;

import com.google.common.collect.Lists;
import com.nowak.wawrzyniec.superdevs.business.ClicksService;
import org.apache.spark.sql.catalyst.expressions.GenericRow;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClicksAPI.class)
class ClicksAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClicksService service;

    @Test
    public void shouldBeAbleToCallClicksEndpointAndReceiveStatusOkWithLongValueRepresentingClicks() throws Exception {
        when(service.calculateClicksPerDatasource(anyString(), anyString(), anyString())).thenReturn(Optional.of(64806L));
        this.mockMvc.perform(get("/clicks/Google%20Ads?since=2000-01-01&till=2020-12-30")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("64806")));
    }

    @Test
    public void shouldThrowExceptionWhenDatasourceInThePathIsEmpty() throws Exception {
        this.mockMvc.perform(get("/clicks/ /")).andDo(print()) //
                .andExpect(status().is4xxClientError()) //
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotValidParametersException));
    }

    @Test
    public void shouldThrowExceptionOfDateParametersDontMatchFormat() throws Exception {
        this.mockMvc.perform(get("/clicks/datasource?since=2020-01-1")).andDo(print()) //
                .andExpect(status().is4xxClientError()) //
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotValidParametersException));
    }

    @Test
    public void shouldReturnStatusOkIfNoDateParametersPassed() throws Exception {
        when(service.calculateClicksPerDatasource(anyString(), anyString(), anyString())).thenReturn(Optional.of(64806L));
        this.mockMvc.perform(get("/clicks/datasource")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("64806")));
    }

    @Test
    public void shouldReturnStatusOkIfOneDateParameterIsMissing() throws Exception {
        when(service.calculateClicksPerDatasource(anyString(), anyString(), anyString())).thenReturn(Optional.of(64806L));
        this.mockMvc.perform(get("/clicks/datasource?till=2020-01-01")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("64806")));
    }

    @Test
    public void shouldReturnStatusOkAndListOfDatasourcesAndCampaignsWithClicksAboveThreshold() throws Exception {
        when(service.collectDatastoresAndCampaignsAboveThreshold(anyInt())).thenReturn(Lists.newArrayList(new GenericRow(new Object[]{"Twitter Ads", "Pickerl-Erinnerung", 11L})));
        this.mockMvc.perform(get("/clicks?threshold=10")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("[{\"datasource\" : \"Twitter Ads\", \"campaign\" : \"Pickerl-Erinnerung\", \"clicks\" : 11}]"));
    }

    @Test
    public void shouldReturn400StatusWhenThresholdParameterIsMissing() throws Exception {
        this.mockMvc.perform(get("/clicks")).andDo(print()).andExpect(status().is4xxClientError());
    }
}