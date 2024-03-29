package mediSsok.mediSsokspring.service.Validation;

import lombok.RequiredArgsConstructor;
import mediSsok.mediSsokspring.domain.repository.member.MemberRepository;
import mediSsok.mediSsokspring.dto.member.MemberSaveRequestDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckEmailValidator extends AbstractValidator<MemberSaveRequestDto> {
    private final MemberRepository userRepository;

    @Override
    protected void doValidate(MemberSaveRequestDto dto, Errors errors) {
        if (userRepository.existsByEmail(dto.toEntity().getEmail())) {
            errors.rejectValue("email", "이메일 중복 오류", "이미 사용중인 이메일 입니다.");
        }
    }
}