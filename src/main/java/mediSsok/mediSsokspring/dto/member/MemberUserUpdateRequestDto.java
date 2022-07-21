package mediSsok.mediSsokspring.dto.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MemberUserUpdateRequestDto {
    private String nickname;
    private String phone;
    private String picture;
}