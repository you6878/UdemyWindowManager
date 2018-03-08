package com.bpplatform.howlwindowmanager

import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    var overlayView : View? = null
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(Settings.canDrawOverlays(this)){
            overlayView = layoutInflater.inflate(R.layout.overlay,null)
            var messageTextView = overlayView!!.findViewById<TextView>(R.id.textView_overlay)
            messageTextView.setText("명성 : 반갑다 오랜만이다!")
            var layout_flag : Int
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                layout_flag = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            }else{
                layout_flag = WindowManager.LayoutParams.TYPE_PHONE
            }

            var params = WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    0,
                    0,
                    layout_flag,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT
            )
            windowManager.addView(overlayView,params)
            overlayView!!.setOnClickListener { windowManager.removeView(overlayView)
            //var intent = Intent(this,MainActivity::class.java)
            //    startActivity(intent)
            }
        }else{
            var intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:"+packageName))
            startActivityForResult(intent,0)
        }



    }
}
