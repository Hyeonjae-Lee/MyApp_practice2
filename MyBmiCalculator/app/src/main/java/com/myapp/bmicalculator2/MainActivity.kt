package com.myapp.bmicalculator2

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadData()

        buttonView.setOnClickListener {

            saveData(HeightView.text.toString().toInt(), WeightView.text.toString().toInt() )

            startActivity<ResultActivity>(
                "Weight" to WeightView.text.toString(),
                "Height" to HeightView.text.toString()
            )

        }

    }

    private fun saveData(height : Int, weight : Int){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()

        editor.putInt("Key_Height", height).apply()
        editor.putInt("Key_Weight", weight).apply()

        //editor.putInt("Key_Height", height).putInt("Key_Weight", weight).apply()
    }

    private fun loadData(){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val height = pref.getInt("Key_Height", 0)
        val weight = pref.getInt("Key_Weight", 0)

        if(height!=0 && weight!=0){
            HeightView.setText(height.toString())
            WeightView.setText(weight.toString())
        }


    }
}
