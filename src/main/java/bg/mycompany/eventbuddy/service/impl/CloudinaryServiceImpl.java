package bg.mycompany.eventbuddy.service.impl;

import bg.mycompany.eventbuddy.model.entity.Picture;
import bg.mycompany.eventbuddy.repository.PictureRepository;
import bg.mycompany.eventbuddy.service.CloudinaryService;
import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private static final String TEMP_FILE = "temp-file";
    private static final String URL = "url";
    private static final String PUBLIC_ID = "public_id";
    private static final String DEFAULT_PROFILE_PICTURE_PUBLIC_ID = "default";

    private static final String DEFAULT_PROFILE_PICTURE_URL = "https://res.cloudinary.com/dlrfr4m5j/image/upload/v1639059919/1095867-200_yne9bl.png";

    private final Cloudinary cloudinary;
    private final PictureRepository pictureRepository;

    public CloudinaryServiceImpl(Cloudinary cloudinary, PictureRepository pictureRepository) {
        this.cloudinary = cloudinary;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public CloudinaryImage upload(MultipartFile multipartFile) throws IOException {

        File tempFile = File.createTempFile(TEMP_FILE, multipartFile.getOriginalFilename());
        multipartFile.transferTo(tempFile);
        try {
            @SuppressWarnings("unchecked")
            Map<String, String> uploadResult = cloudinary.
                    uploader().
                    upload(tempFile, Map.of());

            String url = uploadResult.getOrDefault(URL,
                    DEFAULT_PROFILE_PICTURE_URL);
            String publicId = uploadResult.getOrDefault(PUBLIC_ID, "");

            CloudinaryImage image = new CloudinaryImage();
             image.setPublicId(publicId);
             image.setUrl(url);
             return image;

        } finally {
            tempFile.delete();
        }
    }

    @Override
    public boolean delete(String publicId) {
        try {
            this.cloudinary.uploader().destroy(publicId, Map.of());
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public void defaultProfile() {
        if (pictureRepository.count() == 0) {
            Picture picture = new Picture();
            picture.setUrl(DEFAULT_PROFILE_PICTURE_URL);
            picture.setPublicId(DEFAULT_PROFILE_PICTURE_PUBLIC_ID);
            pictureRepository.save(picture);
        }
    }


    @Override
    public String getDefaultPicturePublicId() {
        return DEFAULT_PROFILE_PICTURE_PUBLIC_ID;
    }
}
