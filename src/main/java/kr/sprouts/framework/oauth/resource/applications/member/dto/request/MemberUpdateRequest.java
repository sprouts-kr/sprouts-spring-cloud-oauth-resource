package kr.sprouts.framework.oauth.resource.applications.member.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberUpdateRequest {
    private String name;
    private String description;
}
