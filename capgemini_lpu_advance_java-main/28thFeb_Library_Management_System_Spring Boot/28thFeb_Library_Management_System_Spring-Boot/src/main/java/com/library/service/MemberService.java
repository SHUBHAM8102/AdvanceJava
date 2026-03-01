package com.library.service;

import java.util.List;

import com.library.entity.Member;

public interface MemberService {
	Member addMember(Member memberDetails);
	Member getMemberById(Long memberId);
	List<Member> getAllMembers();
	Member updateMember(Long memberId, Member updatedDetails);
	Member deleteOrDisableMember(Long memberId);

}
