package utils

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client

/**
  * Created by ppn3633 on 6/14/16.
  */
class S3Client {
  def getS3Client = {
    val yourAWSCredentials : BasicAWSCredentials = new BasicAWSCredentials(Constants.AWS_ACCESS_KEY, Constants.AWS_SECRET_KEY)
    val amazonS3Client = new AmazonS3Client(yourAWSCredentials)
    amazonS3Client
  }

}
