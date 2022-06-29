package mediSsok.mediSsokspring.dto;


import lombok.*;
import mediSsok.mediSsokspring.domain.entity.Member;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberSaveResponseDto {
    private String email;
    private String password;
    private String nickname;
    private String phoneNum;

    @Builder
    public MemberSaveResponseDto(Member entity) {
        this.email = entity.getEmail();
        this.password = entity.getPassword();
        this.nickname = entity.getNickname();
        this.phoneNum = entity.getPhoneNum();
    }

    public Member toEntity(){
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .phoneNum(phoneNum)
                .build();
    }
}