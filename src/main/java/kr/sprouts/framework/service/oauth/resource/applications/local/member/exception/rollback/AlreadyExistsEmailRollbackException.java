package kr.sprouts.framework.service.oauth.resource.applications.local.member.exception.rollback;

import kr.sprouts.framework.autoconfigure.web.response.components.base.BaseRollbackException;
import org.springframework.http.HttpStatus;

public class AlreadyExistsEmailRollbackException extends BaseRollbackException {
    private AlreadyExistsEmailRollbackException(String email) {
        super("already_exists_email", String.format("Already exists email. (%s)", email), HttpStatus.BAD_REQUEST);
    }

    public static AlreadyExistsEmailRollbackException of(String email) {
        return new AlreadyExistsEmailRollbackException(email);
    }
}
