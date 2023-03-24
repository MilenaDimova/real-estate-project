package bg.softuni.myrealestateproject.model.binding;

import org.springframework.web.multipart.MultipartFile;

public class ImageUploadBindingModel {

    private MultipartFile img;

    public MultipartFile getImg() {
        return img;
    }

    public ImageUploadBindingModel setImg(MultipartFile img) {
        this.img = img;
        return this;
    }
}
