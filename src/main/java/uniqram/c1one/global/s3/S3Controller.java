package uniqram.c1one.global.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/s3")
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping
    public ResponseEntity<List<String>> uploadFile(@RequestParam("files") List<MultipartFile> multipartFiles){
        List<String> fileNames = multipartFiles.stream()
                .map(s3Service::uploadFile)
                .toList();

        return ResponseEntity.ok(fileNames);
    }
}
