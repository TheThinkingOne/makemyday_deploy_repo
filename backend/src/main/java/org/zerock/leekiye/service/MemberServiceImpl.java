package org.zerock.leekiye.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.zerock.leekiye.domain.Member;
import org.zerock.leekiye.domain.MemberRole;
import org.zerock.leekiye.dto.MemberDTO;
import org.zerock.leekiye.dto.MemberModifyDTO;
import org.zerock.leekiye.dto.MemberRegisterDTO;
import org.zerock.leekiye.repository.MemberRepository;

import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberDTO getKakaoMember(String accessToken) {
        log.info("[DEBUG] getKakaoMember() 실행됨, accessToken: " + accessToken);

        if (accessToken == null || accessToken.isEmpty()) {
            log.error("[ERROR] accessToken이 null 또는 비어 있음");
            return null;
        }

        // accessToken 을 이용해서 사용자 정보 가져오기
        String kakaoID = getKakaoID(accessToken); // ← 사용자 고유 ID 값 받기
        String nickname = getKakaoNickname(accessToken); // ← 카카오톡 닉네임

        if (kakaoID == null || nickname == null) {
            log.error("[ERROR] 카카오 사용자 정보 조회 실패");
            return null;
        }

        // 카카오 로그인 사용자의 로그인 아이디는 kakao_123456 같은 형식으로 설정
        String userID = "kakao_" + kakaoID;

        log.info("카카오 사용자 ID(userID): {}", userID);
        log.info("카카오 사용자 닉네임(userName): {}", nickname);

        // 기존 회원인지 확인
        Optional<Member> result = memberRepository.findByUserID(userID);

        if (result.isPresent()) {
            log.info("기존 카카오 회원 로그인 성공");
            return entityToDTO(result.get());
        }

        // 신규 회원이면 저장
        Member newSocialMember = makeSocialMember(userID, nickname);
        memberRepository.save(newSocialMember);

        log.info("[DEBUG] 신규 소셜 회원 저장됨: {}", newSocialMember);

        return entityToDTO(newSocialMember);
    }

    @Override
    public UserDetails loadByUserName(String userID) throws UsernameNotFoundException {
        Member member = memberRepository.getWithRoles(userID)
                .orElseThrow(() -> new UsernameNotFoundException(userID + "의 아이디를 가진 사용자를 찾을 수 없습니다."));

        return new MemberDTO(
                member.getId(),
                member.getUserID(),
                member.getPassword(),
                member.getUserName(),
                member.isSocial(),
                member.getMemberRoleList().stream()
                        .map(Enum::name)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Member register(MemberRegisterDTO memberRegisterDTO) { // 여기 리턴타입을 void 로 하는게 좋은가 아니면
        // Member 로 하는게 좋은가 흠...
        // 여기다가 회원가입 로직 적으면 될듯
        // 여기다가 이미 존재하는 회원 체크하는 로직도 추가해야할듯(서비스 등)

        if(memberRegisterDTO.getUserID().length() < 8) {
            throw new RuntimeException("아이디, 비밀번호는 8글자 이상으로 설정해주세요.");
        }
        if(memberRegisterDTO.getPassword().length()<8) {
            throw new RuntimeException("아이디, 비밀번호는 8글자 이상으로 설정해주세요.");
        }

        Member member = dtoToEntity(memberRegisterDTO);
        member.addRole(MemberRole.USER);
        return memberRepository.save(member);
    }

    // 회원정보 변경을 위한 서비스 IMPL 메소드
    @Override
    public void modifyMember(MemberModifyDTO memberModifyDTO) {
        Optional<Member> result = memberRepository.findByUserID(memberModifyDTO.getUserID());

        Member member = result.orElseThrow();

        // 이게 빠져있어서 닉변이 안됬었던것 같다
        member.changeNickname(memberModifyDTO.getUserName());

        // 이 페이지에서 정보를 수정했다는 것은 더이상 소셜 회원이 아니라는 것?
        // 소셜 유저는 비밀번호 변경 불가
        if (!member.isSocial()) {
            if (memberModifyDTO.getPassword() != null && !memberModifyDTO.getPassword().isBlank()) {
                member.changePw(passwordEncoder.encode(memberModifyDTO.getPassword()));
            }
        }

        memberRepository.save(member);
    }

    // 소셜 회원 생성 메소드
    private Member makeSocialMember(String userID, String userName) {
        String tempPassword = makeTempPassword(); // 임시 비번 생성

        log.info("임시 비번 tempPassword: " + tempPassword);

        Member member = Member.builder()
                .userID(userID)
                .password(passwordEncoder.encode(tempPassword))
                .userName(userName)
                .isSocial(true)
                .build();

        member.addRole(MemberRole.USER);

        return member;
    }

    // 카카오에서 사용자 고유 ID 가져오기
    private String getKakaoID(String accessToken) {
        String kakaoGetUserURL = "https://kapi.kakao.com/v2/user/me";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<LinkedHashMap> response = restTemplate.exchange(
                    kakaoGetUserURL, HttpMethod.GET, entity, LinkedHashMap.class
            );

            LinkedHashMap<String, Object> bodyMap = response.getBody();
            Object idObj = bodyMap.get("id");

            return idObj.toString();
        } catch (Exception e) {
            log.error("[ERROR] 카카오 ID 가져오기 실패:", e);
            return null;
        }
    }

    // 카카오에서 닉네임 가져오기
    private String getKakaoNickname(String accessToken) {
        String kakaoGetUserURL = "https://kapi.kakao.com/v2/user/me";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<LinkedHashMap> response = restTemplate.exchange(
                    kakaoGetUserURL, HttpMethod.GET, entity, LinkedHashMap.class
            );

            LinkedHashMap<String, Object> bodyMap = response.getBody();
            LinkedHashMap<String, String> properties = (LinkedHashMap<String, String>) bodyMap.get("properties");

            if (properties == null) {
                return null;
            }

            return properties.get("nickname");
        } catch (Exception e) {
            log.error("[ERROR] 카카오 닉네임 가져오기 실패:", e);
            return null;
        }
    }

    // 10자리의 랜덤한 비밀번호 만드는 메소드
    private String makeTempPassword() {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            buffer.append((char) ((int) (Math.random() * 26) + 97)); // a~z 범위의 랜덤 문자
        }
        return buffer.toString();
    }

    private Member dtoToEntity(MemberRegisterDTO dto) {
        return Member.builder()
                .userID(dto.getUserID())
                .userName(dto.getUserName())
                .password(passwordEncoder.encode(dto.getPassword()))
                .isSocial(false)
                .build();
    }


    // 회원가입 시에 중복된 아이디 이미 있는지 체크\

    // 이게 맞는지 확실하지 않군
    @Override
    public boolean existsByUserID(String userID) {
        return memberRepository.existsByUserID(userID);
    }
}
