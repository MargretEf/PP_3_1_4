package ru.kata.spring.boot_security.demo.conrollers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.PeopleService;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.util.PersonValidator;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AuthConroller {

    private final PersonValidator personValidator;
    private final RoleService roleService;
    private final PeopleService peopleService;
    @Autowired
    private  PasswordEncoder passwordEncoder;
@Autowired
    public AuthConroller(PersonValidator personValidator,RoleService roleService,PeopleService peopleService) {
        this.personValidator = personValidator;
        this.roleService = roleService;
        this.peopleService = peopleService;
    }



    @GetMapping("/users")
    @Transactional
    public String showAllUsers(Model model) {
        model.addAttribute("users", peopleService.getAllUsers());
        model.addAttribute("person", new User());
        List<Role> roles = (List<Role>) roleService.findAll();
        model.addAttribute("allRoles", roles);
        return "users";
    }

        @PostMapping("/registration")
        public String perfomregistration(@ModelAttribute("person") User user,BindingResult bindingResult)
        {
            personValidator.validate(user, bindingResult);
            if (bindingResult.hasErrors()) {
                return "users";
            }
          peopleService.setRole(user);
    peopleService.register(user);
            return "redirect:/admin/users";
        }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("showUser") User user) {
        peopleService.setRole(user);
        peopleService.edit(user);
        return "redirect:/admin/users";
    }


    @PostMapping("/remove")
    public String removeUserId (@ModelAttribute("showUser") User user) {
        peopleService.deleteUser(user.getId());
        return "redirect:/admin/users";
    }

}
