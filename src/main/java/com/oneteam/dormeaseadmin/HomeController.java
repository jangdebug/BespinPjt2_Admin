package com.oneteam.dormeaseadmin;


import com.oneteam.dormeaseadmin.api.ApiService;
import com.oneteam.dormeaseadmin.api.SchoolInfoDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Log4j2
@Controller
public class HomeController {

    @RequestMapping(value = {"", "/"})
    public String home() {
        log.info("home()");

        return "home";
    }

}
