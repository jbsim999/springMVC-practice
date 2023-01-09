package com.feb.member.service;

import com.feb.exception.BusinessLogicException;
import com.feb.exception.ExceptionCode;
import com.feb.member.entity.Member;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    public Member createMember(Member member){

        Member createdMember = member;
        return createdMember;
    }

    public Member updateMember(Member member){
        return member;
    }

    public Member findMember(long memberId){

        Member member = new Member(memberId,"hgd@email.com", "홍길동", "010-1234-1234");
        return member;
    }

    public List <Member> findMembers(){
        // TODO should business logic

        throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
    }

    public void deleteMember(long memberId){

    }
}
