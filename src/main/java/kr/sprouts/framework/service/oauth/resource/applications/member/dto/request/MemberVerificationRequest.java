package kr.sprouts.framework.service.oauth.resource.applications.member.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class MemberVerificationRequest {
    private String email;
    private String password;
}
