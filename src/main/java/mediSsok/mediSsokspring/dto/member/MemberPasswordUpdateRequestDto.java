package mediSsok.mediSsokspring.dto.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MemberPasswordUpdateRequestDto {

    // 기존 비밀번호
    private String nowPassword;
    // 변경할 비밀번호
    private String newPassword;
}
