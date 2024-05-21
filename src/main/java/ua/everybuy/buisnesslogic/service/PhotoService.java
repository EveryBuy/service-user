package ua.everybuy.buisnesslogic.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.everybuy.routing.model.model.response.PhotoUrlResponse;
import ua.everybuy.routing.model.model.response.StatusResponse;

import java.io.IOException;
import java.security.Principal;

@Service
@RequiredArgsConstructor
public class PhotoService {
    private final static String URL_PHOTO_PREFIX = "https://everybuy-user-photos.s3.eu-north-1.amazonaws.com/";
    private final UserService userService;

    @Value("${aws.access.key}")
    private String accessKey;
    @Value("${aws.secret.key}")
    private String secretKey;

    public StatusResponse handlePhotoUpload(MultipartFile photo, Principal principal) throws IOException {

        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion("eu-north-1")
                .build();

        String bucketName = "everybuy-user-photos";
        if (!s3Client.doesBucketExistV2(bucketName)) {
            throw new IOException("Bucket '" + bucketName + "' does not exist.");
        }
        String photoUrl = URL_PHOTO_PREFIX + principal.getName();

        try {

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(photo.getContentType());
            metadata.setContentLength(photo.getSize());

            s3Client.putObject(bucketName, principal.getName(), photo.getInputStream(), metadata);
            userService.updatePhotoUrl(photoUrl, Long.parseLong(principal.getName()));

        } catch (AmazonServiceException e) {
            System.err.println("AWS error: " + e.getErrorMessage());
            throw new IOException("Failed to upload photos to S3: " + e.getErrorMessage(), e);
        } catch (SdkClientException e) {
            System.err.println("Client error: " + e.getMessage());
            throw new IOException("Failed to upload photos to S3: " + e.getMessage(), e);
        }
        return new StatusResponse(200, new PhotoUrlResponse(photoUrl));
    }

}
