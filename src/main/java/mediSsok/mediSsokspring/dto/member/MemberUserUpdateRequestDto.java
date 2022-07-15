package mediSsok.mediSsokspring.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MemberUserUpdateRequestDto {
    private String nickname;
    private String phone;
    @Builder
    public MemberUserUpdateRequestDto(String nickname, String phone) {
        this.nickname = nickname;
        this.phone = phone;
    }

}
