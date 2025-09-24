package com.srizwan.bookofalauddin

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Main1Activity : AppCompatActivity() {
    private var a = ""
    private var b = ""
    private lateinit var button1: LinearLayout
    private lateinit var imageView5: ImageView
    private lateinit var imageView3: ImageView
    private lateinit var imageView4: ImageView
    private lateinit var imageView7: ImageView
    private lateinit var back: ImageView
    private val gmail = Intent()
    private val share = Intent()
    private val rate = Intent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main1)

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            window.apply {
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                statusBarColor = Color.parseColor("#021921")
                navigationBarColor = Color.parseColor("#021921")
            }
        }

        button1 = findViewById(R.id.button1)
        imageView3 = findViewById(R.id.imageView3)
        imageView4 = findViewById(R.id.imageView4)
        imageView5 = findViewById(R.id.imageView5)
        imageView7 = findViewById(R.id.imageview7)

        val sketchU = GradientDrawable().apply {
            val d = applicationContext.resources.displayMetrics.density.toInt()
            setStroke(d, 0xFF008DCD.toInt())
            cornerRadius = d * 12f
            setColor(0xFFFFFFFF.toInt())
        }

        button1.apply {
            elevation = 6f * resources.displayMetrics.density
            background = sketchU
        }

        back = findViewById(R.id.back)
        back.setOnClickListener { imageView5.performClick() }

        button1.setOnClickListener {
            startActivity(Intent(this@Main1Activity, Main4Activity::class.java))
        }

        imageView3.setOnClickListener {
            gmail.apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse("mailto:muhammodrizwan0@gmail.com")
            }
            startActivity(gmail)
            Toast.makeText(this@Main1Activity, "Report us", Toast.LENGTH_SHORT).show()
        }

        imageView4.setOnClickListener {
            rate.apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse("https://play.google.com/apps/details?id=com.srizwan.bookofalauddin")
            }
            startActivity(rate)
            Toast.makeText(this@Main1Activity, "Rate us", Toast.LENGTH_SHORT).show()
        }

        imageView7.setOnClickListener {
            a = "Share app now"
            b = "https://play.google.com/apps/details?id=com.srizwan.bookofalauddin"
            share.apply {
                type = "text/plain"
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_SUBJECT, a)
                putExtra(Intent.EXTRA_TEXT, b)
            }
            startActivity(Intent.createChooser(share, "Share app now"))
            Toast.makeText(this@Main1Activity, "Share app", Toast.LENGTH_SHORT).show()
        }

        imageView5.setOnClickListener { showExitDialog() }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun showExitDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("Are you want to exit?")
            setPositiveButton("Yes") { _, _ -> finishAffinity() }
            setNegativeButton("No") { _, _ -> }
            create()
            show()
        }
    }
    override fun onBackPressed() {
        showExitDialog()
    }
}