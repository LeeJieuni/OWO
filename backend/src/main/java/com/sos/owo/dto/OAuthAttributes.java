package com.sos.owo.dto;

import com.sos.owo.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email){
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
    }

    // 구글인지 네이버인지 카카오인지 구분하기 위한 메소드
    public static OAuthAttributes of(String registrationId, String userNameAttributesName, Map<String, Object> attributes){
        if("naver".equals(registrationId)) {
            //return ofNaver("id", attributes);
        }
        return ofGoogle(userNameAttributesName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes){
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

//    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes){
//        Map<String, Object> response = (Map<String, Object>)attributes.get("response");
//        return OAuthAttributes.builder()
//                .name((String) response.get("name"))
//                .email((String) response.get("email"))
//                .attributes(response)
//                .nameAttributeKey(userNameAttributeName)
//                .build();
//
//    }

    public Member toEntity(){
        return Member.builder()
                .nick(name)
                .email(email)
                .pw("0000")
                .enable(true)
                .build();
    }

}