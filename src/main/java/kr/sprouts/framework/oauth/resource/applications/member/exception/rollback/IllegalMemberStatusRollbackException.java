package kr.sprouts.framework.oauth.resource.applications.member.exception.rollback;

import kr.sprouts.autoconfigure.response.base.BaseRollbackException;
import org.springframework.http.HttpStatus;

public class IllegalMemberStatusRollbackException extends BaseRollbackException {
    private IllegalMemberStatusRollbackException(String reason) {
        super("illegal_member_status", reason, HttpStatus.BAD_REQUEST);
    }

    public static IllegalMemberStatusRollbackException of(String action, String email, String currentStatus) {
        return new IllegalMemberStatusRollbackException(String.format("Unable to %s %s. Current status is %s.", action, email, currentStatus));
    }
}
