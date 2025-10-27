package personal.project.users.service.report;

import personal.project.users.domain.dto.ReportDTO;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public abstract class ReportProvider {

    public abstract String reportType();

    public abstract List<String> getHeader();

    protected abstract List<List<String>> getEntryReportData(ReportDTO reportDTO);

    public boolean support(ReportDTO reportDTO) {
        return this.reportType().equalsIgnoreCase(reportDTO.getType());
    }

    public InputStream generateReport(ReportDTO report, String fileFormatType) throws IOException {
        if(fileFormatType.equals("xls")) {
            return ReportFactory.builder()
                    .name(report.getType())
                    .headers(this.getHeader())
                        .records(getEntryReportData(report))
                    .build()
                    .generate();
        } else {
            return ReportFactoryCSV.builder()
                    .headers(this.getHeader())
                    .records(getEntryReportData(report))
                    .build()
                    .generate();
        }
    }
}
