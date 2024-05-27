package ua.everybuy.buisnesslogic.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.everybuy.errorhandling.exception.FileFormatException;
import ua.everybuy.errorhandling.exception.FileValidException;
import ua.everybuy.routing.model.model.response.PhotoUrlResponse;
import ua.everybuy.routing.model.model.response.StatusResponse;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.security.Principal;

@Service
@RequiredArgsConstructor
public class PhotoService {
    private final static String URL_PHOTO_PREFIX = "https://everybuy-user-photos.s3.eu-north-1.amazonaws.com/";
    private final UserService userService;
    private final AmazonS3 s3Client;
    @Value("${aws.bucket}")
    private String bucketName;

    public StatusResponse handlePhotoUpload(MultipartFile photo, Principal principal) throws IOException {
        isEmpty(photo);
        isImage(photo);

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
            throw new IOException("Failed to upload photos to S3: " + e.getErrorMessage(), e);

        } catch (SdkClientException e) {
            throw new IOException("Failed to upload photos to S3: " + e.getMessage(), e);
        }

        return new StatusResponse(200, new PhotoUrlResponse(photoUrl));
    }

    private void isImage(MultipartFile file) throws IOException {
        if (ImageIO.read(file.getInputStream()) == null) {
            throw new FileFormatException("File should be image");
        }
    }

    private void isEmpty(MultipartFile file) {
        if (file == null || file.isEmpty()){
            throw new FileValidException("File should be not null");
        }
    }

}
