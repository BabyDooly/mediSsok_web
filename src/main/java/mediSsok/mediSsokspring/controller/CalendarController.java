package mediSsok.mediSsokspring.controller;

import lombok.RequiredArgsConstructor;
import mediSsok.mediSsokspring.config.CustomUserDetails;
import mediSsok.mediSsokspring.dto.member.LinkInfoResponseDto;
import mediSsok.mediSsokspring.dto.member.MemberResponseDto;
import mediSsok.mediSsokspring.dto.schedule.CalendarResponseDto;
import mediSsok.mediSsokspring.dto.schedule.CalendarUpdateRequestDto;
import mediSsok.mediSsokspring.service.MemberService;
import mediSsok.mediSsokspring.service.ScheduleDateService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor    //final 필드 생성자 생성
public class CalendarController {
    private final MemberService memberService;
    private final ScheduleDateService scheduleDateService;

    /*---- 스케줄 ----*/

    // 캘린더
    @GetMapping("/status")
    public String dispStatus(@AuthenticationPrincipal CustomUserDetails userDetails, Model model,
                            @RequestParam(defaultValue="0") Long memberId, @RequestParam(defaultValue="0") Long linkId) {
        MemberResponseDto myDto = memberService.findByEmail(userDetails.getUsername());
        List<LinkInfoResponseDto> linkListDto = scheduleDateService.linkFind(userDetails.getMember().getId());
        LinkInfoResponseDto memberDto;

        LinkInfoResponseDto dto = LinkInfoResponseDto.builder()
                .id(0L)
                .userEmail(myDto.getEmail())
                .myEmail(myDto.getEmail())
                .nickname("나")
                .permit(true)
                .picture(myDto.getPicture())
                .userid(myDto.getId())
                .build();

        linkListDto.add(0, dto);

        if ((linkId == 0 && userDetails.getMember().getId() == memberId) || memberId == 0 && linkId == 0)
            memberDto = dto;
        else if(linkId == 0)
            return "index";
        else
            memberDto = memberService.linkFindById(linkId);


        model.addAttribute("linkList", linkListDto);
        model.addAttribute("member", memberDto);

        if ((linkId == 0 && userDetails.getMember().getId() == memberId))
            return "redirect:/status";
        else
            return "Medi_status/mediStat";
    }


    // 캘린더 리스트 전송(GET)
    @GetMapping("/api/status/list")
    @ResponseBody
    public List<CalendarResponseDto> calendarMyList(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return scheduleDateService.calendarList(userDetails.getMember().getId());
    }

    // 캘린더 링크 리스트 전송(GET)
    @GetMapping("/api/status/list/{id}")
    @ResponseBody
    public List<CalendarResponseDto> calendarLinkList(@PathVariable Long id) {
        return scheduleDateService.calendarList(id);
    }

    // 캘린더 일정 수정(POST)
    @PostMapping("/api/status/update/{id}")
    @ResponseBody
    public Long calendarUpdate(@PathVariable Long id, @RequestBody CalendarUpdateRequestDto requestDto){
        return scheduleDateService.calendarUpdate(id, requestDto);
    }
}
