package xiao.ze.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import xiao.ze.demo.entity.User;
import xiao.ze.demo.service.UserService;

/**
 * SecurityController
 *
 * @author xiaoze
 * @date 2018/6/3
 */
@Controller
@RequestMapping("/security")
public class SecurityController {

    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    public String root() {
        return "index";
    }

    @GetMapping("/toLogin")
    public String toLogin(Map<String, Object> map) {

        map.put("user", new User());

        return "login";
    }


    @PostMapping(value = "/login")
    public ModelAndView login(User user, Map<String, Object> map,Model model) {

        if (userService.get(user.getUserNo()) != null) {
            User user1 = userService.get(user.getUserNo());
            if (user1.getUserPwd().equals(user.getUserPwd())) {
                map.put("user", user1);
                model.addAttribute("id",user1.getUserNo());
                return new ModelAndView("/main","m", model );
            }
        }


        return new ModelAndView("/login","m", model );
    }

    @GetMapping(value = "/r")
    public String register1(String id, String name, String password, Map<String, Object> map) {


        return "register";

    }


    @GetMapping("/u/{id}")
    public ModelAndView u(@PathVariable("id") String id, Model model) {

        model.addAttribute("id",id);
        return new ModelAndView("/uptate","m", model );
    }


    @PostMapping("/u")
    public ModelAndView u2( String id,String name,String password, Model model) {

        User user=new User();
        user.setUserName(name);
        user.setUserPwd(password);
        user.setUserNo(id);
        userService.update(user);
        model.addAttribute("id",id);
        return new ModelAndView("/uptate","m", model );
    }


    @PostMapping(value = "/r")
    public String register(String id, String name, String password, Map<String, Object> map) {
        User user = new User();
        user.setUserNo(id);
        user.setUserName(name);
        user.setUserPwd(password);

        userService.addUser(user);
        System.out.println("添加用户"+user.toString());
        return "login";

    }

    @GetMapping("/mainController")
    public String main() {

        return "main";
    }

    @GetMapping("/logout")
    public String logout() {

        return "redirect:/security/toLogin";

    }

}