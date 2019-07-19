package com.myapp.mytiltsensor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.SensorEvent
import android.view.View

class TiltView(context: Context?) : View(context) {

    //paint 객체가 필요하다.
    private val greenPaint : Paint = Paint()
    private val redPaint : Paint = Paint()

    private var cX = 0f
    private var cY = 0f

    private var xCoord = 0f
    private var yCoord = 0f

    init{
        greenPaint.color = Color.GREEN
        redPaint.style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas?) {
        //캔버스 객체를 받는다.

        canvas?.drawCircle(cX, cY , 100f, greenPaint)
        canvas?.drawCircle(cX+xCoord, cY+yCoord , 100f, redPaint)

        canvas?.drawLine(cX-20f, cY, cX + 20f, cY, redPaint)
        canvas?.drawLine(cX,cY-20f, cX, cY+20f, redPaint)

        //drawCircle(cs :Float, cy : Float, radius : Float, pain : Paint)
        super.onDraw(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        cX = w/2f
        cY = h/2f
    }

    fun onSensorEvent(event : SensorEvent){

        //화면을 가로로 돌렸으므로 x축과 y축을 서로 바꾸다
        yCoord = event.values[0]*20
        xCoord = event.values[1]*20

        invalidate()// onDraw 메서드를 다시 호출하는 메서드, 즉 뷰를 다시 그리게 됨
    }
}