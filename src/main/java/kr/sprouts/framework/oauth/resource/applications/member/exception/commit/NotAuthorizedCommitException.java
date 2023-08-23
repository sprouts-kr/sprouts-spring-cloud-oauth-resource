package kr.sprouts.framework.oauth.resource.applications.member.exception.commit;

import kr.sprouts.framework.autoconfigure.web.response.components.base.BaseCommitException;
import org.springframework.http.HttpStatus;

public class NotAuthorizedCommitException extends BaseCommitException {
    private NotAuthorizedCommitException() {
        super("not_authorized", "Not authorize.", HttpStatus.FORBIDDEN);
    }

    public static NotAuthorizedCommitException of() {
        return new NotAuthorizedCommitException();
    }
}
