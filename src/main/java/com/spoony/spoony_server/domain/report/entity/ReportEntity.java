package com.spoony.spoony_server.domain.report.entity;

import com.spoony.spoony_server.domain.post.entity.PostEntity;
import com.spoony.spoony_server.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "report")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String reportType;
    private String reportDetail;

    @Builder
    public ReportEntity(Integer reportId, PostEntity post, UserEntity user, String reportType, String reportDetail) {
        this.reportId = reportId;
        this.post = post;
        this.user = user;
        this.reportType = reportType;
        this.reportDetail = reportDetail;
    }
}
