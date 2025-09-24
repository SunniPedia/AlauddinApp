package com.srizwan.bookhozur14

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

class Main4Activity : AppCompatActivity() {
    private lateinit var back: ImageView
    private lateinit var listView1: ListView
    private val json = Intent()
    private lateinit var name: Array<String>
    private lateinit var author: Array<String>
    private lateinit var bookid: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main4)
        back = findViewById(R.id.back)
        listView1 = findViewById(R.id.listview1)
        back.setOnClickListener { finish() }
        listView1.setOnItemClickListener { _, _, position, _ ->
            json.setClass(applicationContext, ReadingActivity::class.java)
            json.putExtra("name", name[position])
            json.putExtra("bookname", bookid[position])
            json.putExtra("author", author[position])
            startActivity(json)
            Toast.makeText(applicationContext, "${name[position]}গ্রন্থটি চালু হচ্ছে...", Toast.LENGTH_SHORT).show()
        }

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            val w: Window = window
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            w.statusBarColor = Color.parseColor("#FF00BCD4")
            w.navigationBarColor = Color.parseColor("#FF00BCD4")
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val jsonArray = getJSonData("book.json")
        val listItems = getArrayListFromJSONArray(jsonArray)
        val adapter = ListAdapter(this, R.layout.list_layout, listItems)
        listView1.adapter = adapter
    }

    private fun getJSonData(fileName: String): JSONArray? {
        var jsonArray: JSONArray? = null
        try {
            val `is`: InputStream = resources.assets.open(fileName)
            val size: Int = `is`.available()
            val data = ByteArray(size)
            `is`.read(data)
            `is`.close()
            val json = String(data, Charsets.UTF_8)
            jsonArray = JSONArray(json)
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return jsonArray
    }

    private fun getArrayListFromJSONArray(jsonArray: JSONArray?): ArrayList<JSONObject> {
        val aList = ArrayList<JSONObject>()
        try {
            if (jsonArray != null) {
                name = Array(jsonArray.length()) { "" }
                author = Array(jsonArray.length()) { "" }
                bookid = Array(jsonArray.length()) { "" }
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    aList.add(jsonObject)
                    name[i] = jsonObject.getString("name")
                    author[i] = jsonObject.getString("author")
                    bookid[i] = jsonObject.getString("bookid")
                }
            }
        } catch (je: JSONException) {
            je.printStackTrace()
        }
        return aList
    }
}

