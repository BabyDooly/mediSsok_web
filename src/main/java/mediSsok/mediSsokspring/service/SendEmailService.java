package mediSsok.mediSsokspring.service;

import lombok.AllArgsConstructor;
import mediSsok.mediSsokspring.domain.entity.member.Member;
import mediSsok.mediSsokspring.domain.repository.member.MemberRepository;
import mediSsok.mediSsokspring.dto.member.MailDTO;
import mediSsok.mediSsokspring.dto.member.MemberPasswordUpdateRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@AllArgsConstructor
public class SendEmailService {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    private final JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "yaksokmaster@gmail.com";

    @Transactional
    // 메일 생성 및 비밀번호 조작 부분
    public MailDTO createMailAndChangePassword(String userEmail) {
        Member entity = memberRepository.findByEmail(userEmail)
                // 아이디가 없을때
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));

        // 메일 설정 부분
        MailDTO dto = new MailDTO();
        dto.setAddress(userEmail);
        String password = getTempPassword();
        dto.setTitle("YakSsok 임시비밀번호 안내 이메일 입니다.");
        dto.setMessage("안녕하세요. YakSsok 임시비밀번호 안내 관련 이메일 입니다. 임시 비밀번호는 "
                + password + " 입니다.");

        // 비밀번호 암호화 및 패스워드 업데이트 부분
        PasswordEncoder pe = new BCryptPasswordEncoder();
        String encodePassword = pe.encode(password);
        System.out.println("암호화 전 비밀번호: "+password);
        System.out.println("암호화 후 비밀번호: "+encodePassword);
        entity.updatePassword(encodePassword);
        return dto;
    }

    // 랜덤 난수를 통한 패스워드 생성 부분
    public String getTempPassword() {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        String str = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }

    // 메일 설정하는 곳
    public void mailSend(MailDTO mailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setFrom(SendEmailService.FROM_ADDRESS);
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());
        mailSender.send(message);
    }
}