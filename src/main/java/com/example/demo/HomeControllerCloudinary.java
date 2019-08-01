package com.example.demo;
import com.cloudinary.Singleton;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
public class HomeControllerCloudinary {

    @Autowired
    MessageRepository messagesRepository;

    @Autowired
    CloudinaryConfig cloudc;
      @Autowired
    UserService userService;

    @RequestMapping("/")
    public String listMessages(Model model) {
        model.addAttribute("messages", messagesRepository.findAll());
        if (userService.getUser() != null) {
            model.addAttribute("user_id", userService.getUser().getId());
        }
        return "list";
    }

//    @GetMapping("/add")
//    public String messageform(Model model) {
//        model.addAttribute("bullhorn", new Bullhorn());
//        return "messageform";
//    }

    @PostMapping("/process")



    public String processmessage(@ModelAttribute Bullhorn bullhorn,
                                 @RequestParam("file") MultipartFile file)

    {
        if (file.isEmpty()) {
            bullhorn.setUser(userService.getUser());
            messagesRepository.save(bullhorn);
            return "redirect:/";
        }
        try

        {
            Map uploadResult = cloudc.upload(file.getBytes(),
                    ObjectUtils.asMap("resourcetype", "auto"));

            bullhorn.setImage(uploadResult.get("url").toString());
            bullhorn.setUser(userService.getUser());
            messagesRepository.save(bullhorn);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/add";

        }
        return "redirect:/";
    }
}
