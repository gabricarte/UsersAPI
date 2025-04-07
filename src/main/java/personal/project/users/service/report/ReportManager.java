package personal.project.users.service.report;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import personal.project.users.domain.dto.ReportDTO;
import personal.project.users.exception.ReportProviderNotFoundException;
import personal.project.users.service.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@Log4j2
public class ReportManager {
    protected List<ReportProvider> providers;

    @Getter
    @Value("${file.format.type:csv}")
    private String fileFormatType;

    public ReportManager (List<ReportProvider> providers, UserService userService) {
        this.providers = providers;
    }


    //instanceOf
    ReportProvider verifyReportType(ReportDTO report) throws ReportProviderNotFoundException {
        return this.providers.stream().filter(provider-> provider.support(report)).findFirst()
                .orElseThrow(() -> new ReportProviderNotFoundException(report.getType()));
    }

    public InputStream createReport(ReportDTO reportDTO) throws ReportProviderNotFoundException, IOException {
        ReportProvider provider = this.verifyReportType(reportDTO);
        return provider.generateReport(reportDTO, fileFormatType);
    }

}
