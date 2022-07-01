package mediSsok.mediSsokspring.dto.member;


import lombok.*;
import mediSsok.mediSsokspring.domain.entity.member.Member;

@Getter
@Setter
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberSaveResponseDto {
//    @NotBlank(message = "이메일은 필수 입력 값입니다.")
//    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
    private String email;

//    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
//    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z]).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자를 사용하세요.")
    private String password;

//    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
//    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
    private String nickname;

//    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
//    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
    private String phone;

    public Member toEntity(){
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .phone(phone)
                .build();
    }
}