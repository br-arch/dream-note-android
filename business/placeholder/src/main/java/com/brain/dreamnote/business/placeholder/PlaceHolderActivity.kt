package com.brain.dreamnote.business.placeholder

import android.os.Bundle
import com.brain.arch.arch.BaseDaggerActivity
import com.brain.arch.di.DaggerFillable
import javax.inject.Inject

class PlaceHolderActivity : BaseDaggerActivity(), DaggerFillable {

    @Inject
    lateinit var data: PlaceHolderActData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.placeholder_activity_place_holder)
        initView()
    }

    fun initView() {
//        findViewById<TextView>(R.id.tv_content).text = data.value
        val fragment = PlaceHolderFragment()
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }
}
