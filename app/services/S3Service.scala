package services

import java.io._
import javax.inject.Inject

import com.amazonaws.services.s3.model.{ObjectListing, S3Object}
import utils.Constants.{TARGET_DIRECTORY, bucketName}
import utils.{Constants, FileUtils, S3Client}

import scala.collection.JavaConverters._

class S3Service @Inject()(s3Client : S3Client){

  def getBucketFiles(): Unit ={
    val objectListings: List[ObjectListing] = getAllS3Objects()
    objectListings.flatMap(_.getObjectSummaries.asScala.map(_.getKey)).filter(fileKey=> fileKey.endsWith(".xml")).map(readAndWriteFile(_))
  }

  private def getAllS3Objects() : List[ObjectListing] = {
    val objects: ObjectListing = s3Client.getS3Client.listObjects(bucketName)
    getS3ObjectRecursively(List(objects))
  }

  private def getS3ObjectRecursively(output: List[ObjectListing]): List[ObjectListing] = {
    if(output.head.isTruncated) {
      getS3ObjectRecursively(s3Client.getS3Client.listNextBatchOfObjects(output.head) :: output)
    } else {
      output
    }
  }

  private def readAndWriteFile(filePath:String): Unit ={
    val targetDestination = TARGET_DIRECTORY + filePath
    val outputStream = new BufferedOutputStream(new FileOutputStream(targetDestination))

    FileUtils.createDirs(filePath, targetDestination)

     try{
      val s3Object : S3Object = s3Client.getS3Client.getObject(bucketName,filePath)
      val inputStream = s3Object.getObjectContent
      Iterator.continually (inputStream.read).takeWhile (-1 !=).foreach (outputStream.write)
    }catch{
      case e : IOException => println("Unable to write file")
    }finally {
      outputStream.close()
    }
  }

}

object Test extends  App {
  new S3Service(new S3Client).getBucketFiles()
}
