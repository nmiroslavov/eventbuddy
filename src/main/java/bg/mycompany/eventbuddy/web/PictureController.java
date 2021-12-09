package bg.mycompany.eventbuddy.web;

import bg.mycompany.eventbuddy.model.binding.PictureUploadBindingModel;
import bg.mycompany.eventbuddy.model.entity.Picture;
import bg.mycompany.eventbuddy.repository.PictureRepository;
import bg.mycompany.eventbuddy.service.CloudinaryImage;
import bg.mycompany.eventbuddy.service.CloudinaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class PictureController {

    private final CloudinaryService cloudinaryService;
    private final PictureRepository pictureRepository;

    public PictureController(CloudinaryService cloudinaryService, PictureRepository pictureRepository) {
        this.cloudinaryService = cloudinaryService;
        this.pictureRepository = pictureRepository;
    }


    @GetMapping("/pictures/add")
    public String getPicturesAddPage() {
        return "picture-add";
    }

    @PostMapping("/pictures/add")
    public String addPicture(PictureUploadBindingModel pictureUploadBindingModel) throws IOException {

        Picture picture = createPicture(pictureUploadBindingModel.getPicture(), pictureUploadBindingModel.getTitle());

        pictureRepository.save(picture);

        return "redirect:/pictures/all";
    }

    private Picture createPicture(MultipartFile file, String title) throws IOException {
        CloudinaryImage upload = cloudinaryService.upload(file);

        Picture picture = new Picture();
        picture.setPublicId(upload.getPublicId());
        picture.setUrl(upload.getUrl());

        return picture;
    }

    @GetMapping("/pictures/all")
    public String getAllPictures(Model model) {
        //todo get all pictures
        return "pictures-all";
    }
}
