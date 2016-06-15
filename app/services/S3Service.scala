package services

import java.io._
import java.nio.file.{FileSystems, Files, StandardOpenOption}
import java.util

import com.amazonaws.services.s3.model.{ObjectListing, S3Object, S3ObjectInputStream, S3ObjectSummary}
import utils.{Constants, S3Client}

class S3Service(s3Client : S3Client = new S3Client) {

  val filePath = "/Users/ppn3633/Documents/sampleXmlData/book.xml"
  val fileToUpload = new File(filePath)

  def saveFile(): Unit ={

  }

  def getFile( ): Unit ={

    val s3Object : S3Object = s3Client.getS3Client.getObject(Constants.bucketName,"springer-data/book.xml")
    val inputStream = s3Object.getObjectContent
    val outputStream = new BufferedOutputStream(new FileOutputStream(Constants.TARGET_DIRECTORY + "/springer-data/book.xml"))
    Iterator.continually (inputStream.read).takeWhile (-1 !=).foreach (outputStream.write)
    outputStream.close()
  }

  def getBucketFiles(): Unit ={
    val objectListing: ObjectListing = s3Client.getS3Client.listObjects(Constants.bucketName)
    val summaryList  = objectListing.getObjectSummaries

    var i = 1;
    println(summaryList)
    while( i < summaryList.size) {
      println(summaryList.get(i).getKey)
      i+=1
    }
  }

}

object s extends  App {
  new S3Service().getBucketFiles()
  new S3Service().getFile()
}
