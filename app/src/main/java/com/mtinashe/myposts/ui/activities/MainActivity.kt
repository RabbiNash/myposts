package com.mtinashe.myposts.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mtinashe.myposts.R
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class MainActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
