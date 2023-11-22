package com.oneteam.dormeaseadmin.admin.leavePass.sms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oneteam.dormeaseadmin.admin.leavePass.CommonLeavePass;
import com.oneteam.dormeaseadmin.admin.leavePass.ILeavePassMapper;
import com.oneteam.dormeaseadmin.admin.leavePass.LeavePassDto;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class SmsService {

    private final ILeavePassMapper leavePassMapper;
    private final CommonLeavePass commonLeavePass;

    @Value("${naver-cloud-sms.accessKey}")
    private String accessKey;

    @Value("${naver-cloud-sms.secretKey}")
    private String secretKey;

    @Value("${naver-cloud-sms.serviceId}")
    private String serviceId;

    @Value("${naver-cloud-sms.senderPhone}")
    private String phone;

    public SmsService(ILeavePassMapper leavePassMapper, CommonLeavePass commonLeavePass) {
        this.leavePassMapper = leavePassMapper;
        this.commonLeavePass = commonLeavePass;
    }


    public String makeSignature(Long time) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        log.info("makeSignature()");

        String space = " ";
        String newLine = "\n";
        String method = "POST";
        String url = "/sms/v2/services/" + this.serviceId + "/messages";
        String timestamp = time.toString();
        String accessKey = this.accessKey;
        String secretKey = this.secretKey;

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.encodeBase64String(rawHmac);

        return encodeBase64String;

        }

        private HttpHeaders makeHeader() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
            log.info("makeHeader()");
            Long time = System.currentTimeMillis();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-ncp-apigw-timestamp", time.toString());
            headers.set("x-ncp-iam-access-key", accessKey);
            headers.set("x-ncp-apigw-signature-v2", makeSignature(time));

            return headers;

        }

        private SmsResponseDTO buildRequestDto (SmsDTO smsDTO, HttpHeaders headers) throws JsonProcessingException, URISyntaxException {
            List<SmsDTO> messages = new ArrayList<>();
            messages.add(smsDTO);

            SmsRequestDTO request = SmsRequestDTO.builder()
                    .type("SMS")
                    .contentType("COMM")
                    .countryCode("82")
                    .from(phone)
                    .content(smsDTO.getContent())
                    .messages(messages)
                    .build();

            ObjectMapper objectMapper = new ObjectMapper();
            String body = objectMapper.writeValueAsString(request);
            HttpEntity<String> httpBody = new HttpEntity<>(body, headers);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
            SmsResponseDTO response = restTemplate.postForObject(new URI("https://sens.apigw.ntruss.com/sms/v2/services/"+ serviceId +"/messages"), httpBody, SmsResponseDTO.class);

            return response;
        }


    public Map<String, Object> sendComebackMessage(SmsDTO smsDTO, String schoolNo, int pageNum, int amount) throws JsonProcessingException, RestClientException, URISyntaxException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        log.info("sendComebackMessage()");
        HttpHeaders headers = makeHeader();

        SmsResponseDTO response = buildRequestDto(smsDTO, headers);
        Map<String, Object> map = new HashMap<>();

        if(response.getStatusCode().equals("202")){
            LeavePassDto leavePassDto = new LeavePassDto();
            leavePassDto.setNo(smsDTO.getNo());
            leavePassMapper.updateLeavePass(leavePassDto);
        }
        map.put("response", response);
        map.put("leavePassDtos", commonLeavePass.commonClass(schoolNo, pageNum, amount));
        return map;
    }

    public Map<String, String> allSendComebackMessages(String schoolNo) throws JsonProcessingException, RestClientException, URISyntaxException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        log.info("allSendComebackMessages()");
        HttpHeaders headers = makeHeader();

        List<LeavePassDto> leavePassDtos = leavePassMapper.selectLeavePassBySchoolNo(schoolNo);

        Map<String, String> responses = new HashMap<>();
        LeavePassDto leavePassDto = new LeavePassDto();
        leavePassDto.setSchool_no(schoolNo);
        leavePassMapper.updateLeavePass(leavePassDto);

        for(int i = 0; i < leavePassDtos.size(); i++){
            SmsDTO smsDTO = new SmsDTO();
            smsDTO.setTo(leavePassDtos.get(i).getParent_phone());
            smsDTO.setContent("["+leavePassDtos.get(i).getSchool_name()+"] \n" + leavePassDtos.get(i).getStudent_name()+"님이 안전하게복귀하였습니다.");
            SmsResponseDTO response = buildRequestDto(smsDTO, headers);
            if(response.getStatusCode().equals("202")) {
                responses.put(String.valueOf(leavePassDtos.get(i).getNo()), "메시지 발송 성공");
            } else{
                responses.put(String.valueOf(leavePassDtos.get(i).getNo()), "메시지 발송 실패");
            }
        }

        return responses;

    }
}
