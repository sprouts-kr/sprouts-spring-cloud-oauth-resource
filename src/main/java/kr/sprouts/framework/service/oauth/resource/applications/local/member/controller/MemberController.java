package kr.sprouts.framework.service.oauth.resource.applications.local.member.controller;

import kr.sprouts.framework.autoconfigure.web.response.components.body.link.LinkBuilder;
import kr.sprouts.framework.autoconfigure.web.response.components.entity.StructuredResponse;
import kr.sprouts.framework.autoconfigure.web.response.components.entity.StructuredResponseEntity;
import kr.sprouts.framework.service.oauth.resource.applications.local.member.dto.proxy.MemberProxy;
import kr.sprouts.framework.service.oauth.resource.applications.local.member.dto.request.MemberCreateRequest;
import kr.sprouts.framework.service.oauth.resource.applications.local.member.dto.request.MemberPasswordChangeRequest;
import kr.sprouts.framework.service.oauth.resource.applications.local.member.dto.request.MemberUpdateRequest;
import kr.sprouts.framework.service.oauth.resource.applications.local.member.dto.request.MemberVerificationRequest;
import kr.sprouts.framework.service.oauth.resource.applications.local.member.dto.response.MemberResponse;
import kr.sprouts.framework.service.oauth.resource.applications.local.member.dto.response.MemberVerificationResponse;
import kr.sprouts.framework.service.oauth.resource.applications.local.member.service.MemberCommandService;
import kr.sprouts.framework.service.oauth.resource.applications.local.member.service.MemberQueryService;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = MemberController.REQUEST_PATH)
public class MemberController {
    static final String REQUEST_PATH = "members";

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    public MemberController(MemberCommandService memberCommandService, MemberQueryService memberQueryService) {
        this.memberCommandService = memberCommandService;
        this.memberQueryService = memberQueryService;
    }

    @GetMapping
    public StructuredResponseEntity findAll() {
        return StructuredResponse.succeeded(
                memberQueryService.findAll().stream()
                        .map(MemberResponse::fromProxy)
                        .toList()
        );
    }

    @GetMapping(value = "/{id}")
    public StructuredResponseEntity findById(@PathVariable(value = "id") UUID id) {
        return StructuredResponse.succeeded(MemberResponse.fromProxy(
                memberQueryService.findById(id)
        ));
    }

    @PostMapping
    public StructuredResponseEntity create(@RequestBody MemberCreateRequest request) {
        return StructuredResponse.created(MemberResponse.fromProxy(
                memberCommandService.create(
                        request.getEmail(),
                        request.getName(),
                        request.getPassword(),
                        request.getDescription()
                )
        )).withLocation(response -> URI.create(String.format("%s/%s", LinkBuilder.getDefaultHost(), response.getId())));
    }

    @PatchMapping(value = "/{id}")
    public StructuredResponseEntity update(@PathVariable(value = "id") UUID id, @RequestBody MemberUpdateRequest request) {
        memberCommandService.update(id, request.getName(), request.getDescription());

        return StructuredResponse.succeeded(MemberResponse.fromProxy(
                memberQueryService.findById(id)
        ));
    }

    @PatchMapping(value = "/{email}/password")
    public StructuredResponseEntity changePassword(@PathVariable String email, @RequestBody MemberPasswordChangeRequest request) {
        memberCommandService.changePassword(email, request.getCurrentPassword(), request.getNewPassword());

        return StructuredResponse.succeeded(MemberResponse.fromProxy(
                memberQueryService.findByEmail(email)
        ));
    }

    @PatchMapping(value = "/{id}/activate")
    public StructuredResponseEntity activate(@PathVariable(value = "id") UUID id) {
        memberCommandService.activate(id);

        return StructuredResponse.succeeded(MemberResponse.fromProxy(
                memberQueryService.findById(id)
        ));
    }

    @PatchMapping(value = "/{id}/deactivate")
    public StructuredResponseEntity deactivate(@PathVariable(value = "id") UUID id) {
        memberCommandService.deactivate(id);

        return StructuredResponse.succeeded(MemberResponse.fromProxy(
                memberQueryService.findById(id)
        ));
    }

    @PatchMapping(value = "/{id}/block")
    public StructuredResponseEntity block(@PathVariable(value = "id") UUID id) {
        memberCommandService.block(id);

        return StructuredResponse.succeeded(MemberResponse.fromProxy(
                memberQueryService.findById(id)
        ));
    }

    @DeleteMapping(value = "/{id}")
    public StructuredResponseEntity delete(@PathVariable(value = "id") UUID id) {
        memberCommandService.delete(id);

        return StructuredResponse.deleted();
    }

    @PostMapping(value = "/verification")
    public StructuredResponseEntity verification(@RequestBody MemberVerificationRequest request) {
        Pair<Boolean, Optional<MemberProxy>> result
                = memberQueryService.verification(request.getEmail(), request.getPassword());

        return StructuredResponse.succeeded((result.getFirst() && result.getSecond().isPresent()) ?
                MemberVerificationResponse.succeeded(result.getSecond().get()) : MemberVerificationResponse.failed()
        );
    }
}
