package com.feb.member.repository;

import com.feb.member.entity.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface MemberRepository extends PagingAndSortingRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
}
