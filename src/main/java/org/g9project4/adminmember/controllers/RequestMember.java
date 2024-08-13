package org.g9project4.adminmember.controllers;

import lombok.Data;
import org.g9project4.member.constants.Authority;
import org.g9project4.member.entities.Authorities;

import java.util.List;

@Data
public class RequestMember {
    public String mode = "write";
    private String email;
    private String userName;
    private String password;
    private List<Authorities> userType;

}
