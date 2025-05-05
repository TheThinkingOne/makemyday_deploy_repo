//package org.zerock.leekiye.domain;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@ToString(exclude = "memberRoleList") // 연관관계 뺀 이유가 무엇일까
//public class Member {
//
//    // 카카오 로그인은 이메을 안됨
//    // 카카오는 닉네임이랑 프사만 불러올 수 있음
//    // ㅅㅂ 깃허브는 이메일로 넘어오는 것 같은데 어캐하지 깃허브는 하지말까
//    @Id
//    private String userID; // 이건 소셜 로그인 혹은 가입시에 설정하는 아이디
//    // 카톡 땜에 이메일로 할 수가 없어서 userID로 퉁쳐야 할듯
//
//    private String userName; // 이건 회원가입시에 설정하는 닉네임
//    // 카카오 로그인이면 넘어온 유저닉네임이 해당 유저의 닉네임으로 되야 할..것
//
//    private String password;
//
//    private boolean isSocial;
//
//    @ElementCollection(fetch = FetchType.LAZY)
//    @Builder.Default
//    private List<MemberRole> memberRoleList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
//    @Builder.Default
//    private List<Todo> todoList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
//    @Builder.Default
//    private List<WallPaper> wallPaperList = new ArrayList<>();
//
//
//
//    // 새로운 회원의 권한 추가
//    public void addRole(MemberRole memberRole) {
//        memberRoleList.add(memberRole);
//    }
//
//    // 권한 삭제/초기화
//    public void clearRole() {
//        memberRoleList.clear();
//    }
//
//    public void changeNickname(String userName) {
//        this.userName = userName;
//    }
//
//    public void changePw(String password) {
//        this.password = password;
//    }
//
//    public void changeSocial(boolean isSocial) {
//        this.isSocial = isSocial;
//    }
//
//}


// Refactored Code
package org.zerock.leekiye.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유 PK (기본 키)

    @Column(unique = true, nullable = false)
    private String userID; // 로그인용 ID (일반: 입력한 ID, 소셜: kakao_123456)

    private String userName; // 사용자 표시용 닉네임

    private String password;

    private boolean isSocial;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<MemberRole> memberRoleList = new ArrayList<>();

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Todo> todoList = new ArrayList<>();

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<WallPaper> wallPaperList = new ArrayList<>();

    public void addRole(MemberRole memberRole) {
        memberRoleList.add(memberRole);
    }

    public void clearRole() {
        memberRoleList.clear();
    }

    public void changeNickname(String userName) {
        this.userName = userName;
    }

    public void changePw(String password) {
        this.password = password;
    }

    public void changeSocial(boolean isSocial) {
        this.isSocial = isSocial;
    }


}
