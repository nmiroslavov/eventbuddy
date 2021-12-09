package bg.mycompany.eventbuddy.service;

import bg.mycompany.eventbuddy.model.entity.Picture;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PictureService {

    Picture createPicture(MultipartFile file) throws IOException;
}
