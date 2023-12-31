package kr.sprouts.framework.service.oauth.resource.applications.local.member.exception.commit;

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
