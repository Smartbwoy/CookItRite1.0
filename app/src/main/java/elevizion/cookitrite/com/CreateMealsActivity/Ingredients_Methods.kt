package elevizion.cookitrite.com.CreateMealsActivity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import elevizion.cookitrite.com.R

import kotlinx.android.synthetic.main.activity_incredients__methods.*

class Ingredients_Methods : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_incredients__methods)
       //setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
