package bg.softuni.myrealestateproject.web;

import bg.softuni.myrealestateproject.model.view.ImageViewModel;
import bg.softuni.myrealestateproject.service.ImageService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/download/{fileId}")
    public HttpEntity<byte[]> download(@PathVariable("fileId") long fileId) {

        ImageViewModel imageViewModel = this.imageService.getFileById(fileId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType(MimeTypeUtils.parseMimeType(imageViewModel.getContentType())));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+imageViewModel.getFileName());
        headers.setContentLength(imageViewModel.getData().length);

        return new HttpEntity<>(imageViewModel.getData(), headers);
    }

    @PreAuthorize("@imageService.isOwner(#principal.name, #imageId) || @offerService.isAdmin(#principal.name)")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteImageById(Principal principal, @PathVariable("id") Long imageId) {
        boolean isDeleted = this.imageService.deleteImageById(imageId);
        return ResponseEntity.ok(isDeleted);
    }
}
