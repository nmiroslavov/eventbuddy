package bg.mycompany.eventbuddy.service.impl;

import bg.mycompany.eventbuddy.model.entity.Picture;
import bg.mycompany.eventbuddy.repository.PictureRepository;
import bg.mycompany.eventbuddy.service.CloudinaryImage;
import bg.mycompany.eventbuddy.service.CloudinaryService;
import bg.mycompany.eventbuddy.service.PictureService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PictureServiceImpl implements PictureService {

    private final CloudinaryService cloudinaryService;
    private final PictureRepository pictureRepository;

    public PictureServiceImpl(CloudinaryService cloudinaryService, PictureRepository pictureRepository) {
        this.cloudinaryService = cloudinaryService;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public Picture createPicture(MultipartFile file) throws IOException {

        CloudinaryImage upload = cloudinaryService.upload(file);

        Picture picture = new Picture();
        picture.setPublicId(upload.getPublicId());
        picture.setUrl(upload.getUrl());

        pictureRepository.save(picture);

        return pictureRepository.findByPublicId(picture.getPublicId());
    }
}
