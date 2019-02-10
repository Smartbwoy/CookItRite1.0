package elevizion.cookitrite.com.CreateMealsActivity

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.coordinatorlayout.Recipe_Ingredient
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import elevizion.cookitrite.com.R
import elevizion.cookitrite.com.R.array.*
import kotlinx.android.synthetic.main.activity_create_meal.*
import kotlinx.android.synthetic.main.activity_incredients__methods.*
import kotlinx.android.synthetic.main.recipe_pic_name.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "UNUSED_EXPRESSION")
class CreateMealActivity : AppCompatActivity() {
    private val REQUEST_CAPTURE_IMAGE = 1
    private val REQUEST_SELECT_IMAGE_IN_ALBUM = 10
    private val PERMISSION_REQUEST_CODE: Int = 101
    private var mCurrentPhotoPath: String? = null;

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_meal)

      /*  meal_pic_button.setOnClickListener{
            showPictureDialog()
        }*/
        // set on-click listener
        /* next_button.setOnClickListener {
             // your code to perform when the user clicks on the button
             //Check if recipe input field is empty
             if (TextUtils.isEmpty(recipeName.text.toString())) {
                 recipeName.error = "Enter the name of the recipe"
             }
             else if(recipeName.length()<6){
                 recipeName.error = "Recipe name must have a minimum of 6 character"
             }
             else {
                 //val intent = Intent(this, Ingredients_Methods::class.java)
                 //startActivity(intent)
             }
        }*/

      /* ArrayAdapter.createFromResource(
            this,
            R.array.Measure_type,
            R.layout.spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(R.layout.spinner_item)

            // Apply the adapter to the spinner
            measuretype_spinner.adapter = adapter
        }
        var listIngr = ArrayList<Recipe_Ingredient>()
        add_ingr_btn.setOnClickListener {
           when {
                TextUtils.isEmpty(ingr_name_text.text.toString()) -> ingr_name_text.error = "Enter the name of the ingredient"
                TextUtils.isEmpty(ing_quty.text.toString()) -> ing_quty.error = "Enter ingredient amount"
                else ->
                    listIngr.add(Recipe_Ingredient(ing_quty.text.toString(), measuretype_spinner.selectedItem.toString(), ingr_name_text.text.toString()))
                //Toast.makeText(this, "Hiii You clicked me.", Toast.LENGTH_SHORT).show()
            }

            var ingreient_Adapter = RecipeIngredientAdapter(this, listIngr)
            ingrlist.adapter = ingreient_Adapter

        }

        ingrlist.onItemLongClickListener= AdapterView.OnItemLongClickListener { adapterView, view, position, id ->
            //Toast.makeText(this, "Click on " + listIngr[position].label, Toast.LENGTH_SHORT).show();
            // Late initialize an alert dialog object
            lateinit var dialog: android.app.AlertDialog
            // Initialize a new instance of alert dialog builder object
            val builder = android.app.AlertDialog.Builder(this)
            // Set a title for alert dialog
            builder.setTitle("Delete Ingredient.")
            builder.setMessage("Do you wish to delete this ingredient")
// On click listener for dialog buttons
            val dialogClickListener = DialogInterface.OnClickListener{ _, which ->
                when(which){
                    DialogInterface.BUTTON_POSITIVE ->removeIngredient(position,listIngr)
                }
            }
            // Set the alert dialog positive/yes button
            builder.setPositiveButton("YES",dialogClickListener)

            // Set the alert dialog negative/no button
            builder.setNegativeButton("NO",dialogClickListener)

            // Initialize the AlertDialog using builder object
            dialog = builder.create()

            // Finally, display the alert dialog
            dialog.show()
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
            true
        }*/
    }

    private fun showPictureDialog(){
        val items = arrayOf("Select photo from gallery", "Capture photo from camera")
        lateinit var dialog: AlertDialog
        val builder = android.support.v7.app.AlertDialog.Builder(this)
        builder.setTitle("Select Action")
        builder.setItems(items){ _, which ->
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
            //val auxFile = File(mCurrentPhotoPath)


            var bitmap : Bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath)
            imgView.setImageBitmap(bitmap)

        }
        else if(requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM && resultCode == Activity.RESULT_OK){
            imgView.setImageURI(data?.data)
        }
    }

    fun Context.removeIngredient(position:Int, listIngritem:ArrayList<Recipe_Ingredient>){
        listIngritem.remove(listIngritem.get(position))
        var ingreient_Adapter = RecipeIngredientAdapter(this, listIngritem)
        ingrlist.adapter = ingreient_Adapter
    }
}
