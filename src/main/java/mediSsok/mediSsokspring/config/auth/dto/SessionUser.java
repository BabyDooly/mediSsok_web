package mediSsok.mediSsokspring.config.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mediSsok.mediSsokspring.domain.entity.member.Member;

import javax.persistence.Column;
import java.io.Serializable;

// 세년정보 저정 dto 클래스
@Getter
@Setter
@NoArgsConstructor
public class SessionUser implements Serializable {
    private String email;

    private String nickname;

    private String phone;

    public SessionUser(Member member) {
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.phone = member.getPhone();
    }
}
