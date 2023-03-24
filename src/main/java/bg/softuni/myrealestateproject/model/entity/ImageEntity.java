package bg.softuni.myrealestateproject.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "images")
public class ImageEntity extends BaseEntity{
    @Column
    private String fileName;

    @Column
    private String contentType;

    @Lob
    @Column(length = Integer.MAX_VALUE)
    private byte[] data;

    @ManyToOne(fetch = FetchType.LAZY)
    private OfferEntity offer;

    public ImageEntity() {
    }

    public String getFileName() {
        return fileName;
    }

    public ImageEntity setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getContentType() {
        return contentType;
    }

    public ImageEntity setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public byte[] getData() {
        return data;
    }

    public ImageEntity setData(byte[] data) {
        this.data = data;
        return this;
    }

    public OfferEntity getOffer() {
        return offer;
    }

    public ImageEntity setOffer(OfferEntity offer) {
        this.offer = offer;
        return this;
    }
}
