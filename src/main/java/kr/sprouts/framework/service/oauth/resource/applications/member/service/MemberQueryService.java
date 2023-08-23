package kr.sprouts.framework.service.oauth.resource.applications.member.service;

import kr.sprouts.framework.service.oauth.resource.applications.member.dto.proxy.MemberProxy;
import kr.sprouts.framework.service.oauth.resource.applications.member.entity.Member;
import kr.sprouts.framework.service.oauth.resource.applications.member.exception.rollback.MemberNotFoundRollbackException;
import kr.sprouts.framework.service.oauth.resource.applications.member.repository.MemberQueryRepository;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MemberQueryService {
    private final PasswordEncoder passwordEncoder;
    private final MemberQueryRepository memberQueryRepository;

    public MemberQueryService(PasswordEncoder passwordEncoder, MemberQueryRepository memberQueryRepository) {
        this.passwordEncoder = passwordEncoder;
        this.memberQueryRepository = memberQueryRepository;
    }

    public List<MemberProxy> findAll() {
        return memberQueryRepository.findAll().stream()
                .map(MemberProxy::fromEntity)
                .collect(Collectors.toList());
    }

    public MemberProxy findById(UUID id) {
        return MemberProxy.fromEntity(memberQueryRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundRollbackException(id))
        );
    }

    public MemberProxy findByEmail(String email) {
        return MemberProxy.fromEntity(memberQueryRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundRollbackException(email))
        );
    }

    public Pair<Boolean, Optional<MemberProxy>> verification(String email, String password) {
        AtomicReference<Member> memberAtomicReference = new AtomicReference<>();

        memberQueryRepository.findByEmail(email).ifPresent(memberAtomicReference::set);

        return (memberAtomicReference.get() != null && passwordEncoder.matches(password, memberAtomicReference.get().getPassword())) ?
                Pair.of(Boolean.TRUE, Optional.of(MemberProxy.fromEntity(memberAtomicReference.get()))) : Pair.of(Boolean.FALSE, Optional.empty());
    }
}
