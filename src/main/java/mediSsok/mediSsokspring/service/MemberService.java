package mediSsok.mediSsokspring.service;

import lombok.RequiredArgsConstructor;
import mediSsok.mediSsokspring.config.CustomUserDetails;
import mediSsok.mediSsokspring.config.auth.dto.SessionUser;
import mediSsok.mediSsokspring.domain.entity.member.Member;
import mediSsok.mediSsokspring.domain.repository.member.MemberRepository;
import mediSsok.mediSsokspring.dto.member.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private final HttpSession session;


    // 회원가입
    @Transactional
    public String save(MemberSaveResponseDto memberDto) {
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

        entity.userUpdate(requestDto.getNickname(), requestDto.getPhone());

        return entity.getId();
    }


    // 비밀번호 변경
    @Transactional
    public Long passwordUpdate(Long id, MemberPasswordUpdateRequestDto requestDto){
        Member entity = memberRepository.findById(id)
                // 아이디가 없을때
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id = " + id));

//        entity.update(requestDto.getNickname(), requestDto.getPhone());

        return id;
    }

    // 알림 설정
    @Transactional
    public Long alarmUpdate(String email, MemberAlarmUpdateRequestDto requestDto){
        Member entity = memberRepository.findByEmail(email)
                // 아이디가 없을때
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. email = " + email));

        entity.alarmUpdate(requestDto.getVibration(), requestDto.getPushAlarms(), requestDto.getLocationAlarms(), requestDto.getReplenishAlarms());

        return entity.getId();
    }

    // 이메일 조회
    public MemberResponseDto findByEmail (String userEmail){
        Member entity = memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. email = " + userEmail));

        return new MemberResponseDto(entity);
    }

    // 아이디 조회
    public MemberResponseDto findById (long id){
        Member entity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id = " + id));

        return new MemberResponseDto(entity);
    }

    // useremail이 DB에 있는지 확인
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(userEmail)
                .orElseThrow(() ->  new UsernameNotFoundException("해당 사용자가 존재하지 않습니다. email = " + userEmail));

        // 세션 저장
        session.setAttribute("member", new SessionUser(member));

        // 시큐리티 세션에 유저 정보 저장
        return new CustomUserDetails(member);
    }

    // 회원가입
    public void signUp(MemberResponseDto userDto) {
        // 회원 가입 비즈니스 로직 구현
    }

    /* 이메일이 존재하는지 확인 */
    public boolean checkEmail(String memberEmail) {
        /* 이메일이 존재하면 true, 이메일이 없으면 false  */
        return memberRepository.existsByEmail(memberEmail);
    }
}