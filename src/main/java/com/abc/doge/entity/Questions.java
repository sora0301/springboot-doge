package com.abc.doge.entity;

import com.abc.doge.enums.QuestionLevel;
import com.abc.doge.enums.QuestionType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "questions")
public class Questions {
    // 질문 id PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 질문의 유형 TTS_SELECT, IMAGE_SELECT, VIDEO_SELECT, TEXT_SELECT
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionType questionType;

    // 질문의 난이도 EASY, MEDIUM, HARD
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionLevel questionLevel;

    // 질문 내용
    @Column(nullable = false, length = 255)
    private String questionText;

    // 질문에 쓰이는 파일의 이름
    @Column(length = 255)
    private String questionFile;

    // 문제 생성 날짜
    @Column(name = "created_date", nullable = false, updatable = false )
    private LocalDateTime createdDate;

    // 문제 마지막 업데이트 날짜
    @Column(name = "last_update_date", nullable = false)
    private LocalDateTime lastUpdatedDate;

    // 한 질문은 여러 선택지를 가질 수 있음
//    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
//    private List<QuestionOptions> options; // QuestionOptions 리스트 추가

    // 한 질문의 하나의 질문 선택지만 가질 수 있음. 하나의 선택지도 하나의 질문만 참조할 수 있음
    @OneToOne(mappedBy = "question", cascade = CascadeType.ALL)
    private QuestionOptions option; // 단일 선택지 추가

    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now(); // 생성 시 현재 시간으로 설정
        // 생성시 현재 시간으로 설정
        // 이 부분이 누락이라 에러가 났었음 2025.01.14 HSJ
        this.lastUpdatedDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.lastUpdatedDate = LocalDateTime.now(); // 업데이트 전 현재 시간으로 설정
    }

}
