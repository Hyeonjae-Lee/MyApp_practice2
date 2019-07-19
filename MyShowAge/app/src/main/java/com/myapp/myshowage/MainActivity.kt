package com.myapp.myshowage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {

            if(AgeView.text.toString().toBoolean()) {

                var ageString = AgeView.text.toString().toInt()

                val instance = Calendar.getInstance()
                val year = instance.get(Calendar.YEAR).toString().toInt()

                ageString = year - ageString

                AgeView.setText(ageString.toString())
                ShowView.setText("너의 나이는 ${ageString} 살이다")

            }else{

              alert("에러 메시지", "no!") {
                  yesButton {  }

              }.show()
            }
        }


    }
}
