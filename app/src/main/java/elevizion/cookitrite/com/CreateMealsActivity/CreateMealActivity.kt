package elevizion.cookitrite.com.CreateMealsActivity

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.Toast
import elevizion.cookitrite.com.R
import kotlinx.android.synthetic.main.activity_create_meal.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CreateMealActivity : AppCompatActivity() {
    private val REQUEST_CAPTURE_IMAGE = 1
    private val REQUEST_SELECT_IMAGE_IN_ALBUM = 10
    private val PERMISSION_REQUEST_CODE: Int = 101
    private var mCurrentPhotoPath: String? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_meal)

        meal_pic_button.setOnClickListener{
            showPictureDialog()
        }
    }

    private fun showPictureDialog(){
        val items = arrayOf("Select photo from gallery", "Capture photo from camera")
        lateinit var dialog: AlertDialog
        val builder = android.support.v7.app.AlertDialog.Builder(this)
        builder.setTitle("Select Action")
        builder.setItems(items){dialog, which ->
            //Toast.makeText(applicationContext, items[which] + " is clicked", Toast.LENGTH_SHORT).show()
            if(items.indexOf(items[which])==0){
                if (checkPersmission()) selectImageInAlbum() else requestPermission()
            }
            else{
                if (checkPersmission()) takePicture() else requestPermission()
            }
        }
        dialog = builder.create()
        dialog.show()
        true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    &&grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        takePicture()
                } else {
                    Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show()
                }
                return
            }

            else -> {

            }
        }
    }
    private fun checkPersmission(): Boolean {
        return (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(READ_EXTERNAL_STORAGE, CAMERA),
            PERMISSION_REQUEST_CODE)
    }

    @Throws(IOException::class)
    private fun createFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = absolutePath
        }
    }

    private fun takePicture() {
        val intent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file: File = createFile()
        val uri: Uri = FileProvider.getUriForFile(
            this,
            "com.example.android.fileprovider",
            file
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri)
        startActivityForResult(intent, REQUEST_CAPTURE_IMAGE)
    }

    private fun selectImageInAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"

        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_SELECT_IMAGE_IN_ALBUM)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CAPTURE_IMAGE && resultCode == Activity.RESULT_OK) {

            //To get the File for further usage
            val auxFile = File(mCurrentPhotoPath)


            var bitmap : Bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath)
            imgView.setImageBitmap(bitmap)

        }
        else if(requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM && resultCode == Activity.RESULT_OK){
            imgView.setImageURI(data?.data)
        }
    }
}
