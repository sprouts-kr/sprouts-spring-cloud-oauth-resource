package kr.sprouts.framework.service.oauth.resource.applications.local.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.sprouts.framework.service.oauth.resource.applications.local.member.entity.Member;
import kr.sprouts.framework.service.oauth.resource.applications.local.member.entity.QMember;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MemberQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public MemberQueryRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Member> findAll() {
        return jpaQueryFactory.selectFrom(QMember.member)
                .fetch();
    }

    public Optional<Member> findById(UUID id) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(QMember.member)
                .where(QMember.member.id.eq(id))
                .fetchOne()
        );
    }

    public Optional<Member> findByEmail(String email) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(QMember.member)
                .where(QMember.member.email.eq(email))
                .fetchOne()
        );
    }
}
