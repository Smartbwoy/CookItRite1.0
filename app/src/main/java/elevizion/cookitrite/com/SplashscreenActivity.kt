package elevizion.cookitrite.com

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.activity_splashscreen.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SplashscreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        /*var anim = AnimationUtils.loadAnimation(this, R.anim.abc_popup_enter)
        anim.reset()
        val l = findViewById<RelativeLayout>(R.id.activity_splashscreen)
        l.clearAnimation()
        l.startAnimation(anim)*/

        var anim = AnimationUtils.loadAnimation(this, R.anim.abc_grow_fade_in_from_bottom)
        //var anim1= AnimationUtils.loadAnimation(this, R.anim.abc_shrink_fade_out_from_bottom)
        anim.duration=2000
        //anim1.duration=2000
        splash_image.startAnimation(anim)

        //splash_image.startAnimation(anim1)

       val myThread = object : Thread() {
            override fun run() {
                try {
                    Thread.sleep(1000)
                    val intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
        }
        myThread.start()

    }

}
