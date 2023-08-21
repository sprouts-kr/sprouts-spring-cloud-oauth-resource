package kr.sprouts.framework.oauth.resource.applications.member.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class MemberCreateRequest {
    private String email;
    private String name;
    private String password;
    private String description;
}
