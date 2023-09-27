package halo.hamso.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class MemberLoginDto {
    private String phoneNo; // 전화번호(아이디)
    private String password;
}
