package com.brain.dreamnote.business.placeholder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.brain.arch.arch.BaseDaggerFragment
import com.brain.arch.di.DaggerFillable
import javax.inject.Inject

class PlaceHolderFragment : BaseDaggerFragment(), DaggerFillable {

    @Inject
    lateinit var data: PlaceHolderFragData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.placeholder_fragment_place_holder, container, false)
        initView(view)
        return view
    }


    fun initView(view: View) {
        view.findViewById<TextView>(R.id.tv_content).text = data.value
    }

}