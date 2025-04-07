package personal.project.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<String> getObject(@RequestBody String objectKey) {
        String data = service.getObject(objectKey);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(data);
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadObject(@RequestBody String objectKey) {
        byte[] data = service.getObjectBytes(objectKey);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", objectKey);

        return ResponseEntity.ok()
                .headers(headers)
                .body(data);
    }

    @PutMapping("/upload")
    public ResponseEntity<String> uploadObject(@RequestParam String objectKey,
                                               @RequestBody byte[] content) {
        String result = service.putObject(objectKey, content);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/object")
    public ResponseEntity<String> deleteObject(@RequestBody String objectKey) {
        String result = service.deleteObject(objectKey);
        return ResponseEntity.ok(result);
    }
}
