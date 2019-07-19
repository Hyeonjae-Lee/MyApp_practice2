package com.myapp.mywebbrowser

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.email
import org.jetbrains.anko.sendSMS
import org.jetbrains.anko.share

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WebView.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
        }


        WebView.loadUrl("https://google.com")


        //키보드의 검색 버튼 동작 정의하기
        //에디터텍스트가 선택되고 글자가 입력될 때마다 호출된다. 인자로는 반응한 뷰, 액션 ID, 이벤트 세 가지이며
        //여기서는 뷰와 이벤트를 사용하지 않기 때문에 _로 대치될 수 있다.
        TextView.setOnEditorActionListener { _, actionId, _->
            //액션 아이디는 editorInfo 클래스에 상수로 정의된 값 중 검색 버튼에 해당하는 상수와 비교하여 검색 버튼이
            //눌렀는지 확인합니다.
            if(actionId==EditorInfo.IME_ACTION_SEARCH){
                WebView.loadUrl("https://" + TextView.text.toString())
                true
            }else{
                false
            }
        }

        //컨텍스트 메뉴 지정
        registerForContextMenu(WebView)
    }

    override fun onBackPressed() {//뒤로 가기 버튼을 눌렀을 때 오버라이드를 해야 한다.

        if(WebView.canGoBack()){//뒤로 갈 수 있는가가
           WebView.goBack()
        }else{
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //메뉴 리소스를 액티비티의 메뉴로 표시하려면 menuInflater 객체의 inflate()객체의 inflate()메서드를 사용하여
        //메뉴 리소스를 지정한다
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.action_Google, R.id.action_home -> {
                WebView.loadUrl("https://google.com"); return true
            }
            R.id.action_naver ->{
                WebView.loadUrl("https://naver.com"); return true
            }
            R.id.action_youtube -> {
                WebView.loadUrl("https://youtube.com"); return true
            }
            R.id.Developer_email->{
                email("ieehyeon@naver.com", "fuck you nigar!", WebView.url)
                //send email
                return true
            }
            R.id.Developer_call ->{
                val intent = Intent(Intent.ACTION_DIAL)//암시적 인텐트
                intent.data = Uri.parse("tel:010-8664-7152")

                if(intent.resolveActivity(packageManager)!=null){
                    startActivity(intent)
                }
                return true
            }
            R.id.Developer_MMS ->{
                sendSMS("010-8664-7152", "fuck you niga!")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {

        super.onCreateContextMenu(menu, v, menuInfo)

        menuInflater.inflate(R.menu.context, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        when(item?.itemId){
            R.id.action_share ->{
                share(WebView.url)
            }
            R.id.action_basicBrowser -> {
                browse(WebView.url)
            }
        }

        return super.onContextItemSelected(item)
    }
}
