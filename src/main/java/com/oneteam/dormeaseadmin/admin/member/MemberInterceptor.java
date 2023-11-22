package com.oneteam.dormeaseadmin.admin.member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class MemberInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        MemberDto loginedMemberDto = (MemberDto) session.getAttribute("loginedMemberDto");

        if(loginedMemberDto == null) {
            response.sendRedirect(request.getContextPath() + "/admin/member/loginForm");
            return false;
        } else {
            return true;
        }
    }
}
