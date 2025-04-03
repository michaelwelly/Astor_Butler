package museon_online.astor_butler.reference;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menu/pdf")
public class MenuPdfController {

    private final MenuPdfRepository repository;

    @Value("${astor.media.menu-dir}")
    private String menuPdfDirectory;

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getMenuPdf(@PathVariable UUID id) {
        MenuPdf pdf = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("PDF не найден"));

        Path filePath = Path.of(menuPdfDirectory, extractFilename(pdf.getFileUrl()));
        Resource file = new FileSystemResource(filePath);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    private String extractFilename(String fileUrl) {
        return Path.of(fileUrl).getFileName().toString();
    }
}