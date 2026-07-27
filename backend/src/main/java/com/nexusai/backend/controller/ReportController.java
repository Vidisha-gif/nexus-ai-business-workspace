package com.nexusai.backend.controller;

import com.nexusai.backend.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/employees/csv")
    public ResponseEntity<byte[]> exportCsv() {

        byte[] data = reportService.exportEmployeesCsv();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=employees.csv")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(data);
    }

    @GetMapping("/employees/excel")
public ResponseEntity<byte[]> exportExcel() {

    byte[] data = reportService.exportEmployeesExcel();

    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employees.xlsx")
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(data);
}

@GetMapping("/employees/pdf")
public ResponseEntity<byte[]> exportPdf() {

    byte[] data = reportService.exportEmployeesPdf();

    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=employees.pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .body(data);
}
}
