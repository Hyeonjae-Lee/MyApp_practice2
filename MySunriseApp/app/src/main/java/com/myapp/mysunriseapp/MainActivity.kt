package com.myapp.mysunriseapp

import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    protected fun GetSunset(view: View){
        val apiKey = "b48c6738213e96f3d6c8126072fffb85"
        var city =etCityName.text.toString()
        val url = "https://api.openweathermap.org/data/2.5/weather?q=$city&APPID=$apiKey

        MyAsyncTask().execute(url)
    }

    inner class MyAsyncTask:AsyncTask<String, String, String>{

       /* override fun onProgressUpdate(vararg values: String?){

            try{
                var json= JSONObject(values[0])
                val sys=json.getJSONObject("sys")
                var sunrise=sys.getString("sunrise")

                //convert unix time stamp into java.util.Date day and time format
                var srTime = Date(sunrise.toLong() * 1000)
                //update text view with date & time of sunrise
                tvSunsetTime.text = "Sunrise time is $srTime"


            }catch(ex:Exception){}

        }*/

        override fun onPreExecute() {
            //Before task started
        }

        override fun doInBackground(vararg p0: String?): String {
            //TODO http call
            try{
                var url = URL(p0[0])

                val urlConnect = url.openConnection() as HttpsURLConnection
                urlConnect.connectTimeout =7000

                var inString=ConvertStreamtoString(urlConnect.inputStream)
            }catch(ex:Exception){

            }
        }

        override fun onPostExecute(vararg result: String?) {
           //after task done
        }

        override fun onProgressUpdate(vararg values: String?) {
            super.onProgressUpdate(*values)
        }

        override fun onPostExecute(result: String?) {
            //after task done
        }

    }

    fun ConvertStreamtoString(inputStream:InputStream):String{
        val bufferReader = BufferedReader(InputStreamReader(inputStream))

    }

}
