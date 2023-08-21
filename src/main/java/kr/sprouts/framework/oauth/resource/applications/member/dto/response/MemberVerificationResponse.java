package kr.sprouts.framework.oauth.resource.applications.member.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.sprouts.autoconfigure.response.base.BaseResponse;
import kr.sprouts.framework.oauth.resource.applications.member.dto.proxy.MemberProxy;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberVerificationResponse extends BaseResponse {
    private Boolean isVerification;
    private MemberResponse member;

    public static MemberVerificationResponse succeeded(MemberProxy memberProxy) {
        return new MemberVerificationResponse(Boolean.TRUE, MemberResponse.fromProxy(memberProxy));
    }

    public static MemberVerificationResponse failed() {
        return new MemberVerificationResponse(Boolean.FALSE, null);
    }
}
