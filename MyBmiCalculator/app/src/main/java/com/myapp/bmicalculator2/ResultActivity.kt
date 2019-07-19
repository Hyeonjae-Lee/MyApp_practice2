package com.myapp.bmicalculator2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_result.*
import org.jetbrains.anko.toast

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val height = intent.getStringExtra("Height").toInt()
        val weight = intent.getStringExtra("Weight").toInt()

        val bmiResult = weight / Math.pow(height / 100.0, 2.0)

        var x:Double = bmiResult

        when {
           bmiResult >= 35 -> ResultView.text = "fuck, kill me"
            bmiResult >= 30 -> ResultView.text = "too too much fat!"
            bmiResult >= 25 -> ResultView.text = "too much fat!"
            bmiResult >= 20 -> ResultView.text = "fat!"
            bmiResult >= 18.5 -> ResultView.text = "not fat"
            else ->ResultView.text = "not not fat"
        }

        //이미지 표시
        when {
            bmiResult >= 35 -> imageView.setImageResource(R.drawable.ic_assignment_late_black_24dp)
            bmiResult >= 30 -> imageView.setImageResource(R.drawable.ic_sentiment_very_dissatisfied_black_24dp)
            bmiResult >= 25 -> imageView.setImageResource(R.drawable.ic_sentiment_dissatisfied_black_24dp)
            bmiResult >= 20 -> imageView.setImageResource(R.drawable.ic_sentiment_neutral_black_24dp)
            else -> imageView.setImageResource(R.drawable.ic_assignment_late_black_24dp)
        }

        toast(bmiResult.toString())
        //정수나 문자열만 가능
        //toast("$bmiResult")
    }
}
