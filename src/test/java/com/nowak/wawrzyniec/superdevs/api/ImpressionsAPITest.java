package com.nowak.wawrzyniec.superdevs.api;

import com.google.common.collect.Lists;
import com.nowak.wawrzyniec.superdevs.business.ImpressionsService;
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

@WebMvcTest(ImpressionsAPI.class)
class ImpressionsAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImpressionsService service;

    @Test
    public void shouldBeAbleToCallImpressionsEndpointAndReceiveStatusOkWithListOfImpressionsPerDay() throws Exception {
        when(service.calculateImpressionsOverTime()).thenReturn(Lists.newArrayList(new GenericRow(new Object[]{"01/01/20", 124312L})));
        this.mockMvc.perform(get("/impressions")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("[{\"daily\" : \"2020-01-01\", \"impressions\" : 124312}]"));
    }
}