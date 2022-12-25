package com.codestates.member.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
public class MemberDTO {
    @Email
    private String email;
    private String name;
    private String phone;


}
