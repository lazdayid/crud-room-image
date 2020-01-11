package com.lazday.crudroomimagekotlin.activity

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lazday.crudroomimagekotlin.R
import com.lazday.crudroomimagekotlin.helper.PermissionUtils
import com.lazday.crudroomimagekotlin.room.DataModel
import com.lazday.crudroomimagekotlin.room.DataRepository
import kotlinx.android.synthetic.main.activity_create.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class CreateActivity : AppCompatActivity() {

    private val GALLERY_INTENT  = 1
    private val CAMERA_INTENT   = 2

    private lateinit var dataModel: DataModel
    private lateinit var dataRepository: DataRepository
    private var imagePath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        dataRepository = DataRepository(application)

        PermissionUtils.writeExtStorage(this, this)
        imageView.setOnClickListener {
            if (PermissionUtils.writeExtStorage(this, this)) {
                showPictureDialog()
            }
        }

        button.setOnClickListener {
            dataModel = DataModel(id = null, title = editText.text.toString(), image = imagePath!!)
            dataRepository.createData( dataModel )
            Toast.makeText(applicationContext, "Saved!", Toast.LENGTH_SHORT).show()
            finish()
        }

    }

    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
        pictureDialog.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }

    fun choosePhotoFromGallary() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, GALLERY_INTENT)
    }

    private fun takePhotoFromCamera() {
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        if (intent.resolveActivity(packageManager) != null) {
//            startActivityForResult(intent, CAMERA_INTENT)
//        }
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_INTENT)
    }

    public override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        /* if (resultCode == this.RESULT_CANCELED)
         {
         return
         }*/
        if (requestCode == GALLERY_INTENT) {
            if (data != null) {
                val contentURI = data.data
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    val path = saveImage(bitmap)
                    Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show()
                    imageView!!.setImageBitmap(bitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        } else if (requestCode == CAMERA_INTENT) {
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            imageView!!.setImageBitmap(thumbnail)
            saveImage(thumbnail)
            Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show()
        }
    }

    fun saveImage(myBitmap: Bitmap):String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY)
        // have the object build the directory structure, if needed.
        Log.d("fee",wallpaperDirectory.toString())
        if (!wallpaperDirectory.exists())
        {
            wallpaperDirectory.mkdirs()
            Toast.makeText(this, "folder created!", Toast.LENGTH_SHORT).show()
        }

        try
        {
            Log.d("heel",wallpaperDirectory.toString())
            val file = File(wallpaperDirectory, ((Calendar.getInstance()
                .timeInMillis).toString() + ".jpg"))
            file.createNewFile()
            val fileOutput = FileOutputStream(file)
            fileOutput.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(this,
                arrayOf(file.path),
                arrayOf("image/jpeg"), null)
            fileOutput.close()

            imagePath = file.absolutePath
            Log.d("TAG", "File Saved::--->" + file.absolutePath)

            return file.absolutePath
        }
        catch (e1: IOException) {
            e1.printStackTrace()
            Log.d("TAG", e1.toString())
        }

        return ""
    }

    companion object {
        private val IMAGE_DIRECTORY = "/crudRoom"
    }
}
