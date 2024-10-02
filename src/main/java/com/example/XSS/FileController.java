package com.example.XSS;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class FileController {

    //Attack Demo
 /*
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam String filename) {
        try {
            // Simulera en enkel filnedladdning
            Path file = Paths.get("src/main/resources/files/" + filename).normalize();
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists()) {
                return ResponseEntity.ok().body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
      */

    //Fix metoden
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam String filename) {
        try {
            // Kontrollera om filnamnet innehåller ".." som kan användas för directory traversal
            if (filename.contains("..")) {
                return ResponseEntity.badRequest().build(); // Returnera 400 om försök till traversal
            }
            // Använd ClassPathResource för att läsa filen från src/main/resources/files
            Resource resource = new ClassPathResource("files/" + filename);
            if (!resource.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Returnera 404 om filen inte hittas
            }
            // Förbered responsen för att ladda ned filen
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // Returnera 400 vid fel
        }
    }
}
