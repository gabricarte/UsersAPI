package personal.project.users.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AmazonConfig {
    private final String bucketRegion;

    private final String bucketKey;

    private final String bucketSecret;

    public AmazonConfig(@Value("${aws.s3.region}") String bucketRegion,
                        @Value("${aws.s3.key}") String bucketKey,
                        @Value("${aws.s3.secret}") String bucketSecret) {
        this.bucketRegion = bucketRegion;
        this.bucketKey = bucketKey;
        this.bucketSecret = bucketSecret;
    }

    @Bean
    public S3Client s3() {
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(bucketKey, bucketSecret);
        return S3Client.builder()
                .region(Region.of(bucketRegion))
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();
    }
}
