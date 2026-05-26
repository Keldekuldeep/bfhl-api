package com.acropolis.bfhl.controller;

import com.acropolis.bfhl.dto.BfhlRequest;
import com.acropolis.bfhl.dto.BfhlResponse;
import com.acropolis.bfhl.service.BfhlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/bfhl")
@RequiredArgsConstructor
public class BfhlController {

    private final BfhlService bfhlService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BfhlResponse> process(@Valid @RequestBody BfhlRequest request) {
        log.info("POST /bfhl — {} elements", request.getData().size());
        BfhlResponse response = bfhlService.processData(request.getData());
        return ResponseEntity.ok(response);
    }
}
