package com.farmstory.dto;

import com.farmstory.entity.FileEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDTO {
    private int fileNo;
    private int artNo;
    private String fileoName;
    private String filesName;
    private int fileDownload;

    @CreationTimestamp
    private String fileRdate;
    private String filePath;


    public FileEntity toEntity() {
        return FileEntity.builder()
                .fileNo(fileNo)
                .fileoName(fileoName)
                .filesName(filesName)
                .fileDownload(fileDownload)
                .fileRdate(fileRdate)
                .filePath(filePath)
                .build();
    }
}
