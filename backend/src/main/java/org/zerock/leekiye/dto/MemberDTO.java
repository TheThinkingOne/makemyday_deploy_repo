package org.zerock.leekiye.dto;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class MemberDTO extends User {

    // 일반 회원가입이면 로그인시에 아이디, 유저명(닉네임), 비번 따로 설정하게 하고
    // 소셜 로그인이면 해당되는걸 바로 유저명(닉네임에 자동으로 저장하게 해야겠다!)
    private Long id;
    private String userID, userName, password;

    private boolean isSocial;

    private List<String> roleNames = new ArrayList<>();

    // SimpleGrantedAuthority : 문자열로 권한을 만드는 함수
    public MemberDTO(Long id, String userID, String password, String userName, boolean isSocial, List<String> roleNames) {
        super(userID, password, roleNames.stream()
                .map(str -> new SimpleGrantedAuthority("ROLE_" + str))
                .collect(Collectors.toList()));
        this.id = id;
        this.userID = userID;
        this.password = password;
        this.userName = userName;
        this.isSocial = isSocial;
        this.roleNames = roleNames;
    }


    // 이 메소드는 아마 로그인 관련 일것임
//    public Map<String, Object> getClaims() {
//
//        Map<String, Object> dataMap = new HashMap<>();
//
//        dataMap.put("userID", userID);
//        dataMap.put("password", password);
//        dataMap.put("userName", userName);
//        dataMap.put("isSocial", isSocial);
//        dataMap.put("roleNames", roleNames);
//
//        return dataMap;
//    }

    public Map<String, Object> getClaims() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("id", id);
        dataMap.put("userID", userID);
        dataMap.put("userName", userName);
        dataMap.put("password", password);
        dataMap.put("isSocial", isSocial);
        dataMap.put("roleNames", roleNames);
        return dataMap;
    }
}
