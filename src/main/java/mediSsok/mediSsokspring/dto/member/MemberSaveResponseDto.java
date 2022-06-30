package mediSsok.mediSsokspring.dto.member;


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
    private String phone;

    @Builder
    public MemberSaveResponseDto(Member entity) {
        this.email = entity.getEmail();
        this.password = entity.getPassword();
        this.nickname = entity.getNickname();
        this.phone = entity.getPhone();
    }

    public Member toEntity(){
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .phone(phone)
                .build();
    }
}