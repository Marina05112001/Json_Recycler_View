package com.example.myapplication19053

import android.os.Bundle
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    private lateinit var itemAdapter: ItemAdapter
    private val items = arrayListOf<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        itemAdapter = ItemAdapter(this, items)
        recyclerView.adapter = itemAdapter

        readJson()
    }

    private fun readJson() {
        var json: String? = null

        try {
            val inputStream: InputStream = assets.open("file.json")
            json = inputStream.bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(json)

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val name = jsonObject.getString("name")
                val details = jsonObject.get("details")

                if (details is String) {
                    items.add(Item(name, details))
                } else if (details is JSONArray) {
                    val subItems = arrayListOf<SubItem>()
                    for (j in 0 until details.length()) {
                        val subObject = details.getJSONObject(j).keys().next()
                        val subItem = details.getJSONObject(j).getJSONObject(subObject)
                        subItems.add(SubItem(subItem.getString("name"), subItem.getString("articul"), subItem.getString("image")))
                    }
                    items.add(Item(name, subItems))
                }
            }
            itemAdapter.notifyDataSetChanged()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}