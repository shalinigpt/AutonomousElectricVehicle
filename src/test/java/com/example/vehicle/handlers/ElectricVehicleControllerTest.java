package com.example.vehicle.handlers;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ElectricVehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;



    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/welcome")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, World")));
    }

    @Test
    public void shouldReturnChargingRequiredFalse() throws Exception {
        //{ "vin": "W1K2062161F0033", "source": "Home", "destination": "Lake" }
        Map<String, String> params = new HashMap<String, String>();
        params.put("vin", "W1K2062161F0033");
        params.put("source", "Home");
        params.put("destination", "Lake");
//        this.mockMvc.perform(post("/vehicle_reachDestination")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{ 'vin': 'W1K2062161F0033', 'source': 'Home', 'destination': 'Lake' }")
//                        .accept(MediaType.APPLICATION_JSON))
////                      .andExpect(status().isOk())
////                      .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
////                      .andExpect(MockMvcResultMatchers.jsonPath("$.vin").value("W1K2062161F0033"))
//                        .andExpect(MockMvcResultMatchers.jsonPath("$.isChargingRequired").value(false));
    }

}


