package com.oneteam.dormeaseadmin.admin.leavePass.sms;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.oneteam.dormeaseadmin.admin.member.MemberDto;
import com.oneteam.dormeaseadmin.utils.pagination.PageDefine;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Member;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/sms")
public class SmsController {

    private final SmsService smsService;

    public SmsController(SmsService smsService){
        this.smsService = smsService;
    }

    @PostMapping("/sendComebackMessage")
    @ResponseBody
    public Object sendComebackMessage(SmsDTO smsDTO,
                                      HttpSession session,
                                      @RequestParam(value = "pageNum", required = false, defaultValue = PageDefine.DEFAULT_LEAVEPASS_PAGE_NUMBER) int pageNum,
                                      @RequestParam(value = "amount", required = false, defaultValue = PageDefine.DEFAULT_LEAVEPASS_AMOUNT) int amount) throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        log.info("sendComebackMessage()");

        MemberDto loginedMemberDto = (MemberDto) session.getAttribute("loginedMemberDto");

        return smsService.sendComebackMessage(smsDTO,loginedMemberDto.getSchool_no(), pageNum, amount);
    }
    @GetMapping("/allSendComebackMessages")
    @ResponseBody
    public Object allSendComebackMessages(HttpSession session) throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        log.info("allSendComebackMessages()");

        MemberDto loginedMemberDto = (MemberDto) session.getAttribute("loginedMemberDto");

        return smsService.allSendComebackMessages(loginedMemberDto.getSchool_no());
    }

}
