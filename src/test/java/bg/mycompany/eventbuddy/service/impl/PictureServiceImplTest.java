package bg.mycompany.eventbuddy.service.impl;

import bg.mycompany.eventbuddy.model.entity.Picture;
import bg.mycompany.eventbuddy.repository.PictureRepository;
import bg.mycompany.eventbuddy.service.CloudinaryService;
import bg.mycompany.eventbuddy.service.PictureService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PictureServiceImplTest {

    private PictureService pictureService;
    private MockMultipartFile exampleFile;

    @Mock
    private CloudinaryService mockedCloudinaryService;
    @Mock
    private PictureRepository mockedPictureRepository;

    @BeforeEach
    public void init() {
        this.pictureService = new PictureServiceImpl(mockedCloudinaryService, mockedPictureRepository);
        this.exampleFile = new MockMultipartFile("Title", new byte[0]);
    }

    @Test
    void testCreatePicture() throws IOException {
        CloudinaryImage exampleImage = new CloudinaryImage() {{
            setPublicId("default-id");
            setUrl("default-url");
        }};
        Picture picture = new Picture() {{
            setPublicId("default-id");
            setUrl("default-url");
            setId(1L);
        }};
        Mockito.when(mockedCloudinaryService.upload(exampleFile)).thenReturn(exampleImage);
        Picture result = pictureService.createPicture(exampleFile);
        Assertions.assertTrue(result == null);
    }

    @Test
    void testDeletePicture() {
        Picture picture = new Picture() {{
            setId(1L);
            setPublicId("default");
            setUrl("default");
        }};
        Mockito.when(mockedCloudinaryService.delete(picture.getPublicId())).thenReturn(true);
        Assertions.assertTrue(pictureService.deletePicture(picture));
    }

    @Test
    void testUpdateCoverPicture() throws IOException {
        Picture picture = new Picture() {{
            setId(1L);
            setPublicId("default");
            setUrl("default");
        }};
        CloudinaryImage cloudinaryImage = new CloudinaryImage() {{
            setPublicId("updated");
            setUrl("updated");
        }};
        Mockito.when(mockedCloudinaryService.upload(exampleFile)).thenReturn(cloudinaryImage);
        Picture updatePicture = pictureService.updateCoverPicture(exampleFile, picture);
        Assertions.assertEquals(cloudinaryImage.getPublicId(), updatePicture.getPublicId());
    }

    @Test
    void testIsProfilePictureDefault() {
        Picture picture = new Picture() {{
            setPublicId("default");
        }};
        Assertions.assertTrue(pictureService.isProfilePictureDefault(picture));
    }

    @Test
    void testUpdateDefaultProfilePicture() throws IOException {
        CloudinaryImage cloudinaryImage = new CloudinaryImage() {{
            setPublicId("updated");
            setUrl("updated");
        }};
        Mockito.when(mockedCloudinaryService.upload(exampleFile)).thenReturn(cloudinaryImage);
        Picture result = pictureService.updateDefaultProfilePicture(exampleFile);
        Assertions.assertEquals(cloudinaryImage.getPublicId(), result.getPublicId());
    }

    @Test
    void testUpdateProfilePicture() throws IOException {
        CloudinaryImage cloudinaryImage = new CloudinaryImage() {{
            setPublicId("updated");
            setUrl("updated");
        }};
        Picture picture = new Picture() {{
            setId(1L);
            setPublicId("default");
            setUrl("default");
        }};
        Mockito.when(mockedCloudinaryService.upload(exampleFile)).thenReturn(cloudinaryImage);
        Picture result = pictureService.updateProfilePicture(exampleFile, picture);
        Assertions.assertEquals(cloudinaryImage.getPublicId(), result.getPublicId());
    }

    @Test
    void testGetDefaultProfilePicture() {
        Picture picture = new Picture() {{
            setId(1L);
            setPublicId("default");
            setUrl("default");
        }};
        Mockito.when(mockedCloudinaryService.getDefaultPicturePublicId()).thenReturn("default");
        Mockito.when(mockedPictureRepository.findByPublicId("default")).thenReturn(picture);
        Picture defaultProfilePicture = pictureService.getDefaultProfilePicture();

        Assertions.assertEquals(picture, defaultProfilePicture);
    }

}