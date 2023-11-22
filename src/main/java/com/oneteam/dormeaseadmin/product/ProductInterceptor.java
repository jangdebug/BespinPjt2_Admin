package com.oneteam.dormeaseadmin.product;

import com.oneteam.dormeaseadmin.admin.member.MemberDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.PrintWriter;

public class ProductInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        MemberDto loginedMemberDto = (MemberDto) session.getAttribute("loginedMemberDto");

        if(loginedMemberDto == null) {

            String message = "alert('로그인 시, 사용이 가능합니다.');";

            // 응답에 JavaScript 코드를 출력하고 문자 인코딩 설정
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>" + message + "</script>");
            out.flush();

            // JavaScript가 실행된 이후에 일정 시간(0.1초 후)에 리디렉션을 수행

            out.println("<script>setTimeout(function() { location.href = '" + request.getContextPath() + "/admin/member/loginForm'; }, 100);</script>");

            return false;
        } else {
            return true;
        }
    }
}
