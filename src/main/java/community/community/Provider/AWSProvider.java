package community.community.Provider;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class AWSProvider {

    @Value("${aws.bucket.name}")
    private String bucketName;

    private Regions clientRegion = Regions.US_EAST_1;


    public String generatePresignedUrlAndUploadObject(String fileName, String type, MultipartFile file) {
        String ObjectKey = "img/" + fileName;
        try {
            //This code expects that you have AWS credentials set up per:
            // https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .build();

            // Upload a file as a new object with ContentType and title specified.
            File uploadFile = new File(System.getProperty("java.io.tmpdir")+"/"+fileName);
            file.transferTo(uploadFile);
            PutObjectRequest request = new PutObjectRequest(bucketName, ObjectKey, uploadFile);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(type);
            metadata.addUserMetadata("title", "someTitle");
            request.setMetadata(metadata);
            s3Client.putObject(request);

            URL url = generateURL(s3Client, ObjectKey);
            return url.toString();
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public URL generateURL(AmazonS3 s3Client, String ObjectKey) {
        // Set the presigned URL to expire after one hour.
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 30;
        expiration.setTime(expTimeMillis);

        // Generate the presigned URL.
        System.out.println("Generating pre-signed URL.");
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, ObjectKey)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);
        URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

        System.out.println("Pre-Signed URL: " + url.toString());
        return url;
    }

    public String generateArticleAndUpload(String content, String title) {
        String fileName = title + "-" + generateRandomString(5);
        String objectKey = "articles/"+fileName;

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(clientRegion)
                .build();

        s3Client.putObject(bucketName, objectKey, content);

        // Upload a file as a new object with ContentType and title specified.
        PutObjectRequest request = new PutObjectRequest(bucketName, objectKey, new File(fileName));
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("plain/text");
        metadata.addUserMetadata("title", "someTitle");
        request.setMetadata(metadata);

        return objectKey;
    }

    public String generateRandomString(int length) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = length;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        return generatedString;
    }

    public String getArticleContent(String objectKey) {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(clientRegion)
                .build();
        S3Object object = s3Client.getObject(bucketName, objectKey);
        S3ObjectInputStream objectContent = object.getObjectContent();

        try {
            String content = IOUtils.toString(objectContent);
            content = replaceOrDeleteImgURL(content, s3Client, false);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Apply new pre-sign urls for the images stored in the AWS bucket, and replace
     * the original url with new one.
     * @param content
     * @param s3Client
     * @param isDelete true for deleting the img, false for replacing the img
     * @return new content
     */
    public String replaceOrDeleteImgURL(String content, AmazonS3 s3Client, boolean isDelete) {
        String regex = "\\(https://owl-community\\.s3\\.amazonaws\\.com/img/.*\\)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(content);
        while (m.find()) {
            String originalURL = m.group();
            System.out.println(originalURL);
            String[] str = originalURL.split("/|\\?");
            int index = 0;
            while (!"img".equals(str[index])) {
                index++;
            }
            String objectKey = "img/" + str[index+1];
            System.out.println(objectKey);
            if (isDelete) {
                deleteImg(s3Client, objectKey);
            } else {
                URL newURL = generateURL(s3Client, objectKey);
                System.out.println(newURL.toString());
                content = content.replace(originalURL.substring(1, originalURL.length()-1), newURL.toString());
            }
        }
        return content;
    }

    /**
     * delete the img from the aws s3 by using the objectKey
     * @param objectKey
     */
    public void deleteImg(String objectKey) {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(clientRegion)
                .build();
        try {
            s3Client.deleteObject(new DeleteObjectRequest(bucketName, objectKey));
        } catch (SdkClientException e) {
            e.printStackTrace();
        }
    }

    public void deleteImg(AmazonS3 s3Client, String objectKey) {
        try {
            s3Client.deleteObject(new DeleteObjectRequest(bucketName, objectKey));
        } catch (SdkClientException e) {
            e.printStackTrace();
        }
    }

    public void deleteArticles(String objectKey) {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(clientRegion)
                .build();

        String content = getArticleContent(objectKey);
        //find all the images stored in the s3 bucket and delete them
        replaceOrDeleteImgURL(content, s3Client, true);
        s3Client.deleteObject(new DeleteObjectRequest(bucketName, objectKey));
    }

}
