package dev.tutorial.kmpizza.backend.storage.aws

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetUrlRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.io.File

class AmazonFileStorage : FileStorage {

    private val client: S3Client

    private val bucketName: String = System.getenv("bucketName")

    init {
        val region = System.getenv("region")
        val accessKey = System.getenv("accessKey")
        val secretKey = System.getenv("secretKey")

        val credentials = AwsBasicCredentials.create(accessKey, secretKey)
        val awsRegion = Region.of(region)
        client = S3Client.builder()
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .region(awsRegion)
            .build() as S3Client
    }

    override suspend fun save(file: File): String =
    withContext(Dispatchers.IO) {
        client.putObject(
            PutObjectRequest.builder().bucket(bucketName).key(file.name).build(),
            RequestBody.fromFile(file)
        )
        val request = GetUrlRequest.builder().bucket(bucketName).key(file.name).build()
        client.utilities().getUrl(request).toExternalForm()
    }
}
