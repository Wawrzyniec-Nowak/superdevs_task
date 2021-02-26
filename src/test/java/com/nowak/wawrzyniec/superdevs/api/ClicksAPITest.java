package com.nowak.wawrzyniec.superdevs.api;

import com.nowak.wawrzyniec.superdevs.business.ClicksService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
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
}