package bg.softuni.myrealestateproject.service;

import bg.softuni.myrealestateproject.exception.ObjectNotFoundException;
import bg.softuni.myrealestateproject.model.entity.ImageEntity;
import bg.softuni.myrealestateproject.model.entity.OfferEntity;
import bg.softuni.myrealestateproject.model.view.ImageViewModel;
import bg.softuni.myrealestateproject.repository.ImageRepository;
import bg.softuni.myrealestateproject.repository.OfferRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    private final OfferRepository offerRepository;
    private final ModelMapper modelMapper;

    public ImageService(ImageRepository imageRepository, OfferRepository offerRepository, ModelMapper modelMapper) {
        this.imageRepository = imageRepository;
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
    }

    public void saveImageToDB(List<MultipartFile> uploadedImages, Long offerId) {
        OfferEntity offer = this.offerRepository.findById(offerId).get();
        long imagesCountForOffer = this.imageRepository.findAllImagesByOfferId(offerId).stream().count();
        List<ImageEntity> entities = new ArrayList<>();
        for (int i = 0; i < uploadedImages.size(); i++) {
            try {
                ImageEntity entity = new ImageEntity()
                        .setFileName(uploadedImages.get(i).getOriginalFilename())
                        .setContentType(uploadedImages.get(i).getContentType())
                        .setData(uploadedImages.get(i).getBytes())
                        .setImageOrder(i + (int)imagesCountForOffer)
                        .setOffer(offer);
                entities.add(entity);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        this.imageRepository.saveAllAndFlush(entities);
    }

    public ImageEntity getImageEntityById(Long imageId) {
        return this.imageRepository.findById(imageId)
                .orElseThrow(() -> new ObjectNotFoundException("Image with id " + imageId + " was not found!"));
    }

    public ImageViewModel getFileById(Long imageId) {
        return this.modelMapper.map(this.getImageEntityById(imageId), ImageViewModel.class);
    }

    public List<Long> getImagesIds(Long offerId) {
        return this.imageRepository.findAllImagesByOfferId(offerId);
    }


    public void deleteAllImagesByOfferId(Long id) {
        List<ImageEntity> allImagesWithOfferId = this.imageRepository.getAllImagesWithOfferId(id);
        if (allImagesWithOfferId.size() > 0) {
            this.imageRepository.deleteAll(allImagesWithOfferId);
        }
    }

    public boolean deleteImageById(Long imageId) {
        try {
            ImageEntity imageEntity = this.getImageEntityById(imageId);
            this.imageRepository.delete(imageEntity);
        } catch (Exception ex) {
            return false;
        }

        return true;
    }

    public boolean isOwner(String username, Long imageId) {
        OfferEntity offer = this.offerRepository.findOfferByImageId(imageId);
        return this.offerRepository.findById(offer.getId()).filter(o -> o.getOwner().getEmail().equals(username))
                .isPresent();
    }
}
