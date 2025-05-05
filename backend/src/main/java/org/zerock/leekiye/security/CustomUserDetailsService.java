package org.zerock.leekiye.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerock.leekiye.domain.Member;
import org.zerock.leekiye.dto.MemberDTO;
import org.zerock.leekiye.repository.MemberRepository;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Log4j2
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userID) throws UsernameNotFoundException {
        log.info("CustomUserDetailsService - LoadByUserID: " + userID);

        // 이부분 리팩토링
        Member member = memberRepository.getWithRoles(userID)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new MemberDTO(
                member.getId(),
                member.getUserID(),
                member.getPassword(), // 이 부분이 로그인 시에 인코딩 비밀번호 매칭이 인되서 오류가 나는건가
                member.getUserName(),
                member.isSocial(),
                member.getMemberRoleList()
                        .stream()
                        .map(Enum::name)
                        .collect(Collectors.toList())
        );
    }
}
