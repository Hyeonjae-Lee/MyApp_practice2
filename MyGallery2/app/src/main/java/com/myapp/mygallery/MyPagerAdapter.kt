package com.myapp.mygallery

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MyPagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

    //뷰페이지가 표시할 프레그먼트 목록
    private val items = ArrayList<Fragment>()

    override fun getItem(position: Int): Fragment {
       return items[position]
    }

    override fun getCount(): Int {
        return items.size
    }

    fun updateFragments(items : List<Fragment>){
        this.items.addAll(items)
    }

}