package personal.project.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import personal.project.users.domain.dto.MailDTO;
import personal.project.users.service.NotificationService;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping()
    public ResponseEntity<String> sendMail(@RequestBody MailDTO mailDTO){
        return ResponseEntity.ok(notificationService.sendMail(mailDTO));
    }
}
