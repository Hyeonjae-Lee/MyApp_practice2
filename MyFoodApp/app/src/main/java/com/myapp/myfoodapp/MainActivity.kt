package com.myapp.myfoodapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.food_ticket.view.*

class MainActivity : AppCompatActivity() {

    var listOfFood = ArrayList<Food>()
    var adapter:FoodAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //load foods
        listOfFood.add(Food("Coffee","Coffee is my life, and finally the liquid coffee must bie separated from the used grounds", R.drawable.coffee_pot))
        listOfFood.add(Food("Espresso","Espresso is my life, and finally the liquid coffee must bie separated from the used grounds", R.drawable.espresso))
        listOfFood.add(Food("French Fries","French Fries is my life, and finally the liquid coffee must bie separated from the used grounds", R.drawable.french_fries))
        listOfFood.add(Food("Honey","Honey is my life, and finally the liquid coffee must bie separated from the used grounds", R.drawable.honey))
        listOfFood.add(Food("Strawbery ice cream","Strawbery ice cream is my life, and finally the liquid coffee must bie separated from the used grounds", R.drawable.strawberry_ice_cream))
        listOfFood.add(Food("Sugar Cubes","Sugar Cubes is my life, and finally the liquid coffee must bie separated from the used grounds", R.drawable.sugar_cubes))

        adapter = FoodAdapter(this, listOfFood)

        gvListFood.adapter = adapter
    }

    class FoodAdapter:BaseAdapter{
        var listOfFood = ArrayList<Food>()
        var context:Context?=null

        constructor(context:Context, listOfFood:ArrayList<Food>):super(){
            this.context=context
            this.listOfFood=listOfFood
        }

        override fun getView(index: Int, p1: View?, p2: ViewGroup?): View {
            var food = listOfFood[index]
            var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var foodView = inflator.inflate(R.layout.food_ticket, null)
            foodView.ivfoodImage.setImageResource(food.image!!)

            foodView.ivfoodImage.setOnClickListener {

                val intent = Intent(context, FoodDetails::class.java)
                intent.putExtra("name", food.name!!)
                intent.putExtra("des", food.des!!)
                intent.putExtra("image", food.image!!)
                context!!.startActivity(intent)
            }
            foodView.tvName.text = food.name!!
            return foodView
        }

        override fun getItem(p0: Int): Any {
            return listOfFood[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
            return listOfFood.size
        }

    }
}
