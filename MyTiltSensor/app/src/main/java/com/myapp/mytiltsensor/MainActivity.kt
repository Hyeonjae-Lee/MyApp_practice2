package com.myapp.mytiltsensor

import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {
    //센서 정밀도가 변경되면 호출
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(p0: SensorEvent?) {
        //센서값이 변경되면 호출
        //valuse[0] = x ,위로 기울이면 -10~0, 아래로 기울이면 0~10
        p0?.let {
            Log.d("MainActivity", "onSensorChanged : x :" + "${p0.values[0]}, y : ${p0.values[1]}")
            //tag 는 필터링할 때 사용하기 위해서 쓴다

            tiltView.onSensorEvent(p0)
        }

    }

    //지연된 초기화를 사용하여 sensorManager 변수를 처음 사용할 때 getSystemService메서드로 SensorManagerr객체를 얻는다.
    private val sensorManager by lazy {
        //센서를 사용하려면 안드로이드가 제공하는 센서 매니저 서비스 객체가 필요하다
        //센서 매니저는 안드로이드 기기의 각 센서 접근 및 리스너의 등록 취소, 이벤트를 수집하는 방법을 제공
        //장치에 있는 센서를 사용하려면 먼저 센서 매니저에 대한 참조를 얻어야 한다. 이렇게 하려면 getSystemSevice 메서드에
        //SENSOR_SERVICE상수를 전달하여 SensroManager 클래스의 인스턴스를 만든다.
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    private lateinit var tiltView : TiltView

    override fun onCreate(savedInstanceState: Bundle?) {
        //화면 꺼지지 않게하기
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        //화면 가로로 고정하기
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        tiltView = TiltView(this)
        setContentView(tiltView)
    }
    override fun onResume() {
        super.onResume()

        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST)
    }
    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}
