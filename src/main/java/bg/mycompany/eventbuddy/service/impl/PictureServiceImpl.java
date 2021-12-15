package bg.mycompany.eventbuddy.service.impl;

import bg.mycompany.eventbuddy.model.entity.Picture;
import bg.mycompany.eventbuddy.repository.PictureRepository;
import bg.mycompany.eventbuddy.service.CloudinaryService;
import bg.mycompany.eventbuddy.service.PictureService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Override
    public Picture updateCoverPicture(MultipartFile file, Picture originalPicture) throws IOException {

        CloudinaryImage updatedImage = cloudinaryService.upload(file);

        cloudinaryService.delete(originalPicture.getPublicId());

        originalPicture.setPublicId(updatedImage.getPublicId());
        originalPicture.setUrl(updatedImage.getUrl());

        pictureRepository.save(originalPicture);

        return originalPicture;
    }

    @Transactional
    @Override
    public boolean deletePicture(Picture picture) {

        boolean isDeleted = cloudinaryService.delete(picture.getPublicId());
        pictureRepository.delete(picture);

        return isDeleted;
    }

    @Override
    public boolean isProfilePictureDefault(Picture picture) {

        return picture.getPublicId().equals("default");
    }

    @Override
    public Picture updateDefaultProfilePicture(MultipartFile file) throws IOException {

        CloudinaryImage updatedImage = cloudinaryService.upload(file);
        Picture profilePicture = new Picture();
        profilePicture.setPublicId(updatedImage.getPublicId());
        profilePicture.setUrl(updatedImage.getUrl());
        pictureRepository.save(profilePicture);

        return profilePicture;
    }

    @Override
    public Picture updateProfilePicture(MultipartFile file, Picture originalPicture) throws IOException {

        CloudinaryImage updatedImage = cloudinaryService.upload(file);

        cloudinaryService.delete(originalPicture.getPublicId());

        originalPicture.setPublicId(updatedImage.getPublicId());
        originalPicture.setUrl(updatedImage.getUrl());

        pictureRepository.save(originalPicture);

        return originalPicture;
    }

    @Override
    public Picture getDefaultProfilePicture() {
        return pictureRepository.findByPublicId(cloudinaryService.getDefaultPicturePublicId());
    }
}
