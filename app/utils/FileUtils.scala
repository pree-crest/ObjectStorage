package utils

import java.io.File


object FileUtils {

  def fileName( path :String ) : String = {
    val x = path.substring(path.lastIndexOf("/")+1)
    x
  }

  def getParent( path : String ) : String = {
    path.substring(0, path.lastIndexOf("/"))
  }

  def createDirs(filePath: String, destination: String) = {
    if (filePath.lastIndexOf("/") != -1) {
      new File(FileUtils.getParent(destination)).mkdirs()
    }
  }

}
