package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String...strings) throws Exception{
        roleRepository.save(new Role("USER"));
        roleRepository.save(new Role("ADMIN"));

        Role userRole = roleRepository.findByRole(("USER"));
        Role adminrole = roleRepository.findByRole(("ADMIN"));


        User user = new User("jim@jim.com", "password", "jim", "jimmerson",true,"user");
        user.setRoles(Arrays.asList(userRole));
        userRepository.save(user);

        user = new User ("admin@admin.com", "password", "Admin", "User", true, "admin");
        user.setRoles(Arrays.asList(adminrole));
        userRepository.save(user);

//        User game = new User("gameofcodes@gmail.com",
//                user.encode("password"),
//                "Game",
//                "Stevenson",
//                true,
//                "game");
//        userRepository.save(user);

//        User james = new User("james@gmail.com",
//                user.encode("password"),
//                "james",
//                "Harden",
//                true,
//                "james");
//        user.saveUser(james);
//
//        User jes = new User("jessica@yahoomail.com",
//                user.encode("password"),
//                "Jessica",
//                "Ruler",
//                true,
//                "jes");
//        user.saveUser(jes);
//
//        User admin = new User("admas@gmail.com",
//                user.encode("password"),
//                "Admas",
//                "King",
//                true,
//                "admin");
//        user.saveAdmin(admin);
    }
}
