package com.oneteam.dormeaseadmin.utils;

import java.security.SecureRandom;
import java.util.Date;

public class GenerateCode {
    /*
     *  6자리 인증번호 생성
     */
    public static String generateAuthNo() {
        java.util.Random generator = new java.util.Random();
        generator.setSeed(System.currentTimeMillis());
        int randomNumber = (generator.nextInt(1000000) % 1000000);
        String formattedNumber = String.format("%06d", randomNumber);

        return formattedNumber;
    }
    /*
     *  8자리 새 비밀번호 생성
     */
    public static String generateRamdomPassword() {
        char[] charSet = new char[]{
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '!', '@', '#', '$', '%', '^', '&'};

        StringBuffer sb = new StringBuffer();
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());

        int idx = 0;
        int len = charSet.length;
        for (int i = 0; i < 8; i++) {
            idx = sr.nextInt(len);
            sb.append(charSet[idx]);
        }
        return sb.toString();
    }
}
