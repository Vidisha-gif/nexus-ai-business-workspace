package com.nexusai.backend.service;

import com.nexusai.backend.entity.Employee;
import com.nexusai.backend.repository.EmployeeRepository;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final EmployeeRepository employeeRepository;

    // ===========================
    // CSV Export
    // ===========================
    public byte[] exportEmployeesCsv() {

        List<Employee> employees = employeeRepository.findAll();

        StringWriter stringWriter = new StringWriter();
        CSVWriter writer = new CSVWriter(stringWriter);

        writer.writeNext(new String[]{
                "ID",
                "First Name",
                "Last Name",
                "Email",
                "Department",
                "Designation",
                "Salary"
        });

        for (Employee employee : employees) {
            writer.writeNext(new String[]{
                    String.valueOf(employee.getId()),
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getEmail(),
                    employee.getDepartment(),
                    employee.getDesignation(),
                    String.valueOf(employee.getSalary())
            });
        }

        try {
            writer.close();
        } catch (Exception ignored) {
        }

        return stringWriter.toString().getBytes();
    }

    // ===========================
    // Excel Export
    // ===========================
    public byte[] exportEmployeesExcel() {

        List<Employee> employees = employeeRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Employees");

            Row header = sheet.createRow(0);

            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("First Name");
            header.createCell(2).setCellValue("Last Name");
            header.createCell(3).setCellValue("Email");
            header.createCell(4).setCellValue("Department");
            header.createCell(5).setCellValue("Designation");
            header.createCell(6).setCellValue("Salary");

            int rowNum = 1;

            for (Employee employee : employees) {

                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(employee.getId());
                row.createCell(1).setCellValue(employee.getFirstName());
                row.createCell(2).setCellValue(employee.getLastName());
                row.createCell(3).setCellValue(employee.getEmail());
                row.createCell(4).setCellValue(employee.getDepartment());
                row.createCell(5).setCellValue(employee.getDesignation());
                row.createCell(6).setCellValue(employee.getSalary());
            }

            for (int i = 0; i <= 6; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Failed to export Excel file", e);
        }
    }

    public byte[] exportEmployeesPdf() {

    List<Employee> employees = employeeRepository.findAll();

    try {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, outputStream);

        document.open();

        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);

        Paragraph title = new Paragraph("NexusAI Employee Report", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);

        document.add(title);
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);

        table.addCell("ID");
        table.addCell("First Name");
        table.addCell("Last Name");
        table.addCell("Email");
        table.addCell("Department");
        table.addCell("Designation");
        table.addCell("Salary");

        for (Employee employee : employees) {

            table.addCell(String.valueOf(employee.getId()));
            table.addCell(employee.getFirstName());
            table.addCell(employee.getLastName());
            table.addCell(employee.getEmail());
            table.addCell(employee.getDepartment());
            table.addCell(employee.getDesignation());
            table.addCell(String.valueOf(employee.getSalary()));
        }

        document.add(table);

        document.close();

        return outputStream.toByteArray();

    } catch (Exception e) {
        throw new RuntimeException("Failed to export PDF", e);
    }
}
}