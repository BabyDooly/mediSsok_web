package mediSsok.mediSsokspring.service;

import lombok.RequiredArgsConstructor;
import mediSsok.mediSsokspring.domain.entity.Member;
import mediSsok.mediSsokspring.domain.repository.MemberRepository;
import mediSsok.mediSsokspring.dto.member.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor    //final 필드 생성자 생성
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    // 회원가입
    @Transactional
    public String save(MemberSaveResponseDto memberDto) {
        // 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        return memberRepository.save(memberDto.toEntity()).getEmail();
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

    // 로그인(암호화)
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        Optional<Member> userEntityWrapper = memberRepository.findByEmail(userEmail);
        Member entity = userEntityWrapper.get();
        List<GrantedAuthority> authorities = new ArrayList<>();

        return new User(entity.getEmail(), entity.getPassword(), authorities);
    }
}