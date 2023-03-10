package com.feb.member.mapper;

import com.feb.member.dto.MemberPatchDto;
import com.feb.member.dto.MemberPostDto;
import com.feb.member.dto.MemberResponseDto;
import com.feb.member.entity.Member;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member memberPostDtoToMember(MemberPostDto memberPostDto);
    Member memberPatchDtoToMember(MemberPatchDto memberPatchDto);
    MemberResponseDto memberToMemberResponseDto(Member member);
    List<MemberResponseDto> membersToMemberResponseDtos(List<Member> members);


}
