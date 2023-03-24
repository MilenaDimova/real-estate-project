package bg.softuni.myrealestateproject.web;

import bg.softuni.myrealestateproject.model.entity.ImageEntity;
import bg.softuni.myrealestateproject.model.view.ImageViewModel;
import bg.softuni.myrealestateproject.service.ImageService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/download/{fileId}")
    public HttpEntity<byte[]> download(@PathVariable("fileId") long fileId) {

        ImageViewModel imageViewModel = this.imageService.getFileById(fileId).orElseThrow();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType(MimeTypeUtils.parseMimeType(imageViewModel.getContentType())));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+imageViewModel.getFileName());
        headers.setContentLength(imageViewModel.getData().length);

        return new HttpEntity<>(imageViewModel.getData(), headers);
    }
}
