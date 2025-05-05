package org.zerock.leekiye.repository;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.leekiye.config.CustomSecurityConfig;
import org.zerock.leekiye.domain.Member;
import org.zerock.leekiye.domain.MemberRole;
import org.zerock.leekiye.dto.MemberRegisterDTO;
import org.zerock.leekiye.service.MemberService;

import java.util.Optional;

@SpringBootTest
@Log4j2
//@Import(CustomSecurityConfig.class)
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Test
//    public void testInsetMember() {
//        // 근데 아래처럼 하면 서비스 로직 검증이 안 들어가서 보안 쌩까고 하는거임
//        Member member = Member.builder()
//                .userID("testUser0413")
//                .userName("testUser0413")
//                .password(passwordEncoder.encode("testPassword0413"))
//                .isSocial(false)
//
//                .build();
//
//        member.addRole(MemberRole.USER);
//
//        memberRepository.save(member);
//    } // end of this test

    @Test
    // 아래처럼 해야 서비스 로직 통과하는 거
    public void testRegisterMemberThroughService0413() {
        MemberRegisterDTO dto = new MemberRegisterDTO();
        dto.setUserID("testUser0413");
        dto.setUserName("테스트0413");
        dto.setPassword("testPassword0413");

        // 중복 확인
        Assertions.assertFalse(memberService.existsByUserID(dto.getUserID()));

        // 회원가입
        Member member = memberService.register(dto);

        // 검증
        Assertions.assertNotNull(member.getId());
        log.info("회원 ID 존재 확인 통과: {}", member.getId());

        Assertions.assertEquals(dto.getUserID(), member.getUserID());
        log.info("userID 일치 확인 통과");

        Assertions.assertEquals(dto.getUserName(), member.getUserName());
        log.info("userName 일치 확인 통과");

        Assertions.assertTrue(passwordEncoder.matches(dto.getPassword(), member.getPassword()));
        log.info("비밀번호 매칭 확인 통과");
    }

    @Test
    public void testRegisterMemberThroughService0415() {
        MemberRegisterDTO dto = new MemberRegisterDTO();
        dto.setUserID("testUser0415");
        dto.setUserName("테스트0415");
        dto.setPassword("testPassword0415");

        // 중복 확인
        Assertions.assertFalse(memberService.existsByUserID(dto.getUserID()));

        // 회원가입
        Member member = memberService.register(dto);

        // 검증
        Assertions.assertNotNull(member.getId());
        log.info("회원 ID 존재 확인 통과: {}", member.getId());

        Assertions.assertEquals(dto.getUserID(), member.getUserID());
        log.info("userID 일치 확인 통과");

        Assertions.assertEquals(dto.getUserName(), member.getUserName());
        log.info("userName 일치 확인 통과");

        Assertions.assertTrue(passwordEncoder.matches(dto.getPassword(), member.getPassword()));
        log.info("비밀번호 매칭 확인 통과");
    }

    // 이게 지금 안되는데 뭐가 안되는지 나중에 보자
    @Test
    @Transactional // 이거 적어놓으면 자동 롤백해서 그런ㄱ낙
    public void testDeleteMember() {

        Long memberId = 1L; // 삭제할 대상 member.id 값

        // 존재 여부 먼저 체크 (Optional 사용)
        Optional<Member> result = memberRepository.findById(memberId);

        if (result.isPresent()) {
            Member member = result.get();
            memberRepository.delete(member); // 연관된 todo, wallpaper도 자동 삭제됨
        }
    }

    @Test
    // 로그인용 아이디 조회 메서드 테스트
    public void testFindByUserID() {
        String userID = "testUser0413";

        Optional<Member> result = memberRepository.findByUserID(userID);
        Assertions.assertTrue(result.isPresent());

        Member member = result.get();
        log.info("조회된 회원: " + member.getUserName());

        Assertions.assertEquals(userID, member.getUserID());
    }

    // 회원가입 시에 이미 존재하는 유저아이디 인지 조회
    @Test
    public void testExistsByUserID() {
        String userID = "testUser0413";

        boolean exists = memberRepository.existsByUserID(userID);

        if (exists) {
            log.info("이미 존재하는 아이디입니다: {}", userID);
        } else {
            log.info("사용 가능한 아이디입니다: {}", userID);
        }

        Assertions.assertTrue(exists);
    }

    // 회원의 권한 조회
    @Test
    public void testGetWithRoles() {
        String userID = "testUser0413";

        Optional<Member> result = memberRepository.getWithRoles(userID);
        Assertions.assertTrue(result.isPresent());

        Member member = result.get();
        log.info("회원 권한 목록: {}", member.getMemberRoleList());

        Assertions.assertFalse(member.getMemberRoleList().isEmpty());
    }

    // 어드민 계정 넣기
    @Test
    public void registerAdminAccount() {
        MemberRegisterDTO dto = new MemberRegisterDTO();
        dto.setUserID("adminleekiye99");
        dto.setUserName("관리자");
        dto.setPassword("@q6jctr6wm");

        // 1. 회원가입
        Member member = memberService.register(dto);

        // 2. 관리자 권한 추가
        member.addRole(MemberRole.ADMIN);

        // 3. 저장 (JPA의 변경 감지를 확실히 하기 위해 save)
        memberRepository.save(member);

        // 4. 검증
        Assertions.assertTrue(member.getMemberRoleList().contains(MemberRole.ADMIN));
    }



    // 또 맴버에서 테스트 해볼반한게 뭐가있을가





}
