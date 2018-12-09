package com.prongbang.paging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.prongbang.paging.feature.user.ui.UserFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, UserFragment.newInstance())
                .commitNow()
        }
    }

}
