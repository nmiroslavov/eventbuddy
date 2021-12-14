package bg.mycompany.eventbuddy.web;

import bg.mycompany.eventbuddy.model.binding.UserUpdateBindingModel;
import bg.mycompany.eventbuddy.model.service.UserUpdateServiceModel;
import bg.mycompany.eventbuddy.model.view.UserCurrentDetailsViewModel;
import bg.mycompany.eventbuddy.security.SecurityUser;
import bg.mycompany.eventbuddy.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

@Controller
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/users/myprofile")
    public String getCurrentUserProfileInfo(@AuthenticationPrincipal SecurityUser user, Model model) {

        UserCurrentDetailsViewModel userCurrentDetailsViewModel = userService.getLoggedInUserViewModel(user.getUserIdentifier());

        model.addAttribute("currentUserDetails", userCurrentDetailsViewModel);

        return "user-my-profile";
    }

    @GetMapping("/users/myprofile/update")
    public String getMyProfileUpdatePage(@AuthenticationPrincipal SecurityUser user, Model model) {
        UserUpdateBindingModel userUpdateBindingModel = userService.getUserUpdateBindingModel(user.getUserIdentifier());

        model.addAttribute("userUpdateBindingModel", userUpdateBindingModel);

        return "user-my-profile-update";
    }

    @GetMapping("/users/myprofile/update/errors")
    public String updateProfileErrors() {
        return "user-my-profile-update";
    }

    @PatchMapping("/users/myprofile/update")
    public String updateProfile(@Valid UserUpdateBindingModel userUpdateBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes, @AuthenticationPrincipal SecurityUser user) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userUpdateBindingModel", userUpdateBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userUpdateBindingModel", bindingResult);

            return "redirect:/users/myprofile/update/errors";
        }

        UserUpdateServiceModel userUpdateServiceModel = modelMapper.map(userUpdateBindingModel, UserUpdateServiceModel.class);

        userService.updateUser(userUpdateServiceModel, user.getUserIdentifier());

        return "redirect:/users/myprofile";
    }
}
