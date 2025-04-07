package personal.project.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import personal.project.users.service.S3Service;

@RestController
@RequiredArgsConstructor
@RequestMapping("/s3")

public class S3Controller {

    private final S3Service service;

    @GetMapping()
    public ResponseEntity<String> getAllObjects() {
        String data = service.getAllObjects();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(data);
    }

    @GetMapping("/object")
    public ResponseEntity<String> getLastLoadProcess(@RequestBody String objectKey) {
        String data = service.getObject(objectKey);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(data);
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> getObject(@RequestBody String objectKey) {
        byte[] data = service.getObjectBytes(objectKey);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", objectKey);

        return ResponseEntity.ok()
                .headers(headers)
                .body(data);
    }
}
