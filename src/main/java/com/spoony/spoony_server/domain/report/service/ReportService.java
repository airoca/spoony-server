package com.spoony.spoony_server.domain.report.service;


import com.spoony.spoony_server.domain.post.entity.PostEntity;
import com.spoony.spoony_server.domain.post.repository.PostRepository;
import com.spoony.spoony_server.domain.report.dto.request.ReportRequestDTO;
import com.spoony.spoony_server.domain.report.dto.response.ReportResponseDTO;
import com.spoony.spoony_server.domain.report.entity.ReportEntity;
import com.spoony.spoony_server.domain.report.enums.ReportType;
import com.spoony.spoony_server.domain.report.repository.ReportRepository;
import com.spoony.spoony_server.domain.user.entity.UserEntity;
import com.spoony.spoony_server.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public ReportService(ReportRepository reportRepository, PostRepository postRepository, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }


    public ReportResponseDTO createReport(ReportRequestDTO reportRequest) {

        if (reportRequest == null || reportRequest.reportDetail() == null) {
            throw new IllegalArgumentException();
        }

        ReportType reportType = reportRequest.reportType();
        if (reportType == null) {
            reportType = ReportType.ADVERTISEMENT;
        }

        Long postId = reportRequest.postId();
        Long userId = reportRequest.userId();

        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        ReportEntity reportEntity = ReportEntity.builder()
                .post(postEntity)
                .user(userEntity)
                .reportType(reportType)
                .reportDetail(reportRequest.reportDetail())
                .build();

        ReportEntity savedEntity = reportRepository.save(reportEntity);

        return new ReportResponseDTO(
                savedEntity.getReportId(),
                savedEntity.getUser().getUserId(),
                savedEntity.getReportType(),
                savedEntity.getPost().getPostId()

        );
    }
}