package mediSsok.mediSsokspring.dto.member;


import lombok.*;
import mediSsok.mediSsokspring.domain.entity.member.LinkInfo;
import mediSsok.mediSsokspring.domain.entity.member.Member;
import mediSsok.mediSsokspring.dto.medicineBox.MedicineBoxResponseDto;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LinkInfoResponseDto {
    private Long id;
    private String userEmail;
    private String nickname;
    private Boolean permit;
    private String picture;
    private String myEmail;
    private Long userid;


    @Builder
    public LinkInfoResponseDto(Long id, String userEmail, String nickname, Boolean permit, String picture, String myEmail, Long userid) {
        this.id = id;
        this.userEmail = userEmail;
        this.nickname = nickname;
        this.permit = permit;
        this.picture = picture;
        this.myEmail = myEmail;
        this.userid = userid;
    }

    @Builder
    public LinkInfoResponseDto(LinkInfo entity) {
        this.id = entity.getId();
        this.userEmail = entity.getUserEmail();
        this.nickname = entity.getNickname();
        this.permit = entity.getPermit();
        this.picture = entity.getMember().getPicture();
    }
}