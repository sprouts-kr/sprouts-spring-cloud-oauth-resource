package kr.sprouts.framework.service.oauth.resource.applications.local.member.dto.proxy;

import kr.sprouts.framework.service.oauth.resource.applications.local.member.entity.Member;
import kr.sprouts.framework.service.oauth.resource.applications.local.member.enumeration.MemberStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberProxy {
    private UUID id;
    private String email;
    private String name;
    private String password;
    private Boolean passwordExpired;
    private String passwordExpireDate;
    private MemberStatus status;
    private String description;

    private LocalDateTime createdOn;
    private UUID createdBy;
    private LocalDateTime lastModifiedOn;
    private UUID lastModifiedBy;

    public static MemberProxy fromEntity(Member member) {
        return new MemberProxy(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getPassword(),
                member.getPasswordExpired(),
                member.getPasswordExpireDate(),
                member.getStatus(),
                member.getDescription(),
                member.getCreatedOn(),
                member.getCreatedBy(),
                member.getLastModifiedOn(),
                member.getLastModifiedBy()
        );
    }
}
