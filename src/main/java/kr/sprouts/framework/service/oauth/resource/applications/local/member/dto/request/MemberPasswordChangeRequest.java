package kr.sprouts.framework.service.oauth.resource.applications.local.member.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberPasswordChangeRequest {
    private String currentPassword;
    private String newPassword;
}
