package com.lazday.crudroomimagekotlin.helper

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import java.io.ByteArrayOutputStream
import java.io.File

class ImageConverter {
    companion object {

        fun convertImage2ByteArray(bitmap: Bitmap): ByteArray? {
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            return stream.toByteArray()
        }

        fun convertImage2Image(array: ByteArray): Bitmap? {
            return BitmapFactory.decodeByteArray(array, 0, array.size)
        }

        fun convertImageFromPath(imagePath: String, imageView: ImageView){
            val imgFile = File(imagePath)
            if (imgFile.exists()) {
                val bitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
                imageView.setImageBitmap(bitmap)
            }
        }

    }
}