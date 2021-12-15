package bg.mycompany.eventbuddy.service;

import bg.mycompany.eventbuddy.model.entity.Picture;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PictureService {

    Picture createPicture(MultipartFile file) throws IOException;

    Picture updateCoverPicture(MultipartFile file, Picture originalPicture) throws IOException;

    boolean deletePicture(Picture picture);

    boolean isProfilePictureDefault(Picture picture);

    Picture updateDefaultProfilePicture(MultipartFile file) throws IOException;

    Picture updateProfilePicture(MultipartFile file, Picture originalPicture) throws IOException;

    Picture getDefaultProfilePicture();
}
