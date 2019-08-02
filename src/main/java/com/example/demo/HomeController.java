package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;


@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    MessageRepository messageRepository;


    //Home
//    @RequestMapping("/")
//    public String index(Principal principal, Model model){
//        model.addAttribute("messages", messageRepository.findAll());
//        model.addAttribute("user", userRepository.findAll());
//        return "index";
//    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistrationPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        model.addAttribute("user", user);
        if (result.hasErrors()) {
            return "register";
        } else {
            userService.saveUser(user);
            model.addAttribute("message", "User Account Created");
        }
        return "redirect:/login";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/userProfile")
    public String userProfile() {
        return "userProfile";
    }


//    @GetMapping("/add")
//    public String newMessage(Model model){
//        model.addAttribute("bullhorn", new Bullhorn());
//        return "messageForm";
//    }

    @PostMapping("/process")
    public String processMessage(@Valid Bullhorn bullhorn, @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "messageForm";
        }
        messageRepository.save(bullhorn);
        Set<Bullhorn> messages = new HashSet<Bullhorn>();
        messages.add(bullhorn);
        user.setMessages(messages);
        return "redirect:/";
    }

    @RequestMapping("/update/{id}")
    public String updateMessage(@PathVariable("id") long id, Model model) {
        model.addAttribute("bullhorn", messageRepository.findById(id).get());
        return "messageForm";
    }

    @RequestMapping("/detail/{id}")
    public String viewMessage(@PathVariable("id") long id, Model model) {
        model.addAttribute("bullhorn", messageRepository.findById(id).get());
        return "viewMessage";
    }

    @RequestMapping("/delete/{id}")
    public String deleteMessage(@PathVariable("id") long id) {
        messageRepository.deleteById(id);
        return "redirect:/";
    }
    @RequestMapping("/view/{id}")
    public String showMessage(@PathVariable("id") long id, Model model) {
        model.addAttribute("bullhorn", messageRepository.findById(id).get());
        return "show";
    }
}


