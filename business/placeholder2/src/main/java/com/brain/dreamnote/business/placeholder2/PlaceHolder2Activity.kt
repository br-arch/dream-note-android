package com.brain.dreamnote.business.placeholder2

import android.os.Bundle
import android.widget.TextView
import com.brain.arch.arch.BaseDaggerActivity
import com.brain.arch.di.DaggerFillable
import javax.inject.Inject


class PlaceHolder2Activity: BaseDaggerActivity(), DaggerFillable {

    @Inject
    lateinit var data: PlaceHolder2ActData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.placeholder2_activity_place_holder2)
        initView()
    }

    fun initView() {
        findViewById<TextView>(R.id.tv_content).text = data.value
    }
}