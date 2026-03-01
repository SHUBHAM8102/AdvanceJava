package com.library.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.entity.Member;
import com.library.service.MemberService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

	private final MemberService memberService;
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@PostMapping
	public Member addMember(@RequestBody Member member) {
		return memberService.addMember(member);
	}

	@GetMapping("/{id}")
	public Member getMemberById(@PathVariable Long id) {
		return memberService.getMemberById(id);
	}

	@GetMapping
	public List<Member> getAllMembers() {
		return memberService.getAllMembers();
	}

	@PutMapping("/{id}")
	public Member updateMember(@PathVariable Long id, @RequestBody Member member) {
		return memberService.updateMember(id, member);
	}

	@DeleteMapping("/{id}")
	public String deleteMember(@PathVariable Long id) {
		memberService.deleteOrDisableMember(id);
		return "Member disable successfully";
	}
}
