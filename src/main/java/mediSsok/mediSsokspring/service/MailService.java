package mediSsok.mediSsokspring.service;

import lombok.RequiredArgsConstructor;
import mediSsok.mediSsokspring.domain.entity.member.Member;
import mediSsok.mediSsokspring.domain.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import mediSsok.mediSsokspring.dto.member.MailVo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor    //final 필드 생성자 생성
public class MailService {

    @Autowired
    MemberRepository userRepository;
    private final JavaMailSender mailSender;
    private static final String title = "YakSsok 임시 비밀번호 안내 이메일입니다.";
    private static final String message = "안녕하세요. YakSsok 임시 비밀번호 안내 메일입니다. "
            +"\n" + "회원님의 임시 비밀번호는 아래와 같습니다. 로그인 후 반드시 비밀번호를 변경해주세요."+"\n";
    private static final String fromAddress = "\n" + "yaksokmaster@gmail.com";
//    private final MemberRepository userRepository;


    /** 이메일이 존재하는지 확인 **/
    public boolean checkEmail(String memberEmail) {
        /* 이메일이 존재하면 true, 이메일이 없으면 false  */
        return userRepository.existsByEmail(memberEmail);
    }

    /** 임시 비밀번호 생성 **/
    public String getTmpPassword() {
        char[] charSet = new char[]{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        String pwd = "";

        /* 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 조합 */
        int idx = 0;
        for(int i = 0; i < 10; i++){
            idx = (int) (charSet.length * Math.random());
            pwd += charSet[idx];
        }
        return pwd;
    }

    /** 임시 비밀번호로 업데이트 **/
    public void updatePassword(String tmpPassword, String memberEmail) {
        Member member = userRepository.findByEmail(memberEmail).orElseThrow(() ->
                new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));
        member.updatePassword(tmpPassword);
    }

    /** 이메일 생성 **/
    public MailVo createMail(String tmpPassword, String memberEmail) {
        MailVo mailVo = MailVo.builder()
                .toAddress(memberEmail)
                .title(title)
                .message(message + tmpPassword)
                .fromAddress(fromAddress)
                .build();
        return mailVo;
    }

    /** 이메일 전송 **/
    public void sendMail(MailVo mailVo) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mailVo.getToAddress());
        mailMessage.setSubject(mailVo.getTitle());
        mailMessage.setText(mailVo.getMessage());
        mailMessage.setFrom(mailVo.getFromAddress());
        mailMessage.setReplyTo(mailVo.getFromAddress());

        mailSender.send(mailMessage);
    }
}

