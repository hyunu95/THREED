// common/service/S3Service.java
package com.example.threed.common.service;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3Service {

	private final AmazonS3 amazonS3;

	@Value("${aws.s3.bucket-name}")
	private String bucket;

	public String uploadFile(MultipartFile file, String key) throws IOException {
		amazonS3.putObject(bucket, key, file.getInputStream(), null);
		return amazonS3.getUrl(bucket, key).toString();
	}
}
