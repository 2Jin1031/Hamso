package halo.hamso.dto.member;

import halo.hamso.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.management.relation.RelationType;

@AllArgsConstructor
@NoArgsConstructor
@Getter @ToString
public class MemberJoinDto {
    private String name; // 회원의 이름

    private String phoneNo; // 전화번호

    private String password;

    private RelationType type; // 관계

    private String affiliation; // 소속

    private Integer money; // 조의금

    public MemberJoinDto(Member member) {
        this.name = member.getName();
        this.phoneNo = member.getPhoneNo();
        this.password = member.getPassword();
        this.affiliation = member.getAffiliation();
        this.money = member.getMoney();
    }

}
