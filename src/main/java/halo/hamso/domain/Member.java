package halo.hamso.domain;

import jakarta.persistence.*;
import halo.hamso.dto.member.MemberJoinDto;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.management.relation.RelationType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter @ToString
@Table(name = "member")
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "name")
    private String name; // 회원의 이름

    @Column(name = "phn", unique = true)
    private String phoneNo; // 전화번호(아이디)

    @Column(name = "pwd")
    private String password; // 비밀번호

    @Column(name="relation")
    private RelationType type; // 관계

    @Column(name = "affiliation")
    private String affiliation; // 소속

    @Column(name = "money")
    private Integer money; // 조의금

    public Member(MemberJoinDto memberInfo) {
        this.name = memberInfo.getName();
        this.phoneNo = memberInfo.getPhoneNo();
        this.password = memberInfo.getPassword();
        //this.type = memberInfo.g
        this.affiliation = memberInfo.getAffiliation();
        this.money = memberInfo.getMoney();
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return loginId;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Reservation> reservationList;
}
