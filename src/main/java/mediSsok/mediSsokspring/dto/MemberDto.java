package mediSsok.mediSsokspring.dto;


import lombok.*;
import mediSsok.mediSsokspring.domain.entity.MemberEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDto {
    private String nickname;
    private String email;
    private String password;
    private String phoneNum;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String passwordConfirm;

    public MemberEntity toEntity(){
        return MemberEntity.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .phoneNum(phoneNum)
                .build();
    }

    @Builder
    public MemberDto(String email, String password, String nickname, String phoneNum) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.phoneNum = phoneNum;
    }
}