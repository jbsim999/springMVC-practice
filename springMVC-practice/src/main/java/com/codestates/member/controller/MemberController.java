package com.codestates.member.controller;

import com.codestates.member.dto.MemberPatchDto;
import com.codestates.member.dto.MemberPostDto;
import com.codestates.member.dto.MemberResponseDto;
import com.codestates.member.entity.Member;
import com.codestates.member.service.MemberService;
import com.codestates.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

import com.codestates.member.mapper.MemberMapper;

@RestController
@RequestMapping("/v7/members")
@Validated
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper mapper;

    public MemberController(MemberService memberService, MemberMapper mapper) {
        this.memberService = memberService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberPostDto memberPostDto){

//        Member member =new Member();
//        member.setEmail(memberPostDto.getEmail());
//        member.setName(memberPostDto.getName());
//        member.setPhone(memberPostDto.getPhone());

        Member member = mapper.memberPostDtoToMember(memberPostDto);
        Member response = memberService.createMember(member);
        return new ResponseEntity<>(mapper.memberToMemberResponseDto(response), HttpStatus.CREATED);
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@PathVariable("member-id")@Min(1) long memberId,
                                      @Valid @RequestBody MemberPatchDto memberPatchDto){
        memberPatchDto.setMemberId(memberId);
//        memberPatchDto.setName("?????????");
//        Member member = new Member();
//        member.setMemberId(memberPatchDto.getMemberId());
//        member.setName(memberPatchDto.getName());
//        member.setPhone(memberPatchDto.getPhone());

        Member response = memberService.updateMember(mapper.memberPatchDtoToMember(memberPatchDto));
        return new ResponseEntity<>(mapper.memberToMemberResponseDto(response),HttpStatus.OK);
    }

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id")long memberId){
//        System.out.println("# memberId : " + memberId);

        Member response = memberService.findMember(memberId);
        return new ResponseEntity<>(mapper.memberToMemberResponseDto(response),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getMembers(){
//        System.out.println("# get Members");

        List<Member> members = memberService.findMembers();
        List<MemberResponseDto> response =
                members.stream()
                        .map(member -> mapper.memberToMemberResponseDto(member))
                        .collect(Collectors.toList());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id")long memberId){
        System.out.println("# delete member");
        memberService.deleteMember(memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
/*
    @ExceptionHandler
    public ResponseEntity handleException(MethodArgumentNotValidException e){
        final List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<ErrorResponse.FieldError> errors =
                fieldErrors.stream()
                        .map(fieldError -> new ErrorResponse.FieldError(
                                fieldError.getField(),
                                fieldError.getRejectedValue(),
                                fieldError.getDefaultMessage()))
                        .collect(Collectors.toList());
        return new ResponseEntity<>(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
    }
 */

}
