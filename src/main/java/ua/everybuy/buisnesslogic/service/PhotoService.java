package ua.everybuy.buisnesslogic.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoService {
    private final static String URL_PHOTO_PREFIX = "https://everybuy-user-photos.s3.eu-north-1.amazonaws.com/";
    private final UserService userService;

    @Value("${aws.access.key}")
    private String accessKey;
    @Value("${aws.secret.key}")
    private String secretKey;

    public String handlePhotoUpload(MultipartFile photo, Long userId) throws IOException {



        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion("eu-north-1")
                .build();

        String bucketName = "everybuy-user-photos";
        if (!s3Client.doesBucketExistV2(bucketName)) {
            throw new IOException("Bucket '" + bucketName + "' does not exist.");
        }
        String photoKey;

        try {

            photoKey = photo.getOriginalFilename();

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(photo.getContentType());
            metadata.setContentLength(photo.getSize());

            s3Client.putObject(bucketName, photoKey, photo.getInputStream(), metadata);
            userService.updatePhotoUrl(photoKey, userId);

        } catch (AmazonServiceException e) {
            System.err.println("AWS error: " + e.getErrorMessage());
            throw new IOException("Failed to upload photos to S3: " + e.getErrorMessage(), e);
        } catch (SdkClientException e) {
            System.err.println("Client error: " + e.getMessage());
            throw new IOException("Failed to upload photos to S3: " + e.getMessage(), e);
        }

        return URL_PHOTO_PREFIX + photoKey;
    }

}
