package mediSsok.mediSsokspring.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass   //상속할 경우 필드(createdDate, modifiedDate)들도 칼럼에 인색
@EntityListeners(AuditingEntityListener.class)  //Auditing 사용
public class BaseTimeEntity {

    @CreatedDate    // 생성 시간 자동 저장
    private LocalDateTime createdDate;

    @LastModifiedDate   // 변경 시간 자동 저장
    private LocalDateTime modifiedDate;
}
