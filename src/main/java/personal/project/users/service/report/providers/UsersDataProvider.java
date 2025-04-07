package personal.project.users.service.report.providers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import personal.project.users.domain.dto.ReportDTO;
import personal.project.users.domain.entity.User;
import personal.project.users.service.UserService;
import personal.project.users.service.report.ReportProvider;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Component // component é necessário pq?
public class UsersDataProvider extends ReportProvider {

    private final UserService userService;

    @Override
    public String reportType() {
        return "UsersData";
    }

    public List<String> getHeader(){
        return Arrays.asList("Username", "Name", "E-mail",
                "Birth", "Phone");
    }

    // Vai no banco para buscar os usuários
    @Override
    protected List<List<String>> getEntryReportData(ReportDTO reportDTO) {
        List<User> users = userService.getUsers();
        return users.stream()
                .map(user -> List.of(
                        user.getUsername(),
                        user.getName(),
                        user.getEmail(),
                        user.getBirth(),
                        user.getPhone()
                ))
                .toList();
    }
}
