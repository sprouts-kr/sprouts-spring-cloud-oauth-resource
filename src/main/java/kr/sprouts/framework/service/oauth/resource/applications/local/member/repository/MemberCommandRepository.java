package kr.sprouts.framework.service.oauth.resource.applications.local.member.repository;

import kr.sprouts.framework.service.oauth.resource.applications.local.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MemberCommandRepository extends JpaRepository<Member, UUID> { }
