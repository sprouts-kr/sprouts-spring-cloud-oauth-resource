package kr.sprouts.framework.oauth.resource.applications.member.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.sprouts.autoconfigure.response.base.BaseResponse;
import kr.sprouts.framework.oauth.resource.applications.member.dto.proxy.MemberProxy;
import kr.sprouts.framework.oauth.resource.applications.member.enumeration.MemberStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberResponse extends BaseResponse {
    private UUID id;
    private String email;
    private String name;
    private Boolean passwordExpired;
    private String passwordExpireDate;
    private MemberStatus status;
    private String description;

    private LocalDateTime createdOn;
    private UUID createdBy;
    private LocalDateTime lastModifiedOn;
    private UUID lastModifiedBy;

    public static MemberResponse fromProxy(MemberProxy proxy) {
        return new MemberResponse(
                proxy.getId(),
                proxy.getEmail(),
                proxy.getName(),
                proxy.getPasswordExpired(),
                proxy.getPasswordExpireDate(),
                proxy.getStatus(),
                proxy.getDescription(),
                proxy.getCreatedOn(),
                proxy.getCreatedBy(),
                proxy.getLastModifiedOn(),
                proxy.getLastModifiedBy()
        );
    }
}
