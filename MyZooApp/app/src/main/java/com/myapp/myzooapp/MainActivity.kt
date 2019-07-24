package com.myapp.myzooapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.animal_ticket.view.*

class MainActivity : AppCompatActivity() {

    var listOfAnimals = ArrayList<Animal>()
    var adapter:AnimalAdapter ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //load animals
        listOfAnimals.add(Animal("Babon", "Babon live in big place with tree", R.drawable.baboon, false))
        listOfAnimals.add(Animal("bulldog", "bulldog live in big place with tree", R.drawable.bulldog, false))
        listOfAnimals.add(Animal("panda", "panda live in big place with tree", R.drawable.panda, true))
        listOfAnimals.add(Animal("swallow_bird", "swallow_bird live in big place with tree", R.drawable.swallow_bird, false))
        listOfAnimals.add(Animal("white_tiger", "white_tiger live in big place with tree", R.drawable.white_tiger, true))
        listOfAnimals.add(Animal("zebra", "zebra live in big place with tree", R.drawable.zebra, false))

        adapter = AnimalAdapter(this, listOfAnimals)
        tvListAnimal.adapter = adapter
    }

    fun delete(index:Int){
        listOfAnimals.removeAt(index)
        adapter!!.notifyDataSetChanged()
    }

    fun add(index : Int){
        listOfAnimals.add(index, listOfAnimals[index])
        adapter!!.notifyDataSetChanged()
    }

    inner class AnimalAdapter:BaseAdapter{

        var listofAnimals = ArrayList<Animal>()
        var context:Context?=null

        constructor(context: Context, listofAnimals : ArrayList<Animal>) :super(){
            this.listofAnimals=listofAnimals
            this.context = context
        }

        override fun getView(index: Int, p1: View?, p2: ViewGroup?): View {

            val animal = listofAnimals[index]

            if (animal.isKiller == true){
                var inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

                var myView = inflater.inflate(R.layout.animal_killer_ticket, null)
                myView.textViewInfo.text = animal.des!!
                myView.textViewName.text = animal.name!!
                myView.AnimalImage.setImageResource(animal.image!!)

                myView.AnimalImage.setOnClickListener {
                    add(index)

                   /* val intent1 = Intent(context, AnimalInfo::class.java)
                    intent1.putExtra("name", animal.name!!)
                    intent1.putExtra("des", animal.des!!)
                    intent1.putExtra("image", animal.image!!)
                    context!!.startActivity(intent1)*/

                }

                myView.MyInfo.setOnClickListener {
                    delete(index)
                }
                myView.MyInfo.setOnClickListener { delete(index) }
                return myView
            }else {
                var inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

                var myView = inflater.inflate(R.layout.animal_ticket, null)
                myView.textViewInfo.text = animal.des!!
                myView.textViewName.text = animal.name!!
                myView.AnimalImage.setImageResource(animal.image!!)



                myView.AnimalImage.setOnClickListener {
                    add(index)
                    /*
                    val intent1 = Intent(context, AnimalInfo::class.java)
                    intent1.putExtra("name", animal.name!!)
                    intent1.putExtra("des", animal.des!!)
                    intent1.putExtra("image", animal.image!!)
                    context!!.startActivity(intent1)
                    */
                }

                myView.MyInfo.setOnClickListener {
                    delete(index)
                }
                myView.MyInfo.setOnClickListener { delete(index) }

                return myView
            }
        }

        override fun getItem(p0: Int): Any {
            return listofAnimals[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
            return listofAnimals.size
        }

    }
}
