package com.myapp.mytestapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:010-8664-7152"))

            if(intent.resolveActivity(packageManager)!=null){
                startActivity(intent)
            }

        }

        Button2.setOnClickListener {
            val intetnt = Intent()
        }
    }
}
