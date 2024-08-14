package org.g9project4.adminmember.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.g9project4.member.constants.Authority;
import org.g9project4.member.entities.Authorities;

import java.util.List;

@Data
public class RequestMember {
    public Long seq;
    public String mode = "add";
    @NotBlank
    private String email;
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
    private String mobile;
    private String type = "ALL";

}
