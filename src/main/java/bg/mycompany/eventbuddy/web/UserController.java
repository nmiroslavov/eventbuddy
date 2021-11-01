package bg.mycompany.eventbuddy.web;

import bg.mycompany.eventbuddy.model.binding.UserLoginBindingModel;
import bg.mycompany.eventbuddy.model.binding.UserRegisterBindingModel;
import bg.mycompany.eventbuddy.model.service.UserServiceModel;
import bg.mycompany.eventbuddy.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final ModelMapper modelMapper;
    private final UserService userService;

    public UserController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @ModelAttribute
    public UserRegisterBindingModel userRegisterBindingModel() {
        return new UserRegisterBindingModel();
    }

    @ModelAttribute
    public UserLoginBindingModel userLoginBindingModel() {
        return new UserLoginBindingModel();
    }

    @GetMapping("/register")
    public String register(Model model) {
        if (!model.containsAttribute("usernameIsTaken")) {
            model.addAttribute("usernameIsTaken", false);
        }

        if (!model.containsAttribute("emailIsTaken")) {
            model.addAttribute("emailIsTaken", false);
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerConfirm(@Valid UserRegisterBindingModel userRegisterBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors() || !userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel",
                    userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel",
                    bindingResult);

            return "redirect:register";
        }

        if (userService.checkUsernameIsTaken(userRegisterBindingModel.getUsername())) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel",
                    userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("usernameIsTaken", true);

            return "redirect:register";
        }

        if (userService.checkEmailIsTaken(userRegisterBindingModel.getEmail())) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("emailIsTaken", true);

            return "redirect:register";
        }

        userService.registerUser(modelMapper.map(userRegisterBindingModel, UserServiceModel.class));

        return "redirect:login";

    }

    @GetMapping("/login")
    public String login(Model model) {
        if (!model.containsAttribute("userNotFound")) {
            model.addAttribute("userNotFound", false);
        }

        return "login";
    }

    @PostMapping("/login")
    public String loginConfirm(@Valid UserLoginBindingModel userLoginBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel", bindingResult);

            return "redirect:login";
        }

        UserServiceModel userServiceModel = userService.findByUsernameAndPassword(userLoginBindingModel.getUsername(), userLoginBindingModel.getPassword());


        if (userServiceModel == null) {
            redirectAttributes.addFlashAttribute("userNotFound", true);
            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);

            return "redirect:login";
        }

        userService.loginUser(userServiceModel.getUsername(), userServiceModel.getId());

        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();

        return "redirect:/";
    }
}
