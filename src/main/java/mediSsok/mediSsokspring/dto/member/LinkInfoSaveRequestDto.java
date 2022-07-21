package mediSsok.mediSsokspring.dto.member;


import lombok.*;
import mediSsok.mediSsokspring.domain.entity.member.LinkInfo;
import mediSsok.mediSsokspring.domain.entity.member.Member;


@Getter
@Setter
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LinkInfoSaveRequestDto {

    private String userEmail;
    private String nickname;
    private Long memberId;

    public LinkInfo toEntity(){
        return LinkInfo.builder()
                .userEmail(userEmail)
                .nickname(nickname)
                .member(Member.builder().id(memberId).build())
                .build();
    }
}