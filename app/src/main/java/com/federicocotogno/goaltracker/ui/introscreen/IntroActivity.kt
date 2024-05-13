package com.federicocotogno.goaltracker.ui.introscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.federicocotogno.goaltracker.R
import com.federicocotogno.goaltracker.data.models.IntroView
import com.federicocotogno.goaltracker.ui.MainActivity
import com.federicocotogno.goaltracker.ui.introscreen.adapters.ViewPagerIntroAdapter
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {

    lateinit var introView: List<IntroView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        addToIntroView()

        viewPager2.adapter = ViewPagerIntroAdapter(introView)
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        circleIndicator.setViewPager(viewPager2)

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (position == 2) {
                    animationButton()
                }
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })
    }

    private fun animationButton() {
        btn_start_app.visibility = View.VISIBLE

        btn_start_app.animate().apply {
            duration = 1400
            alpha(1f)

            btn_start_app.setOnClickListener {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.start()
    }

    private fun addToIntroView() {
        //Create some items that you want to add to your viewpager

        introView = listOf(
            IntroView("Welcome to goal Tracker!", R.drawable.ic_tea),
            IntroView("This app is designed to keep track of your goals, " +
                    "whether it's a good one, or a bad one.", R.drawable.ic_fastfood),
            IntroView("Good luck! Tap on the button below to get started with using the app!", R.drawable.ic_smoking2),
        )
    }
}
