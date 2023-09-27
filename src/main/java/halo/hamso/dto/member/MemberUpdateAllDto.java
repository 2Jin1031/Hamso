package halo.hamso.dto.member;

import lombok.Data;

import javax.management.relation.RelationType;

@Data
public class MemberUpdateAllDto {

    private String phoneNo; // 전화번호

    private RelationType type; // 관계

    private String affiliation; // 소속

    private Integer money; // 조의금
}
