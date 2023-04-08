package bg.softuni.myrealestateproject.model.binding;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class UploadImagesBindingModel {

    private Long id;

    private List<MultipartFile> uploadedImages;

    public Long getId() {
        return id;
    }

    public UploadImagesBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

    public List<MultipartFile> getUploadedImages() {
        return uploadedImages;
    }

    public UploadImagesBindingModel setUploadedImages(List<MultipartFile> uploadedImages) {
        this.uploadedImages = uploadedImages;
        return this;
    }
}
