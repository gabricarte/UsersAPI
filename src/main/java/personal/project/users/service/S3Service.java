package personal.project.users.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final S3Client s3Client;
    private String bucketName = "s3-unfit-report-qa";

    public String getAllObjects(){
        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();

        ListObjectsV2Response response = s3Client.listObjectsV2(request);

        List<String> objectKeys = response.contents().stream()
                .map(S3Object::key)
                .collect(Collectors.toList());

        return String.join("\n", objectKeys);
    }

    public String getObject(String objectKey) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();
        try (ResponseInputStream<GetObjectResponse> objectStream = s3Client.getObject(getObjectRequest)) {
            return IOUtils.toString(objectStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler objeto do S3: " + objectKey, e);
        }
    }

    public byte[] getObjectBytes(String objectKey) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        try (ResponseInputStream<GetObjectResponse> objectStream = s3Client.getObject(getObjectRequest)) {
            return objectStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao baixar o objeto do S3: " + objectKey, e);
        }
    }

    public String putObject(String objectKey, byte[] content) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        try {
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(content));
            return "Objeto enviado com sucesso: " + objectKey;
        } catch (SdkException e) {
            throw new RuntimeException("Erro ao enviar objeto para o S3: " + objectKey, e);
        }
    }


    public String deleteObject(String objectKey) {
        DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        try {
            s3Client.deleteObject(deleteRequest);
            return "Objeto excluído com sucesso: " + objectKey;
        } catch (SdkException e) {
            throw new RuntimeException("Erro ao excluir objeto do S3: " + objectKey, e);
        }
    }
}


