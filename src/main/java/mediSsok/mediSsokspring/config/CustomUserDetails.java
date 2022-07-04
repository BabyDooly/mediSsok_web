package mediSsok.mediSsokspring.config;

import lombok.Getter;
import lombok.ToString;
import mediSsok.mediSsokspring.domain.entity.member.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

// 로그인을 진행하고 완료되면 UserDetails 타입의 오브젝트를 고유한 세션 저장소에 저장
@Getter
@ToString
public class CustomUserDetails implements UserDetails, Serializable {

    private Member member;
    private Map<String, Object> attributes;
    private Collection<GrantedAuthority> authorities = new ArrayList<>();

    public CustomUserDetails(Member member) {
        this.member = member;
    }

    public CustomUserDetails(Member member, Map<String, Object> attributes) {
        this.member = member;
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.member.getPassword();
    }

    @Override
    public String getUsername() {
        return this.member.getEmail();
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

}