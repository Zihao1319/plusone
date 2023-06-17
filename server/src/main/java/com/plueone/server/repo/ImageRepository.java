package com.plueone.server.repo;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class ImageRepository {

    @Autowired
    private AmazonS3 s3Client;

    @Value("${DO_STORAGE_BUCKETNAME}")
    private String bucketName;

    // private static final String IMAGE_URL =
    // "https://ozh2923.sgp1.digitaloceanspaces.com/";

    public String upload(MultipartFile file, String userId) throws IOException {

        Map<String, String> userData = new HashMap<>();

        userData.put("userId", userId);
        userData.put("uploadDateTime", LocalDateTime.now().toString());
        userData.put("originalFileName", file.getOriginalFilename());

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());

        System.out.printf(">>>>>>>>file type: %s\n", file.getContentType());
        metadata.setContentLength(file.getSize());
        metadata.setUserMetadata(userData);

        StringTokenizer tk = new StringTokenizer(file.getOriginalFilename(), ".");
        int count = 0;
        String fileNameExt = "";

        while (tk.hasMoreTokens()) {

            if (count == 1) {
                fileNameExt = tk.nextToken();
                break;

            } else {
                fileNameExt = tk.nextToken();
                count += 1;
            }
        }

        if (fileNameExt.equals("blob")) {
            fileNameExt = fileNameExt + ".png";

        }

        String id = UUID.randomUUID().toString().substring(0, 6);
        System.out.printf(">>>>>>>>fileName: %s\n", "%s.%s".formatted(id, fileNameExt));

        String imageUrl = "%s-%s.%s".formatted(userId, id, fileNameExt);

        // ! Create putobject Request and configure with public access
        PutObjectRequest putReq = new PutObjectRequest(bucketName, imageUrl,
                file.getInputStream(), metadata);
        putReq.withCannedAcl(CannedAccessControlList.PublicRead);

        // ! Execute the request
        s3Client.putObject(putReq);

        // String url = IMAGE_URL + "%s-%s.%s".formatted(id, fileNameExt);

        return imageUrl;
    }

    public void delete(String imageUrl) {
        DeleteObjectRequest delReq = new DeleteObjectRequest(bucketName, imageUrl);
        s3Client.deleteObject(delReq);

    }

}
