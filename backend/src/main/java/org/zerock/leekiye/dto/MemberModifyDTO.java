package org.zerock.leekiye.dto;

import lombok.Data;

@Data
public class MemberModifyDTO {

    private String userID;

    private String userName;

    private String password;

    private boolean isSocial;
}
