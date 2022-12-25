package com.codestates.member.controller;

import com.codestates.member.Member;
import com.codestates.member.MemberPatchDto;
import com.codestates.member.MemberPostDto;
import com.codestates.member.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/v3/members")
@Validated
public class MemberController {

    public final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberPostDto memberPostDto){

        Member member =new Member();
        member.setEmail(memberPostDto.getEmail());
        member.setName(memberPostDto.getName());
        member.setPhone(memberPostDto.getPhone());

        Member response = memberService.createMember(member);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@PathVariable("member-id")@Min(1) long memberId,
                                      @Valid @RequestBody MemberPatchDto memberPatchDto){
        memberPatchDto.setMemberId(memberId);
//        memberPatchDto.setName("홍길동");

        Member member = new Member();
        member.setMemberId(memberPatchDto.getMemberId());
        member.setName(memberPatchDto.getName());
        member.setPhone(memberPatchDto.getPhone());

        Member response = memberService.updateMember(member);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id")long memberId){
//        System.out.println("# memberId : " + memberId);

        Member response = memberService.findMember(memberId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getMembers(){
//        System.out.println("# get Members");

        List<Member> response = memberService.findMembers();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id")long memberId){
        System.out.println("# delete member");

        memberService.deleteMember(memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
