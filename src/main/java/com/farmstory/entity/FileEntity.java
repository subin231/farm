package com.farmstory.entity;

import com.farmstory.dto.FileDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity                 // 엔티티 객체 정의
@Table(name = "file")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fileNo;

    private String fileoName;
    private String filesName;
    private int fileDownload;

    @ManyToOne
    @JoinColumn(name = "artNo")
    private Article article;

    @CreationTimestamp
    private String fileRdate;
    private String filePath;


    public FileDTO toDTO() {
        return FileDTO.builder()
                .fileNo(fileNo)
                .artNo(article.getArtNo())
                .fileoName(fileoName)
                .filesName(filesName)
                .fileDownload(fileDownload)
                .filePath(filePath)
                .build();
    }
}
