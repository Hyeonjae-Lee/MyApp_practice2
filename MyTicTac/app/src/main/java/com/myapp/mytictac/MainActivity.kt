package com.myapp.mytictac

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    protected fun buClick(view : View){
        val buSelected = view as Button
        var cellId = 0
        when(buSelected.id){

            R.id.button -> cellId =1
            R.id.button2 -> cellId =2
            R.id.button3 -> cellId =3
            R.id.button4 -> cellId =4
            R.id.button5 -> cellId =5
            R.id.button6 -> cellId =6
            R.id.button7 -> cellId =7
            R.id.button8 -> cellId =8
            R.id.button9 -> cellId =9

        }

        toast("ID : $cellId")

        PlayGame(cellId, buSelected)
    }

    var Player1 = ArrayList<Int>()
    var Player2 = ArrayList<Int>()
    var ACtivePlayer = 1

    fun PlayGame(cellID:Int, buSelected:Button){

        if(ACtivePlayer==1){
            buSelected.text ="X"
            buSelected.setBackgroundColor(Color.GREEN)
            Player1.add(cellID)

            ACtivePlayer = 2
            AutoPlay()
        }else{
            buSelected.text ="O"
            buSelected.setBackgroundColor(Color.BLUE)
            Player2.add(cellID)

            ACtivePlayer = 1
        }

        buSelected.isEnabled = false
        CheckWiner()
    }

    fun CheckWiner(){
        var Winer = -1

        //row1
        if(Player1.contains(1) && Player1.contains(2) && Player1.contains(3)){
            Winer=1
        }//row1
        if(Player2.contains(1) && Player2.contains(2) && Player2.contains(3)){
            Winer=2
        }

        //row2
        if(Player1.contains(4) && Player1.contains(5) && Player1.contains(6)){
            Winer=1
        }//row1
        if(Player2.contains(4) && Player2.contains(5) && Player2.contains(6)){
            Winer=2
        }

        //row3
        if(Player1.contains(7) && Player1.contains(8) && Player1.contains(9)){
            Winer=1
        }//row1
        if(Player2.contains(7) && Player2.contains(8) && Player2.contains(9)){
            Winer=2
        }

        //col 1
        if(Player1.contains(1) && Player1.contains(4) && Player1.contains(7)){
            Winer=1
        }//row1
        if(Player2.contains(1) && Player2.contains(4) && Player2.contains(7)){
            Winer=2
        }

        //col 2
        if(Player1.contains(2) && Player1.contains(5) && Player1.contains(8)){
            Winer=1
        }//row1
        if(Player2.contains(2) && Player2.contains(5) && Player2.contains(8)){
            Winer=2
        }

        //col 3
        if(Player1.contains(3) && Player1.contains(6) && Player1.contains(9)){
            Winer=1
        }//row1
        if(Player2.contains(3) && Player2.contains(6) && Player2.contains(9)){
            Winer=2
        }

        if(Winer!=-1){
         if(Winer==1){
             toast("Winner is Player1!")
         }else toast("Winner is Player2!")
        }
    }

    fun AutoPlay(){
        var emptyCells = ArrayList<Int>()
        for(cellID in 1..9){
            if(!(Player1.contains(cellID) || Player2.contains(cellID))){
                 emptyCells.add(cellID)
            }
        }

        var r = Random
        val randIndex = r.nextInt(emptyCells.size-0)+0
        val cellID = emptyCells.get(randIndex)

        var buSelect : Button?
        when(cellID){
            1->buSelect = button
            2->buSelect = button2
            3->buSelect = button3
            4->buSelect = button4
            5->buSelect = button5
            6->buSelect = button6
            7->buSelect = button7
            8->buSelect = button8
            9->buSelect = button9
            else -> {
                buSelect = button
            }
        }

        PlayGame(cellID, buSelect)
    }

   protected fun Reset(view : View){

       button.isEnabled = true
       button2.isEnabled = true
       button3.isEnabled = true
       button4.isEnabled = true
       button5.isEnabled = true
       button6.isEnabled = true
       button7.isEnabled = true
       button8.isEnabled = true
       button9.isEnabled = true

       button.setBackgroundColor(Color.LTGRAY)
       button2.setBackgroundColor(Color.LTGRAY)
       button3.setBackgroundColor(Color.LTGRAY)
       button4.setBackgroundColor(Color.LTGRAY)
       button5.setBackgroundColor(Color.LTGRAY)
       button6.setBackgroundColor(Color.LTGRAY)
       button7.setBackgroundColor(Color.LTGRAY)
       button8.setBackgroundColor(Color.LTGRAY)
       button9.setBackgroundColor(Color.LTGRAY)

       button.text =""
       button2.text =""
       button3.text =""
       button4.text =""
       button5.text =""
       button6.text =""
       button7.text =""
       button8.text =""
       button9.text =""

       Player1.clear()
       Player2.clear()
       ACtivePlayer = 1
   }
}
