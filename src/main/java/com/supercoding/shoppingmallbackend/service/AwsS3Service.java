package com.supercoding.shoppingmallbackend.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.UtilErrorCode;
import com.supercoding.shoppingmallbackend.common.util.FilePathUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AwsS3Service {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // MultipartFile을 전달받아 File로 전환한 후 S3에 업로드
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
        return upload(uploadFile, dirName);
    }

    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);

//        removeNewFile(uploadFile);  // 로컬에 생성된 File 삭제 (MultipartFile -> File 전환 하며 로컬에 파일 생성됨)

        return uploadImageUrl;      // 업로드된 파일의 S3 URL 주소 반환
    }

    /**
     * imageUrl과 MultipartFile을 전달하면 AWS-S3에 파일을 업데이트함
     * @param multipartFile 변경할 파일
     * @param imageUrl 저장된 ImageUrl
     * @return updateUrl 경로
     * @throws IOException
     */
    public String updateFile(MultipartFile multipartFile, String imageUrl) throws IOException {
        log.info("imageUrl: {}", imageUrl);
        String filePath = FilePathUtils.convertImageUrlToFilePath(imageUrl);
        log.info("filePath: {}", filePath);

        File updateFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));

        removeFile(filePath);

        return putS3(updateFile, filePath);
    }

    /**
     * AWS 파일을 삭제함
     * @param imageUrl 저장된 ImageUrl 경로
     */
    private void removeFile(String imageUrl) {
        String filePath = FilePathUtils.convertImageUrlToFilePath(imageUrl);
        if(!amazonS3Client.doesObjectExist(bucket, filePath)){
            throw new CustomException(UtilErrorCode.NOT_FOUND_FILE);
        }

        amazonS3Client.deleteObject(bucket, filePath);
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead)	// PublicRead 권한으로 업로드 됨
        );
        uploadFile.delete();
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

//    private void removeNewFile(File targetFile) {
//        if(targetFile.delete()) {
//            log.info("파일이 삭제되었습니다.");
//        }else {
//            log.info("파일이 삭제되지 못했습니다.");
//        }
//    }

    private Optional<File> convert(MultipartFile file) throws  IOException {
        File convertFile = new File(file.getOriginalFilename());
        log.info("파일 이름: {}", file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertFile)) {
            fos.write(file.getBytes());
        }
        log.info("File: {}", convertFile.getName());
        return Optional.of(convertFile);
    }
}
