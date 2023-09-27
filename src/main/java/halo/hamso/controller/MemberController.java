package halo.hamso.controller;

import halo.hamso.argumentresolver.Login;
import halo.hamso.domain.Member;
import halo.hamso.dto.member.MemberDto;
import halo.hamso.dto.member.MemberUpdateAllDto;
import halo.hamso.dto.member.UpdatePasswordDto;
import halo.hamso.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{phoneNo}")
    public ResponseEntity<MemberDto> getMemberByPhoneNo(@PathVariable("phoneNo") String PhoneNo) {
        MemberDto member = memberService.findByPhoneNo(PhoneNo);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{phoneNo}")
    public ResponseEntity<Void> deleteMemberByPhoneNo(@PathVariable("phoneNo") String phoneNo) {
        memberService.deleteMember(phoneNo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/update-all")
    public ResponseEntity<Void> updateMemberAll(@RequestBody MemberUpdateAllDto memberInfo) {
        memberService.updateMemberAll(memberInfo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/update-password")
    public ResponseEntity<Boolean> updatePassword(@Login Member loginMember, @RequestBody UpdatePasswordDto updatePasswordDto) {
        Boolean success = memberService.updatePassword(loginMember.getPhoneNo(), updatePasswordDto.getOldPassword(), updatePasswordDto.getNewPassword());
        return new ResponseEntity<>(success, HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity<MemberDto> getMemberInfo(@Login Member loginMember) {
        return new ResponseEntity<>(new MemberDto(loginMember), HttpStatus.OK);
    }
}
