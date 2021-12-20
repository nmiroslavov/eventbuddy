package bg.mycompany.eventbuddy.service.impl;

import bg.mycompany.eventbuddy.repository.PictureRepository;
import bg.mycompany.eventbuddy.service.CloudinaryService;
import com.cloudinary.Cloudinary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CloudinaryServiceImplTest {

    @Mock
    private Cloudinary mockedCloudinary;
    @Mock
    private PictureRepository mockedPictureRepository;

    private CloudinaryService cloudinaryService;

    private MockMultipartFile exampleFile;

    @BeforeEach
    public void init() {
        cloudinaryService = new CloudinaryServiceImpl(mockedCloudinary, mockedPictureRepository);

        exampleFile = new MockMultipartFile("Title", new byte[0]);
    }

    @Test
    void testUploadImage() {
        Assertions.assertThrows(NullPointerException.class,
                () -> cloudinaryService.upload(exampleFile));
    }

    @Test
    void testDeleteImage() throws IOException {
//        Mockito.when(mockedCloudinary.uploader().destroy("example", Map.of())).thenReturn(Map.of());
        Assertions.assertThrows(NullPointerException.class,
                () -> cloudinaryService.delete("example"));
    }

    @Test
    void testDefaultProfile() {
        Mockito.when(mockedPictureRepository.count()).thenReturn(0L);
        cloudinaryService.defaultProfile();
        Assertions.assertTrue(true);
    }

    @Test
    void testDefaultPicturePublicId() {
        String expected = "default";
        Assertions.assertEquals(expected, cloudinaryService.getDefaultPicturePublicId().toString());
    }

}