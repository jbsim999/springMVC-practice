package com.feb.member.controller;

import com.feb.member.mapper.MemberMapper;
import com.feb.member.dto.MemberPatchDto;
import com.feb.member.dto.MemberPostDto;
import com.feb.member.dto.MemberResponseDto;
import com.feb.member.entity.Member;
import com.feb.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/members")
@Validated
public class MemberController {
    private final MemberService memberService;

    private final MemberMapper mapper;

    public MemberController(MemberService memberService, MemberMapper mapper) {
        this.memberService = memberService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberPostDto memberPostDto){

        Member response = memberService.createMember(mapper.memberPostDtoToMember(memberPostDto));

        return new ResponseEntity<>(mapper.memberToMemberResponseDto(response), HttpStatus.CREATED);
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@PathVariable("member-id") @Positive long memberId,
                                      @Valid @RequestBody MemberPatchDto memberPatchDto){
        memberPatchDto.setMemberId(memberId);

        Member response = memberService.updateMember(mapper.memberPatchDtoToMember(memberPatchDto));

        return new ResponseEntity<>(mapper.memberToMemberResponseDto(response), HttpStatus.OK);
    }

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id")@Positive long memberId){
        Member response = memberService.findMember(memberId);
        return new ResponseEntity<>(mapper.memberToMemberResponseDto(response),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getMembers(){
        List<Member> members = memberService.findMembers();

        List<MemberResponseDto> response =
                members.stream()
                        .map(member -> mapper.memberToMemberResponseDto(member))
                        .collect(Collectors.toList());

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id")@Positive long memberId){
        memberService.deleteMember(memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
