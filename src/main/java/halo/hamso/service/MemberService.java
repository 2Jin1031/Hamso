package halo.hamso.service;

import halo.hamso.domain.Member;
import halo.hamso.dto.member.MemberDto;
import halo.hamso.dto.member.MemberUpdateAllDto;
import halo.hamso.exception.NotFoundException;
import halo.hamso.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

//    public MemberDto findById(Long id){
//        Member member = memberRepository.findById(id).get();
//        MemberDto memberDto = new MemberDto(member);
//        return memberDto;
//    }

    public MemberDto findByPhoneNo(String phoneNo){
        Optional<Member> member = memberRepository.findByPhoneNo(phoneNo);
        if(member.isEmpty()) {
            // Member not found, handle accordingly
            throw new NotFoundException("Member not found with phoneNo: " + phoneNo);
        } else {
            return new MemberDto(member.get());
        }
    }

    public Member findByPhoneNoForReal(String phoneNo){
        Optional<Member> member = memberRepository.findByPhoneNo(phoneNo);
        if(member.isEmpty()) {
            // Member not found, handle accordingly
            throw new NotFoundException("Member not found with phoneNo: " + phoneNo);
        } else {
            return member.get();
        }
    }

    public List<MemberDto> findAll() {
        List<Member> all = memberRepository.findAll();
        List<MemberDto> memberDtoList = convertMemberToMemberDto(all);
        return memberDtoList;
    }

    @Transactional
    public void updateMemberAll(MemberUpdateAllDto memberInfo) {
        Optional<Member> oMember = memberRepository.findByPhoneNo(memberInfo.getPhoneNo());
        if(oMember.isEmpty()) {
            // Member not found, handle accordingly
            throw new NotFoundException("Member not found with phoneNo: " + memberInfo.getPhoneNo());
        } else {
            Member member = oMember.get();
            member.setPhoneNo(memberInfo.getPhoneNo());
            member.setType(memberInfo.getType());
            member.setAffiliation(memberInfo.getAffiliation());
            member.setMoney(memberInfo.getMoney());
        }

    }

    @Transactional
    public void deleteMember(String phoneNo) {
        Optional<Member> oMember = memberRepository.findByPhoneNo(phoneNo);
        if(oMember.isEmpty()) {
            // Member not found, handle accordingly
            throw new NotFoundException("Member not found with phoneNo: " + phoneNo);
        } else {
            Member member = oMember.get();
            memberRepository.delete(member);
        }
    }

//    @Transactional
//    public void updatePassword(MemberLoginDto memberInfo) {
//        Optional<Member> oMember = memberRepository.findByPhoneNo(memberInfo.getPhoneNo());
//        if(oMember.isEmpty()) {
//            throw new NotFoundException("Member not found with phoneNo: " + memberInfo.getPhoneNo());
//        } else {
//            oMember.get().setPassword(memberInfo.getPassword());
//        }
//    }

    private static List<MemberDto> convertMemberToMemberDto(List<Member> memberList) {
        List<MemberDto> memberDtoList = memberList.stream()
                .map(a -> new MemberDto(a))
                .collect(Collectors.toList());
        return memberDtoList;
    }

    public boolean checkPhoneNoDuplicate(String phoneNo) {
        int cnt = memberRepository.getCountPhoneNo(phoneNo);
        if(cnt>0) {
            return true;
        }
        return false;
    }

    @Transactional
    public Boolean updatePassword(String phoneNo, String oldPassword, String newPassword) {
        Optional<Member> oMember = memberRepository.findByPhoneNo(phoneNo);
        if(oMember.isEmpty()) {
            throw new NotFoundException("Member not found with phoneNo: " + phoneNo);
        }
        Member member = oMember.get();

        if(encoder.matches(oldPassword, member.getPassword())) {
            member.setPassword(encoder.encode(newPassword));
        } else {
            throw new NotFoundException("비밀 번호가 일치하지 않습니다." );
        }
        return true;
    }
}
