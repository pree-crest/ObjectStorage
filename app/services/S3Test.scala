package services

import utils.S3Client

object S3Test extends App {
  new S3Service(new S3Client).getBucketFiles()
}