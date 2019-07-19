package com.myapp.mymap2

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    private lateinit var locationRequest : LocationRequest
    private lateinit var locationCallback : MyLocationCallBack

    private val REQUEST_ACCESS_FINE_LOCATION = 1000

    private val polyLineOptions = PolylineOptions().width(5f).color(Color.RED)

    override fun onCreate(savedInstanceState: Bundle?) {

        //화면이 꺼지지 않게 하기
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        //화면 세로 모드로 고정
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationInit()
    }

    //위치 정보를 얻기 위한 각종 초기화
    private fun locationInit(){
        fusedLocationProviderClient = FusedLocationProviderClient(this)
        locationCallback = MyLocationCallBack()

        locationRequest = LocationRequest()

        //gps우선
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        locationRequest.interval = 1000
        locationRequest.fastestInterval = 5000
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onResume() {
        super.onResume()

        permissionCheck(cancel = {
            //위치 정보가필요한 이유 다이얼로그 표시
            showPermissionInfoDialog()
        },
            //현재 위치를 주기적으로 표시;
            ok={
                addLocationListner()
            }
            )
    }

    @SuppressLint("MissingPermission")
    private fun addLocationListner(){
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    inner class MyLocationCallBack : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)

            val location = locationResult?.lastLocation

            location?.run{
                //14 level로 확대하고 현재 위치로 카메라 이동

                val latLng = LatLng(latitude, longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))

                Log.d("MapsActivity", "위도: $latitude, 경도 : $longitude")

                polyLineOptions.add(latLng)

                mMap.addPolyline(polyLineOptions)
            }
        }
    }

    private fun permissionCheck(cancel : () -> Unit, ok : () -> Unit){//함수 인자 두 개를 받는다
        //위치 권한이 있는지 검사
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            //권한이 허용되지 않았을 경우
            if(ActivityCompat.shouldShowRequestPermissionRationale(this@MapsActivity, android.Manifest.permission.ACCESS_FINE_LOCATION)){//잘 안되면 여기이다
                cancel()
            }else{
                //권한 요청
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_ACCESS_FINE_LOCATION )
            }

        }else{
            ok()
        }
    }
    
    private fun showPermissionInfoDialog(){
        alert("현재 위치 정보를 알려면 권한이 필요합니다", "위치 권한이 필요한 이유"){
            yesButton { 
                //권한 요청
                //yes블럭 내부에서 this는 DialInterface 객체를 의미한다. 따라서 명시적으로 MapsActivity를 설정해 주어야 한다
                ActivityCompat.requestPermissions(this@MapsActivity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_ACCESS_FINE_LOCATION )
            }
            noButton { 
                toast("권한을 허용해 주으세오!")
            }
        }.show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            REQUEST_ACCESS_FINE_LOCATION -> {
                if( grantResults.isNotEmpty() && (grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                    //권한이 요청된 경우
                    addLocationListner()
                }else{
                    toast("권한을 제발 허용해 주우으세요!")
                }
                return
            }
        }
    }

    override fun onPause() {
        super.onPause()

        removeLocationListner()
    }

    private fun removeLocationListner(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
}
