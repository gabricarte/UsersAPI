package personal.project.users.controller;

import personal.project.users.domain.dto.InfoDTO;
import lombok.AllArgsConstructor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@AllArgsConstructor
public class InfoController {

    private final BuildProperties buildProperties;

    @GetMapping("/info")
    public ResponseEntity<InfoDTO> getInfo(){
        return ResponseEntity.status(HttpStatus.OK).body(InfoDTO.builder()
                .version(this.buildProperties.getVersion())
                .build());
    }
}
