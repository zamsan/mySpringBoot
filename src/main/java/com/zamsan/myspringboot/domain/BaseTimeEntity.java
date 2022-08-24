package com.zamsan.myspringboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass //JPA 클래스들이 BaseTimeEntity를 상속할 경우 필드들을 컬럼으로 인식하도록
@EntityListeners(AuditingEntityListener.class) //BaseTimeEntity 클래스에 Auditing기능 포함
public class BaseTimeEntity {

    @CreatedDate //Entity가 생성 되어 저장 될때 시간이 자동으로 저장
    private LocalDateTime createDate;

    @LastModifiedDate //조회한 Entity의 값을 변경 할때 시간이 자동으로 저장
    private  LocalDateTime modifiedDate;
}
