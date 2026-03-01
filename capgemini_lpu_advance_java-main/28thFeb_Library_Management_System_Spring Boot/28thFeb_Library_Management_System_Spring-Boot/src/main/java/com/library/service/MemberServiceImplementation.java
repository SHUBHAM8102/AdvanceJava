package com.library.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.entity.Member;
import com.library.repository.MemberRepository;

import jakarta.transaction.Transactional;
@Service
public class MemberServiceImplementation implements MemberService{
	
	@Autowired
	private  MemberRepository memberRepository;


	@Override
	public Member addMember(Member memberDetails) {
		memberDetails.setMemberSince(LocalDate.now());
        memberDetails.setStatus("ACTIVE");

        return memberRepository.save(memberDetails);
	}

	@Override
	public Member getMemberById(Long memberId) {
		return memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));
	}

	@Override
	public List<Member> getAllMembers() {
		 return memberRepository.findAll();
	}

	@Override
	@Transactional
	public Member updateMember(Long memberId, Member updatedDetails) {
		Member existingMember = getMemberById(memberId);
        existingMember.setName(updatedDetails.getName());
        existingMember.setEmail(updatedDetails.getEmail());
        existingMember.setPhone(updatedDetails.getPhone());
        existingMember.setStatus(updatedDetails.getStatus());
        return memberRepository.save(existingMember);
	}

	@Override
	@Transactional
	public Member deleteOrDisableMember(Long memberId) {
		Member member = getMemberById(memberId);
        member.setStatus("BLOCKED");
        return memberRepository.save(member);
	}

}
