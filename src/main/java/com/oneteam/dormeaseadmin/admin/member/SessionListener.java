package com.oneteam.dormeaseadmin.admin.member;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class SessionListener implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent event) {
        log.info("sessionCreated()");
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        log.info("sessionDestroyed()");
        MemberDto loginedMemberDto = (MemberDto) event.getSession().getAttribute("loginedMemberDto");
    }
}
