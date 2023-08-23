package kr.sprouts.framework.service.oauth.resource.applications.member.service;

import kr.sprouts.framework.autoconfigure.web.response.components.base.BaseCommitException;
import kr.sprouts.framework.service.oauth.resource.applications.member.dto.proxy.MemberProxy;
import kr.sprouts.framework.service.oauth.resource.applications.member.entity.Member;
import kr.sprouts.framework.service.oauth.resource.applications.member.enumeration.MemberStatus;
import kr.sprouts.framework.service.oauth.resource.applications.member.exception.commit.NotAuthorizedCommitException;
import kr.sprouts.framework.service.oauth.resource.applications.member.exception.rollback.AlreadyExistsEmailRollbackException;
import kr.sprouts.framework.service.oauth.resource.applications.member.exception.rollback.MemberNotFoundRollbackException;
import kr.sprouts.framework.service.oauth.resource.applications.member.repository.MemberCommandRepository;
import kr.sprouts.framework.service.oauth.resource.applications.member.repository.MemberQueryRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@Transactional(noRollbackFor = { BaseCommitException.class })
public class MemberCommandService {
    private final PasswordEncoder passwordEncoder;
    private final MemberCommandRepository memberCommandRepository;
    private final MemberQueryRepository memberQueryRepository;

    public MemberCommandService(PasswordEncoder passwordEncoder, MemberCommandRepository memberCommandRepository, MemberQueryRepository memberQueryRepository) {
        this.passwordEncoder = passwordEncoder;
        this.memberCommandRepository = memberCommandRepository;
        this.memberQueryRepository = memberQueryRepository;
    }

    public MemberProxy create(String email, String name, String password, String description) {
        memberQueryRepository.findByEmail(email).ifPresent(member -> {
            throw AlreadyExistsEmailRollbackException.of(member.getEmail());
        });

        return MemberProxy.fromEntity(memberCommandRepository.save(
                Member.builder()
                        .email(email)
                        .name(name)
                        .password(passwordEncoder.encode(password))
                        .passwordExpired(Boolean.FALSE)
                        .passwordExpireDate(LocalDateTime.now().plusMonths(3).format(DateTimeFormatter.ISO_LOCAL_DATE))
                        .status(MemberStatus.PENDING_APPROVAL)
                        .description(description)
                        .build())
        );
    }

    public void update(UUID id, String name, String description) {
        memberQueryRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundRollbackException(id))
                .name(name)
                .description(description);
    }

    public void activate(UUID id) {
        memberQueryRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundRollbackException(id))
                .activate();
    }

    public void deactivate(UUID id) {
        memberQueryRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundRollbackException(id))
                .deactivate();
    }

    public void block(UUID id) {
        memberQueryRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundRollbackException(id))
                .block();
    }

    public void delete(UUID id) {
        memberCommandRepository.deleteById(id);
    }

    public void changePassword(String email, String currentPassword, String newPassword) {
        Member member = memberQueryRepository.findByEmail(email)
                .orElseThrow(NotAuthorizedCommitException::of);

        if (Boolean.FALSE.equals(passwordEncoder.matches(currentPassword, member.getPassword()))) {
            throw NotAuthorizedCommitException.of();
        }

        member.password(passwordEncoder.encode(newPassword));
    }
}
