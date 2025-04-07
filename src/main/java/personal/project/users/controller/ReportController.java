package personal.project.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import personal.project.users.domain.dto.ReportDTO;
import personal.project.users.exception.ReportProviderNotFoundException;
import personal.project.users.service.report.ReportManager;

import java.io.IOException;
import java.io.InputStream;

@RestController()
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportManager reportManager;

    @GetMapping("/{type}")
    public ResponseEntity<byte[]> generateUserReport(@PathVariable String type) throws IOException, ReportProviderNotFoundException {
        // Cria o DTO
        ReportDTO reportDTO = ReportDTO.builder()
                .type(type)
                .build();

        // Gera o relatório
        InputStream reportStream = reportManager.createReport(reportDTO);

        // Lê os bytes do relatório
        byte[] reportBytes = reportStream.readAllBytes();

        // Define o tipo de conteúdo e headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(type.equals("xls")
                ? MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                : MediaType.parseMediaType("text/csv"));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + type + "_report." + reportManager.getFileFormatType());

        return new ResponseEntity<>(reportBytes, headers, HttpStatus.OK);
    }
}
