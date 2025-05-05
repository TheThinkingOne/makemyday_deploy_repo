package org.zerock.leekiye.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.leekiye.domain.Member;
import org.zerock.leekiye.dto.MemberDTO;
import org.zerock.leekiye.dto.MemberModifyDTO;
import org.zerock.leekiye.dto.MemberRegisterDTO;

import java.util.stream.Collectors;

@Transactional
public interface MemberService {

    MemberDTO getKakaoMember(String accessToken); // 카카오 계정 로그인

    Member register(MemberRegisterDTO memberRegisterDTO); // 일반 회원 가입

    void modifyMember(MemberModifyDTO memberModifyDTO); // 회원 정보 수정

    boolean existsByUserID(String userID); // 중복 확인

    // 일반 로그인 위한 loadByUserName 메소드 생성해야함
    UserDetails loadByUserName(String userID) throws UsernameNotFoundException;

    // Entity → DTO 변환
    default MemberDTO entityToDTO(Member member) {
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
}
