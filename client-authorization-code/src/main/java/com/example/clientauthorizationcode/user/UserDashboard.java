package com.example.clientauthorizationcode.user;

import java.util.Arrays;

import com.example.clientauthorizationcode.security.ClientUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserDashboard {

    @Autowired
    private OAuth2RestTemplate restTemplate;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/dashboard")
    public ModelAndView dashboard() {
        ClientUserDetails userDetails = (ClientUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ClientUser clientUser = userDetails.getClientUser();
        clientUser.setEntries(Arrays.asList(new Entry("entry 1"), new Entry("entry 2")));

        ModelAndView mv = new ModelAndView("dashboard");
        mv.addObject("user", clientUser);
        tryToGetUserProfile(mv);
        return mv;
    }

    @GetMapping("/callback")
    public ModelAndView callback() {
        return new ModelAndView("forward:/dashboard");
    }


    private void tryToGetUserProfile(ModelAndView mv) {
        String endpoint = "http://localhost:8080/api/profile";
        try {
            UserProfile userProfile = restTemplate.getForObject(endpoint, UserProfile.class);
            mv.addObject("profile", userProfile);

        } catch (HttpClientErrorException e) {
            throw new RuntimeException("it was not possible to retrieve user profile");
        }

    }

}
