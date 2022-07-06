package mediSsok.mediSsokspring.dto.member;


import lombok.*;
import mediSsok.mediSsokspring.domain.entity.member.Member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberResponseDto {


    private String email;
    private String password;
    private String nickname;


    private Long id;
    private String phone;
    private Boolean vibration;
    private Boolean pushAlarms;
    private Boolean locationAlarms;
    private Boolean replenishAlarms;

    @Builder
    public MemberResponseDto(Member entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
        this.nickname = entity.getNickname();
        this.phone = entity.getPhone();
        this.vibration = entity.getVibration();
        this.pushAlarms = entity.getPushAlarms();
        this.locationAlarms = entity.getLocationAlarms();
        this.replenishAlarms = entity.getReplenishAlarms();
    }
}