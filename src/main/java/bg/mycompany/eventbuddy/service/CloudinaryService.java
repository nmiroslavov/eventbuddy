package bg.mycompany.eventbuddy.service;

import bg.mycompany.eventbuddy.service.impl.CloudinaryImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {

    CloudinaryImage upload(MultipartFile file) throws IOException;

    boolean delete(String publicId);

    void defaultProfile();

    String getDefaultPicturePublicId();
}
