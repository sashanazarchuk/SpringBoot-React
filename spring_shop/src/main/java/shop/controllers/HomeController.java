package shop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.dto.UploadImageDTO;
import shop.storage.StorageService;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@RestController
@AllArgsConstructor
public class HomeController {
    private final StorageService storageService;
    @GetMapping("/")
    public String index(){
        return "Hello";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serverFile(@PathVariable String filename) throws Exception {
        Resource file = storageService.loasAsResource(filename);
        String urlFileName = URLEncoder.encode("Сало.jpg", StandardCharsets.UTF_8.toString());
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "filename=\""+urlFileName+"\"")
                .body(file);
    }
    @PostMapping("/upload")
    public String upload(@RequestBody UploadImageDTO dto) {
        String fileName = storageService.save(dto.getBase64());
        return fileName;
    }

    @PostMapping(value="/form/upload", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public String formupload(@RequestParam("file")MultipartFile file) {
        String fileName = storageService.saveMultipartFile(file);
        return fileName;
    }
}
