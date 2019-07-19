package com.myapp.mygallery

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    private val REQUEST_READ_EXTERNAL_STORAGE = 1000//1000이 1초

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            //권한이 허용되지 않음
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                //사전에 권한이 거부되었을 때 반응
                alert("사진 정보를 얻으려면 외부 저장소 권한이 필수로 필요합니다", "권한이 필요한 이유"){
                    yesButton {
                        //권한 요청
                        ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_READ_EXTERNAL_STORAGE)
                    }
                    noButton {

                    }
                }.show()
            }else{
                //사전에 권한이 거부되지 않았을 때
                //권한 요청
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_READ_EXTERNAL_STORAGE)
            }
        }else{
            //권한 허용됨
            getAllPhotos()
        }
    }

    private fun getAllPhotos(){
        //모든 사진 정보 가져오기
        val cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            MediaStore.Images.ImageColumns.DATE_TAKEN+" DESC")//찍은 날짜 내림차순

       val fragments = ArrayList<Fragment>()
        if(cursor!=null){
            while(cursor.moveToNext()){
                //사진 경로 uri 가져오기
                val uri = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                Log.d("MainActivity", uri)

                fragments.add(PhotoFragment.newInstance(uri))
            }
            cursor.close()
        }

       //어댑터
       val adapterss = MyPagerAdapter(supportFragmentManager)
        adapterss.updateFragments(fragments)

        ViewPager.adapter = adapterss
       // viewPager.adapter = adapter

        timer(period = 3000){
            runOnUiThread {
                if(ViewPager.currentItem <= adapterss.count -1){
                    ViewPager.currentItem = ViewPager.currentItem + 1
                }else{
                    ViewPager.currentItem = 0
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            requestCode-> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //권한 허용됨
                    getAllPhotos()
                } else {
                    //권한 거부
                    toast("권한 거부 됨")
                }
                return
            }
        }
    }
}
