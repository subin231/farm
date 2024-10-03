package com.farmstory.oauth2;

import com.farmstory.entity.User;
import com.farmstory.repository.user.UserRepository;
import com.farmstory.security.MyUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Log4j2
@RequiredArgsConstructor
@Service
public class MyOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final HttpServletRequest request;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("Load user... 1 : "+userRequest);

        String accessToken = userRequest.getAccessToken().getTokenValue();
        log.info("Load user... 2 (access token) : " + accessToken);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        log.info("Load user... 3 (provider) : " + provider);

        // 소셜 로그인 유저 정보 로딩
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("Load user... 4 (OAuth2User) : " + oAuth2User);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        log.info("Load user... 5 (attributes) : " + attributes);


        String email = null;
        String uid = null;
        String name = null;
        String nick = null;
        String role = "USER";


        if(provider.equals("google")) {
            //사용자 확인 및 회원가입 처리
            email = (String) attributes.get("email");
            uid = email.split("@")[0];
            name = attributes.get("given_name").toString();
            nick = attributes.get("name").toString();

        }else if(provider.equals("naver")){

            // attributes에서 response 키를 통해 유저 정보를 가져옴
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");

            email = (String) response.get("email");
            uid = email.split("@")[0];
            name = (String) response.get("name");
            nick = name + ThreadLocalRandom.current().nextInt(100000, 1000000);

        }else if(provider.equals("kakao")) {
            // attributes에서 필요한 정보를 가져옴
            int random = ThreadLocalRandom.current().nextInt(100000, 1000000);
            Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");  // properties에서 nickname 추출

            // 닉네임은 properties에서 가져옴
            name = (String) properties.get("nickname");
            uid = "kakaoUser_" + random;
            nick = name + random;
        }
        User user = userRepository.findById(uid).orElse(null);

        if (user != null) {
            //회원이 존재하면 시큐리티 인증 처리(로그인)
            return MyUserDetails
                    .builder()
                    .user(user)
                    .accessToken(accessToken)
                    .attributes(attributes)
                    .build();
        }else{
            String userip = getClientIpAddress(request);
            //회원이 존재하지 않으면 회원가입 처리
            user = User.builder()
                    .userUid(uid)
                    .userNick(nick)
                    .userEmail(email)
                    .userRegDate(LocalDateTime.now())
                    .userRegip(userip)
                    .userRole(role)
                    .userName(name)
                    .build();

            userRepository.save(user);

            return MyUserDetails
                    .builder()
                    .user(user)
                    .accessToken(accessToken)
                    .attributes(attributes)
                    .build();

        }
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


}
