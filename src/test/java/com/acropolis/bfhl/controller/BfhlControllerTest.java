package com.acropolis.bfhl.controller;

import com.acropolis.bfhl.dto.BfhlResponse;
import com.acropolis.bfhl.service.BfhlService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BfhlController.class)
class BfhlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BfhlService bfhlService;

    // ── GET /bfhl — returns operation_code: 1 ─────────────────────────────
    @Test
    void givenGetRequest_whenGet_thenReturns200WithOperationCode() throws Exception {
        mockMvc.perform(get("/bfhl"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation_code").value(1));
    }

    // ── curl Example A: ["a","1","334","4","R","$"] ────────────────────────
    @Test
    void givenExampleA_whenPost_thenReturns200WithCorrectFields() throws Exception {
        BfhlResponse mock = BfhlResponse.builder()
                .isSuccess(true)
                .userId("john_doe_17091999")
                .email("john@xyz.com")
                .rollNumber("ABCD123")
                .oddNumbers(List.of("1"))
                .evenNumbers(List.of("334", "4"))
                .alphabets(List.of("A", "R"))
                .specialCharacters(List.of("$"))
                .sum("339")
                .concatString("Ra")
                .build();

        when(bfhlService.processData(anyList())).thenReturn(mock);

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"data\": [\"a\", \"1\", \"334\", \"4\", \"R\", \"$\"]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.odd_numbers[0]").value("1"))
                .andExpect(jsonPath("$.even_numbers[0]").value("334"))
                .andExpect(jsonPath("$.even_numbers[1]").value("4"))
                .andExpect(jsonPath("$.alphabets[0]").value("A"))
                .andExpect(jsonPath("$.alphabets[1]").value("R"))
                .andExpect(jsonPath("$.special_characters[0]").value("$"))
                .andExpect(jsonPath("$.sum").value("339"))
                .andExpect(jsonPath("$.concat_string").value("Ra"));
    }

    // ── curl Example B: ["2","a","y","4","&","-","*","5","92","b"] ─────────
    @Test
    void givenExampleB_whenPost_thenReturns200WithCorrectFields() throws Exception {
        BfhlResponse mock = BfhlResponse.builder()
                .isSuccess(true)
                .userId("john_doe_17091999")
                .email("john@xyz.com")
                .rollNumber("ABCD123")
                .oddNumbers(List.of("5"))
                .evenNumbers(List.of("2", "4", "92"))
                .alphabets(List.of("A", "Y", "B"))
                .specialCharacters(List.of("&", "-", "*"))
                .sum("103")
                .concatString("ByA")
                .build();

        when(bfhlService.processData(anyList())).thenReturn(mock);

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"data\": [\"2\",\"a\",\"y\",\"4\",\"&\",\"-\",\"*\",\"5\",\"92\",\"b\"]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.odd_numbers[0]").value("5"))
                .andExpect(jsonPath("$.even_numbers[0]").value("2"))
                .andExpect(jsonPath("$.even_numbers[1]").value("4"))
                .andExpect(jsonPath("$.even_numbers[2]").value("92"))
                .andExpect(jsonPath("$.alphabets[0]").value("A"))
                .andExpect(jsonPath("$.alphabets[1]").value("Y"))
                .andExpect(jsonPath("$.alphabets[2]").value("B"))
                .andExpect(jsonPath("$.special_characters[0]").value("&"))
                .andExpect(jsonPath("$.special_characters[1]").value("-"))
                .andExpect(jsonPath("$.special_characters[2]").value("*"))
                .andExpect(jsonPath("$.sum").value("103"))
                .andExpect(jsonPath("$.concat_string").value("ByA"));
    }

    // ── curl Example C: ["A","ABCD","DOE"] ────────────────────────────────
    @Test
    void givenExampleC_whenPost_thenReturns200WithCorrectFields() throws Exception {
        BfhlResponse mock = BfhlResponse.builder()
                .isSuccess(true)
                .userId("john_doe_17091999")
                .email("john@xyz.com")
                .rollNumber("ABCD123")
                .oddNumbers(List.of())
                .evenNumbers(List.of())
                .alphabets(List.of("A", "ABCD", "DOE"))
                .specialCharacters(List.of())
                .sum("0")
                .concatString("EoDdCbAa")
                .build();

        when(bfhlService.processData(anyList())).thenReturn(mock);

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"data\": [\"A\", \"ABCD\", \"DOE\"]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.alphabets[0]").value("A"))
                .andExpect(jsonPath("$.alphabets[1]").value("ABCD"))
                .andExpect(jsonPath("$.alphabets[2]").value("DOE"))
                .andExpect(jsonPath("$.sum").value("0"))
                .andExpect(jsonPath("$.concat_string").value("EoDdCbAa"));
    }

    // ── curl Numbers only: ["2","8","13"] ─────────────────────────────────
    @Test
    void givenNumbersOnly_whenPost_thenReturns200WithCorrectFields() throws Exception {
        BfhlResponse mock = BfhlResponse.builder()
                .isSuccess(true)
                .userId("john_doe_17091999")
                .email("john@xyz.com")
                .rollNumber("ABCD123")
                .oddNumbers(List.of("13"))
                .evenNumbers(List.of("2", "8"))
                .alphabets(List.of())
                .specialCharacters(List.of())
                .sum("23")
                .concatString("")
                .build();

        when(bfhlService.processData(anyList())).thenReturn(mock);

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"data\": [\"2\", \"8\", \"13\"]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.odd_numbers[0]").value("13"))
                .andExpect(jsonPath("$.even_numbers[0]").value("2"))
                .andExpect(jsonPath("$.even_numbers[1]").value("8"))
                .andExpect(jsonPath("$.sum").value("23"))
                .andExpect(jsonPath("$.concat_string").value(""));
    }

    // ── curl Special characters only: ["$","@","#"] ───────────────────────
    @Test
    void givenSpecialCharsOnly_whenPost_thenReturns200WithCorrectFields() throws Exception {
        BfhlResponse mock = BfhlResponse.builder()
                .isSuccess(true)
                .userId("john_doe_17091999")
                .email("john@xyz.com")
                .rollNumber("ABCD123")
                .oddNumbers(List.of())
                .evenNumbers(List.of())
                .alphabets(List.of())
                .specialCharacters(List.of("$", "@", "#"))
                .sum("0")
                .concatString("")
                .build();

        when(bfhlService.processData(anyList())).thenReturn(mock);

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"data\": [\"$\", \"@\", \"#\"]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.special_characters[0]").value("$"))
                .andExpect(jsonPath("$.special_characters[1]").value("@"))
                .andExpect(jsonPath("$.special_characters[2]").value("#"))
                .andExpect(jsonPath("$.sum").value("0"))
                .andExpect(jsonPath("$.concat_string").value(""));
    }

    // ── curl Empty array — should return 400 ──────────────────────────────
    @Test
    void givenEmptyDataArray_whenPost_thenReturns400AndSuccessFalse() throws Exception {
        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"data\": []}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.is_success").value(false));
    }

    // ── curl Null data — should return 400 ────────────────────────────────
    @Test
    void givenNullDataField_whenPost_thenReturns400AndSuccessFalse() throws Exception {
        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.is_success").value(false));
    }

    // ── curl Malformed JSON — should return 400 ───────────────────────────
    @Test
    void givenMalformedJson_whenPost_thenReturns400AndSuccessFalse() throws Exception {
        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{invalid}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.is_success").value(false));
    }
}
