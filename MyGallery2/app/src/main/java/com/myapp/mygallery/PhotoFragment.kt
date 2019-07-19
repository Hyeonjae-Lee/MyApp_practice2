package com.myapp.mygallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_photo.*

private const val ARG_URL = "uri"

class PhotoFragment : Fragment() {

    private var uri : String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
           uri = it.getString(ARG_URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(this).load(uri).into(imageView)
        //with(this)로 사용 준비를 하고 load메서드에 uri값을 인자로 주고 해당 이미지를 부드럽게 로딩한다.
    }

    companion object {
        @JvmStatic
        fun newInstance(uri : String) =
            PhotoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_URL, uri)
                }
            }
    }
}
