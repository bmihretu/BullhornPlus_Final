package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
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
    @RequestMapping("/")
    public String index(Principal principal, Model model){
        model.addAttribute("messages", messageRepository.findAll());
        model.addAttribute("user", userService.getUser());

        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model){
        model.addAttribute("user",  new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistrationPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {

        model.addAttribute("user", user);
        if (result.hasErrors()) {
            return "register";
        } else {
            user.setHash(MD5Util.md5Hex(user.getEmail()));
            userService.saveUser(user);
            model.addAttribute("message", "User Account Created");
        }
        return "redirect:/login";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/userProfile")
    public String userProfile(Model model){
        model.addAttribute("user", userService.getUser());
        return "userProfile";}

    @PostMapping("/admin")
    public String admin(){return "admin";}

    @GetMapping("/add")
    public String newMessage(Model model){
        model.addAttribute("bullhorn", new Bullhorn());
        return "messageForm";
    }

    @PostMapping("/process")
    public String processMessage(@Valid Bullhorn bullhorn, @ModelAttribute("user") User user, BindingResult result, Model model){
        if(result.hasErrors()){
            return "messageForm";
        }
        messageRepository.save(bullhorn);
        Set<Bullhorn> messages = new HashSet<Bullhorn>();
        messages.add(bullhorn);
        user.setMessages(messages);
        return "redirect:/";
    }

    @RequestMapping("/update/{id}")
    public String updateMessage(@PathVariable("id") long id, Model model){
        model.addAttribute("bullhorn", messageRepository.findById(id).get());
        return "messageForm";
    }

    @RequestMapping("/detail/{id}")
    public String viewMessage(@PathVariable("id") long id, Model model){
        model.addAttribute("bullhorn", messageRepository.findById(id).get());
        return "viewMessage";
    }

    @RequestMapping("/delete/{id}")
    public String deleteMessage(@PathVariable("id") long id){
        messageRepository.deleteById(id);
        return "redirect:/";
    }

    @RequestMapping("/updateUser/{id}")
    public String updateUser(@PathVariable("id") long id, Model model){
        model.addAttribute("user", userRepository.findById(id).get());
        return "updateUser";
    }
    @PostMapping("/updateUser")
    public String processupdateUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "updateUser";
        } else {
//            user.setHash(MD5Util.md5Hex(user.getEmail()));
            userService.saveUser(user);
//            model.addAttribute("message", "User Account Created");
        }
        return "redirect:/";
    }



}
