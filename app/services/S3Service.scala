package services

import java.io._

import com.amazonaws.services.s3.model.{ObjectListing, S3Object}
import utils.{Constants, S3Client}
import scala.collection.JavaConverters._

trait BucketingFile {

  def fileName( path :String ) : String = {
    val x = path.substring(path.lastIndexOf("/")+1)
    x
  }

  def getParent( path : String ) : String = {
    path.substring(0, path.lastIndexOf("/"))
  }

}

class S3Service(s3Client : S3Client = new S3Client) extends BucketingFile {

  def getFile( fileKey :String ): Unit ={
    val outputStream = new BufferedOutputStream(new FileOutputStream(Constants.TARGET_DIRECTORY + fileName(fileKey)))
    try{
      val s3Object : S3Object = s3Client.getS3Client.getObject(Constants.bucketName,fileKey)
      val inputStream = s3Object.getObjectContent
      Iterator.continually (inputStream.read).takeWhile (-1 !=).foreach (outputStream.write)
    }catch{
      case e : Exception => throw e ;
    }finally{
      outputStream.close()
    }
  }

  def getBucketFiles(): Unit ={
    val objectListings = getAllObjectListing()
    objectListings.flatMap(_.getObjectSummaries.asScala.map(_.getKey)).filter(p=> p.endsWith(".xml")).map(getFile(_))
  }

  private def getAllObjectListing() : List[ObjectListing] = {
    val objects: ObjectListing = s3Client.getS3Client.listObjects(Constants.bucketName)
     getAllObjectListingRecursively(List(objects))
  }

  private def getAllObjectListingRecursively(output: List[ObjectListing]): List[ObjectListing] = {
    if(output.head.isTruncated) {
      getAllObjectListingRecursively(s3Client.getS3Client.listNextBatchOfObjects(output.head) :: output)
    } else {
      output
    }
  }
}

object s extends  App {
  new S3Service().getBucketFiles()
}
