package mediSsok.mediSsokspring.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MemberPasswordUpdateRequestDto {

    private String password;

    @Builder
    public MemberPasswordUpdateRequestDto(String password) {
        this.password = password;
    }
}
