package com.myapp.mystopwatch

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    private var lap = 1
    private var time = 0
    private var timerTask : Timer? = null
    var isRunning = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fAbPlay.setOnClickListener {

            if(isRunning) {
                start()
                isRunning = false
            }else{
                pause()
                isRunning = true
            }
        }

        LabButton.setOnClickListener {
            lapView()
        }

        fabRefresh.setOnClickListener {
            refresh()
        }
    }

    private fun start(){
        fAbPlay.setImageResource(R.drawable.ic_stop_black_24dp)

        timerTask = timer(period = 10) {//0.01초바마 변수 증가

            time++
            val sec = time/100
            val milli = time%100
            runOnUiThread {
                SubNumber.text = "$milli"
                MainNumber.text ="$sec"
            }

        }
    }

    private fun pause(){

        fAbPlay.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        timerTask?.cancel()

    }

    private fun lapView() {
        val lapTime = this.time
        val textView = TextView(this)

        textView.text = "$lap Lab : ${lapTime / 100} . ${lapTime % 100}"

        //맨 위에 랩타임 추가
        Lablayout.addView(textView, 0)
        lap++
    }

    private fun refresh(){
        timerTask?.cancel()

        time = 0
        isRunning = true
        fAbPlay.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        MainNumber.text = "00"
        SubNumber.text = "00"

    }
}
