package mediSsok.mediSsokspring.Validation;

import mediSsok.mediSsokspring.dto.member.MemberSaveRequestDto;
import lombok.RequiredArgsConstructor;
import mediSsok.mediSsokspring.domain.repository.member.MemberRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckNicknameValidator extends AbstractValidator<MemberSaveRequestDto> {

    private final MemberRepository userRepository;

    @Override
    protected void doValidate(MemberSaveRequestDto dto, Errors errors) {
        if (userRepository.existsByNickname(dto.toEntity().getNickname())) {
            errors.rejectValue("nickname", "닉네임 중복 오류", "이미 사용중인 닉네임 입니다.");
        }
    }
}