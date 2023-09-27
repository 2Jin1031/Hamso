package halo.hamso.dto.member;

import halo.hamso.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.relation.RelationType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private Long id;

    private String name; // 회원의 이름

    private String phoneNo; // 전화번호

    private RelationType type; // 관계

    private String affiliation; // 소속

    private Integer money; // 조의금

    public MemberDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.phoneNo = member.getPhoneNo();
        this.type = member.getType();
        this.affiliation = member.getAffiliation();
        this.money = member.getMoney();
    }
}
