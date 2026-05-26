package com.acropolis.bfhl.service;

import com.acropolis.bfhl.dto.BfhlResponse;

import java.util.List;

public interface BfhlService {
    BfhlResponse processData(List<String> data);
}
