package bg.mycompany.eventbuddy.web;

import bg.mycompany.eventbuddy.model.binding.UserRegisterBindingModel;
import bg.mycompany.eventbuddy.model.service.UserRegistrationServiceModel;
import bg.mycompany.eventbuddy.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @ModelAttribute("userRegisterBindingModel")
    public UserRegisterBindingModel userRegisterBindingModel() {
        return new UserRegisterBindingModel();
    }

    public RegistrationController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/users/register")
    public String getRegistrationPage() {
        return "user-register";
    }

    @PostMapping("/users/register")
    public String registerUser(@Valid UserRegisterBindingModel userRegisterBindingModel,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        boolean problem = false;

        if (userService.isUsernameTaken(userRegisterBindingModel.getUsername())) {
            redirectAttributes.addFlashAttribute("usernameIsTaken", true);
            problem = true;
        }

        if (userService.isEmailTaken(userRegisterBindingModel.getEmail())) {
            redirectAttributes.addFlashAttribute("emailIsTaken", true);
            problem = true;
        }

        if (bindingResult.hasErrors() || problem ||
                !userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getMatchingPassword())) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);

            return "redirect:/users/register";
        }


        UserRegistrationServiceModel serviceModel =
                modelMapper.map(userRegisterBindingModel, UserRegistrationServiceModel.class);

        userService.registerAndLoginUser(serviceModel);
        return "redirect:/home";
    }
}
