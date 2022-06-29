package mediSsok.mediSsokspring.service;

import lombok.RequiredArgsConstructor;
import mediSsok.mediSsokspring.domain.entity.Member;
import mediSsok.mediSsokspring.domain.repository.MemberRepository;
import mediSsok.mediSsokspring.dto.MemberResponseDto;
import mediSsok.mediSsokspring.dto.MemberSaveResponseDto;
import mediSsok.mediSsokspring.dto.MemberUpdateRequestDto;
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
    public String joinUser(MemberSaveResponseDto memberDto) {
        // 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        return memberRepository.save(memberDto.toEntity()).getNickname();
    }

    // 수정
    @Transactional
    public Long update(Long id, MemberUpdateRequestDto requestDto){
        Member entity = memberRepository.findById(id)
                // 아이디가 없을때
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id = " + id));

        entity.update(requestDto.getNickname(), requestDto.getPhoneNum());

        return id;
    }


    // 조회
    public MemberResponseDto findById (Long id){
        Member entity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id = " + id));

        return new MemberResponseDto(entity);
    }

    // 로그인(암호화)
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<Member> userEntityWrapper = memberRepository.findByEmail(userId);
        Member userEntity = userEntityWrapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();


        return new User(userEntity.getNickname(), userEntity.getPassword(), authorities);
    }

}