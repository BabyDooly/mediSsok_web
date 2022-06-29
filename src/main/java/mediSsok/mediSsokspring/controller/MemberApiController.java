package mediSsok.mediSsokspring.controller;

import lombok.RequiredArgsConstructor;
import mediSsok.mediSsokspring.dto.MemberResponseDto;
import mediSsok.mediSsokspring.dto.MemberSaveResponseDto;
import mediSsok.mediSsokspring.dto.MemberUpdateRequestDto;
import mediSsok.mediSsokspring.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor    //final 필드 생성자 생성
public class MemberApiController {
    private final MemberService memberService;

    // 회원가입
    @PostMapping("/user/signup")
    public String Signup(MemberSaveResponseDto memberDto) {
        memberService.joinUser(memberDto);

        return "redirect:/user/login";
    }

    // 회원 조회
    @GetMapping("/api/v1/posts/{id}")
    public MemberResponseDto findById(@PathVariable Long id){
        return memberService.findById(id);
    }

    // 게시물 수정
    @PostMapping("/api/v1/posts/{id}")
    @ResponseBody
    public Long update(@PathVariable Long id, @RequestBody MemberUpdateRequestDto requestDto){
        return memberService.update(id, requestDto);
    }
}