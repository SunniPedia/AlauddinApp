package com.srizwan.bookofalauddin

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class ReadingActivity : AppCompatActivity() {
    private lateinit var back: ImageView
    private lateinit var listView1: ListView
    private lateinit var name: Array<String>
    private lateinit var author: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reading)

        back = findViewById(R.id.back)
        val heading1: TextView = findViewById(R.id.heading)
        val writer1: TextView = findViewById(R.id.writer)
        heading1.text = intent.getStringExtra("name")
        writer1.text = intent.getStringExtra("author")

        listView1 = findViewById(R.id.listview1)
        listView1.isFastScrollEnabled = true

        back.setOnClickListener { finish() }

        listView1.setOnItemClickListener { _, _, position, _ ->
            if (author[position].isNotEmpty()) {
                val json = Intent(applicationContext, ViewActivity::class.java).apply {
                    putExtra("name", name[position])
                    putExtra("author", author[position])
                }
                startActivity(json)
            }
        }

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            window.apply {
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                statusBarColor = Color.parseColor("#FF00BCD4")
                navigationBarColor = Color.parseColor("#FF00BCD4")
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val jsonArray = getJSonData(intent.getStringExtra("bookname") ?: "")
        val listItems = getArrayListFromJSONArray(jsonArray)
        val adapter = ListAdapterA(this, R.layout.list_layout1, listItems)
        listView1.adapter = adapter
    }

    private fun getJSonData(fileName: String): JSONArray? {
        return try {
            resources.assets.open(fileName).use { inputStream ->
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                val json = String(buffer, Charsets.UTF_8)
                JSONArray(json)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        } catch (e: JSONException) {
            e.printStackTrace()
            null
        }
    }

    private fun getArrayListFromJSONArray(jsonArray: JSONArray?): ArrayList<JSONObject> {
        val aList = ArrayList<JSONObject>()
        jsonArray?.let {
            name = Array(it.length()) { "" }
            author = Array(it.length()) { "" }
            for (i in 0 until it.length()) {
                try {
                    val jsonObject = it.getJSONObject(i)
                    aList.add(jsonObject)
                    name[i] = jsonObject.getString("1")
                    author[i] = jsonObject.getString("2")
                } catch (je: JSONException) {
                    je.printStackTrace()
                }
            }
        }
        return aList
    }
}

