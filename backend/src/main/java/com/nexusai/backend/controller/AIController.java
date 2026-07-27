package com.nexusai.backend.controller;

import com.nexusai.backend.dto.AIAnalysisResponse;
import com.nexusai.backend.service.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIController {

    private final AIService aiService;

    @PostMapping("/analyze/{employeeId}")
    public ResponseEntity<AIAnalysisResponse> analyzeEmployee(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(aiService.analyzeEmployee(employeeId));
    }
}