package bashkirov.store.Controller;

import bashkirov.store.model.Person;
import bashkirov.store.service.RegistrationService;
import bashkirov.store.validation.PersonValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final RegistrationService registrationService;
    private final PersonValidator personValidator;

    @GetMapping("/registration")
    public String registrationNewPage(
            @ModelAttribute("personNew") Person personNew
    ) {
        return "registration-new-page";
    }

    @PostMapping("/registration")
    public String registration(
            @ModelAttribute("personNew") Person personNew,
            BindingResult bindingResult
    ) {
        personValidator.validate(personNew, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration-new-page";
        }

        registrationService.register(personNew);
        return "redirect:/auth/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "enter-page";
    }
}
