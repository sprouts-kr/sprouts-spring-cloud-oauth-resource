package kr.sprouts.framework.service.oauth.resource.applications.local.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import kr.sprouts.framework.service.oauth.resource.applications.local.member.enumeration.MemberStatus;
import kr.sprouts.framework.service.oauth.resource.applications.local.member.exception.rollback.IllegalMemberStatusRollbackException;
import kr.sprouts.framework.service.oauth.resource.components.jpa.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Table(name = "members", uniqueConstraints = { @UniqueConstraint(columnNames = { "email", "deletedOn" })})
@EqualsAndHashCode(of = "id", callSuper = false)
@SQLDelete(sql = "UPDATE members SET deleted = 1, deleted_on = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted = false")
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Audited
@Builder
@Getter
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(nullable = false, length = 36)
    @Type(type = "uuid-char")
    private UUID id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(length = 2000)
    private String password;

    @Column(nullable = false)
    private Boolean passwordExpired;

    @Column(nullable = false)
    private String passwordExpireDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Column(length = 2000)
    private String description;

    public Member name(String name) {
        this.name = name;
        return this;
    }

    public Member description(String description) {
        this.description = description;
        return this;
    }

    public Member activate() {
        if (MemberStatus.PENDING_APPROVAL != this.status) {
            throw IllegalMemberStatusRollbackException.of("activate", this.getEmail(), this.status.toString());
        }

        this.status = MemberStatus.ACTIVE;
        return this;
    }

    public Member deactivate() {
        if (MemberStatus.ACTIVE != this.status) {
            throw IllegalMemberStatusRollbackException.of("deactivate", this.getEmail(), this.status.toString());
        }

        this.status = MemberStatus.DEACTIVATED;
        return this;
    }

    public Member block() {
        if (MemberStatus.BLOCKED == this.status) {
            throw IllegalMemberStatusRollbackException.of("block", this.getEmail(), this.status.toString());
        }

        this.status = MemberStatus.BLOCKED;
        return this;
    }

    public Member password(String password) {
        this.password = password;
        this.passwordExpired = Boolean.FALSE;
        this.passwordExpireDate = LocalDateTime.now().plusMonths(3).format(DateTimeFormatter.ISO_LOCAL_DATE);
        return this;
    }
}
