package utils

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import utils.Constants.{AWS_ACCESS_KEY, AWS_SECRET_KEY}


class S3Client {
  def getS3Client = {
    val yourAWSCredentials : BasicAWSCredentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY)
    val amazonS3Client = new AmazonS3Client(yourAWSCredentials)
    amazonS3Client
  }

}
