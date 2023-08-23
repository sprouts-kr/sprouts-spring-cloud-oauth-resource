package kr.sprouts.framework.oauth.resource.applications.member.exception.rollback;

import kr.sprouts.framework.autoconfigure.web.response.components.base.BaseRollbackException;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class MemberNotFoundRollbackException extends BaseRollbackException {
    public MemberNotFoundRollbackException(UUID id) {
        super("member_not_found", String.format("Member not found. (id: %s)", id.toString()), HttpStatus.BAD_REQUEST);
    }

    public MemberNotFoundRollbackException(String email) {
        super("member_not_found", String.format("Member not found. (email: %s)", email), HttpStatus.BAD_REQUEST);
    }
}
