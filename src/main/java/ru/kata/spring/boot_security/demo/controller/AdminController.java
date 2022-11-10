package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public AdminController(UserService userService, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

//    @GetMapping("")
//    public String usersList(Model model) {
//        model.addAttribute("list", userService.getAllUsers());
//        return "admin/users";
//    }
//
//    @GetMapping("/new")
//    public String newUser(Model model) {
//        model.addAttribute("user", new User());
//        return "admin/new";
//    }
//    @PostMapping
//    public String create(@ModelAttribute("user") User user,
//                         @RequestParam(required=false) String roleAdmin) {
//        userService.editUser(user, (roleAdmin != null && roleAdmin.equals("ROLE_ADMIN")));
//        return "redirect:/admin";
//    }
//
//
//
//    @GetMapping("/edit/{id}")
//    public String edit(Model model, @PathVariable("id") long id) {
//        User user = userService.getUserById(id);
//        boolean checked =
//                user.getRoles().contains(roleService.getRoleByName("ROLE_ADMIN"));
//        model.addAttribute("user", user);
//        model.addAttribute("checked", checked);
//        return "admin/edit";
//    }
//    @PostMapping("/edit/{id}")
//    public String update(@ModelAttribute("user") User user,
//                         @RequestParam(required=false) String roleAdmin) {
//        if (user.getPassword().equals("")) {
//            User updUser = userService.getUserById(user.getId());
//            user.setPassword(updUser.getPassword());
//        } else user.setPassword(passwordEncoder.encode(user.getPassword()));
//        userService.editUser(user, (roleAdmin != null && roleAdmin.equals("ROLE_ADMIN")));
//        return "redirect:/admin";
//    }
//
//
//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable("id") long id) {
//        userService.deleteUser(id);
//        return "redirect:/admin";
//    }
@GetMapping("")
public String getAllUsers(Model model) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String email = auth.getName();
    User user = (User) userService.loadUserByUsername(email);
    model.addAttribute("users", userService.getAllUsers());
    model.addAttribute("listRoles", roleService.getAllRoles());
    model.addAttribute("newUser", new User());
    model.addAttribute("user", user);
    return "/admin/admin";
}



    @PostMapping ("/createNewUser" )
    public String addUser (@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PatchMapping("/{id}/updateUser")
    public String updateUserById(User user, String role) {

        userService.saveUser(user, (role.contains("ROLE_ADMIN")));
        return "redirect:/admin";
    }

    @DeleteMapping ("/{id}/delete")
    public String deleteUserById(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }





}
