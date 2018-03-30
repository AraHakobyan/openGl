package opengl.airhockeyexample

import android.app.ActivityManager
import android.content.Context
import android.content.pm.ConfigurationInfo
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var glSurfaceView: GLSurfaceView? = null
    private var renderSet:Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        glSurfaceView= GLSurfaceView(this)
        val activityManager: ActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val configurationInfo: ConfigurationInfo = activityManager.deviceConfigurationInfo
        val supportsEs2:Boolean = configurationInfo.reqGlEsVersion >= 0x20000
        if (supportsEs2){
            glSurfaceView!!.setEGLContextClientVersion(2)
            glSurfaceView!!.setRenderer(AirHockeyRenderer(this))
            renderSet = true
        } else{
            Toast.makeText(this,"this device doesn't support OpenGl ES 2.0", Toast.LENGTH_LONG).show()
            return
        }
        setContentView(glSurfaceView)
    }

    override fun onPause() {
        super.onPause()
        if (renderSet){
            glSurfaceView?.onPause()
        }
    }

    override fun onResume() {
        super.onResume()
        if (renderSet){
            glSurfaceView?.onResume()
        }
    }
}
