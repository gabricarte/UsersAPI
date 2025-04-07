package personal.project.users.exception;


public class ReportProviderNotFoundException extends Exception {
    public ReportProviderNotFoundException(String reportType) {
        super("Report Provider não encontrado para o relatório " + reportType);
    }
}
