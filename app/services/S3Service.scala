package services

import java.io.File
import java.util

import com.amazonaws.services.s3.model.{ObjectListing, S3Object, S3ObjectSummary}
import utils.{Constants, S3Client}

/**
  * Created by ppn3633 on 6/14/16.
  */

class S3Service(s3Client : S3Client = new S3Client) {

  val filePath = "/Users/ppn3633/Documents/sampleXmlData/book.xml"
  val fileToUpload = new File(filePath)


  // This will create a bucket for storage
  //s3Client.createBucket(bucketName)

  //s3Client.putObject(bucketName, "springer-data/book.xml", fileToUpload)


//  val inputStream = s3Object.getObjectContent
//  var ch = inputStream.read()
//  while (ch != -1){
//    ch = inputStream.read()
//    print(ch.toChar)
//  }


  def saveFile(): Unit ={

  }

  def getFile(): Unit ={

    val s3Object : S3Object = s3Client.getS3Client.getObject(Constants.bucketName,"springer-data/book.xml")

  }
  def getBucketFiles(): Unit ={
    val fileList: ObjectListing = s3Client.getS3Client.listObjects(Constants.bucketName)
    //val summaryList  = fileList.getObjectSummaries
    //for (summary <- summaryList){
    //  println(summary)
    //}
    //fileList.getObjectSummaries.get(1).getKey
  }

}

object s extends  App {
  new S3Service().getBucketFiles()
}
