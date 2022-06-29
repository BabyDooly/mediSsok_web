package mediSsok.mediSsokspring.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MemberUpdateRequestDto {
    private String nickname;
    private String phoneNum;
    @Builder
    public MemberUpdateRequestDto(String nickname, String phoneNum) {
        this.nickname = nickname;
        this.phoneNum = phoneNum;
    }
}
