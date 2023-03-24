package bg.softuni.myrealestateproject.service;

import bg.softuni.myrealestateproject.model.entity.ImageEntity;
import bg.softuni.myrealestateproject.model.entity.OfferEntity;
import bg.softuni.myrealestateproject.model.view.ImageViewModel;
import bg.softuni.myrealestateproject.repository.ImageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    public ImageService(ImageRepository imageRepository, ModelMapper modelMapper) {
        this.imageRepository = imageRepository;
        this.modelMapper = modelMapper;
    }

    public List<ImageEntity> saveImageToDB(List<MultipartFile> uploadedImages, OfferEntity offer) {
        List<ImageEntity> entities = new ArrayList<>();
        uploadedImages.forEach(ui -> {
            try {
                ImageEntity entity = new ImageEntity()
                    .setFileName(ui.getOriginalFilename())
                    .setContentType(ui.getContentType())
                    .setData(ui.getBytes())
                    .setOffer(offer);
                entities.add(entity);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return this.imageRepository.saveAllAndFlush(entities);
    }

    public Optional<ImageViewModel> getFileById(Long fileId) {
        return this.imageRepository.findById(fileId)
                .map(imageEntity -> this.modelMapper.map(imageEntity, ImageViewModel.class));
    }

    public List<Long> getImagesIds(Long offerId) {
        return this.imageRepository.findAllImagesByOfferId(offerId);
    }


}
