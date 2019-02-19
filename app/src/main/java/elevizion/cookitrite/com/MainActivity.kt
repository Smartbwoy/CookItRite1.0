package elevizion.cookitrite.com

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import elevizion.cookitrite.com.CreateMealsActivity.CreateMealActivity

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //val intent = Intent(this, CreateMealActivity::class.java)
        val intent=Intent(this,ViewRecipeActivity::class.java)
        startActivity(intent)
        finish()
        //setContentView(R.layout.activity_splashscreen)
        /*setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/

    }

}
