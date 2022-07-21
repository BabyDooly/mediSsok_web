package mediSsok.mediSsokspring.service;

import lombok.RequiredArgsConstructor;
import mediSsok.mediSsokspring.config.CustomUserDetails;
import mediSsok.mediSsokspring.config.auth.dto.SessionUser;
import mediSsok.mediSsokspring.domain.entity.member.Member;
import mediSsok.mediSsokspring.domain.repository.member.LinkInfoRepository;
import mediSsok.mediSsokspring.domain.repository.member.MemberRepository;
import mediSsok.mediSsokspring.dto.member.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service
@RequiredArgsConstructor    //final 필드 생성자 생성
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    private final LinkInfoRepository linkInfoRepository;

    private final HttpSession session;


    /*---- 개인정보 ----*/

    // 회원가입
    @Transactional
    public String memberCreate(MemberSaveRequestDto memberDto) {
        // 비밀번호 암호화후 저장
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        return memberRepository.save(memberDto.toEntity()).getEmail();
    }

    /* 회원가입 시, 유효성 및 중복 검사 */
    @Transactional(readOnly = true)
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        // 유효성 검사에 실패한 필드 목록을 받음
        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

    // 개인정보 변경
    @Transactional
    public Long userUpdate(String email, MemberUserUpdateRequestDto requestDto){
        Member entity = memberRepository.findByEmail(email)
                // 아이디가 없을때
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. email = " + email));

        entity.userUpdate(requestDto.getNickname(), requestDto.getPhone(), requestDto.getPicture());
        return entity.getId();
    }

    // 비밀번호 변경
    @Transactional
    public Long passwordUpdate(String email, MemberPasswordUpdateRequestDto requestDto){
        Member entity = memberRepository.findByEmail(email)
                // 아이디가 없을때
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id = " + email));
        //비밀번호 암호화 시키는 부분
        PasswordEncoder pe = new BCryptPasswordEncoder();
        // 암호화된 비밀번호를 평문과 비교시 .matches 사용해야함 같으면 true 다르면 false;
        if(pe.matches(requestDto.getNowPassword(), entity.getPassword())){
            //비밀번호 변경 시키는 부분
            entity.updatePassword(pe.encode(requestDto.getNewPassword()));
            return entity.getId();
        }
        return null;
    }

    // 알림 설정
    @Transactional
    public Long alarmUpdate(String email, MemberAlarmUpdateRequestDto requestDto){
        Member entity = memberRepository.findByEmail(email)
                // 아이디가 없을때
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. email = " + email));
        entity.alarmUpdate(requestDto.getVibration(), requestDto.getPushAlarms(), requestDto.getLocationAlarms(), requestDto.getReplenishAlarms(), requestDto.getWorkAlarms());
        return entity.getId();
    }

    // 이메일 조회
    @Transactional
    public MemberResponseDto findByEmail (String userEmail){
        Member entity = memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. email = " + userEmail));

        return new MemberResponseDto(entity);
    }

    // userEmail DB에 있는지 확인
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(userEmail)
                .orElseThrow(() ->  new UsernameNotFoundException("해당 사용자가 존재하지 않습니다. email = " + userEmail));
        // 세션 저장
        session.setAttribute("member", new SessionUser(member));
        // 시큐리티 세션에 유저 정보 저장
        return new CustomUserDetails(member);
    }

    @Transactional
    // 유저 이메일 체크
    public boolean userEmailCheck(String userEmail) {
        boolean user = memberRepository.existsByEmail(userEmail);
        if(user){ return true; }
        else { return false; }
    }

    /*---- 연동 ----*/
    // 연동 생성
    @Transactional
    public Long linkCreate(LinkInfoSaveRequestDto requestDto) {
        return linkInfoRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    // 연동 중복 체크
    public boolean linkEmailCheck(String userEmail, Long memberId) {
        boolean user = linkInfoRepository.existsByUserEmailAndMemberId(userEmail, memberId);
        if(user){ return true; }
        else { return false; }
    }
}