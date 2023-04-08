package bg.softuni.myrealestateproject.model.view;

public class ImageViewModel {
    private Long id;
    private String fileName;
    private String contentType;
    private byte[] data;

    public ImageViewModel() {
    }

    public Long getId() {
        return id;
    }

    public ImageViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public ImageViewModel setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getContentType() {
        return contentType;
    }

    public ImageViewModel setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public byte[] getData() {
        return data;
    }

    public ImageViewModel setData(byte[] data) {
        this.data = data;
        return this;
    }
}
