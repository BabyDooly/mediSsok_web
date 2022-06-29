package mediSsok.mediSsokspring.dto;


import lombok.*;
import mediSsok.mediSsokspring.domain.entity.Member;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String phoneNum;

    @Builder
    public MemberResponseDto(Member entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
        this.nickname = entity.getNickname();
        this.phoneNum = entity.getPhoneNum();
    }

}