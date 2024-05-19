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

//    var arr = arrayListOf<String>()
//    var type = arrayListOf<String>()

    private lateinit var itemAdapter: ItemAdapter
    private val items = arrayListOf<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        read_json()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        itemAdapter = ItemAdapter(this, items)
        recyclerView.adapter = itemAdapter

        readJson()
    }

//    fun read_json(){
//        var json_list = findViewById<ListView>(R.id.json_list)
//
//        var json: String? = null
//
//        try {
//            val inputStream: InputStream = assets.open("file.json")
//            json = inputStream.bufferedReader().use { it.readText() }
//
//            var jsonarr = JSONArray(json)
//
//            for(i in 0..jsonarr.length()-1){
//                var jsonobj = jsonarr.getJSONObject(i)
//                arr.add(jsonobj.getString("name"))
//                type.add(jsonobj.getString("type"))
//            }
//
//            val adpt = ArrayAdapter(this, android.R.layout.simple_list_item_1, arr)
//            json_list.adapter = adpt
//
//            json_list.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
//                Toast.makeText(applicationContext, "nazhato: " + type[position], Toast.LENGTH_LONG).show()
//            }
//        }
//        catch (e:IOException){
//
//        }
//    }

    private fun readJson() {
        var json: String? = null

        try {
            val inputStream: InputStream = assets.open("file.json")
            json = inputStream.bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(json)

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val name = jsonObject.getString("name")
                val type = jsonObject.get("type")

                if (type is String) {
                    items.add(Item(name, type))
                } else if (type is JSONArray) {
                    val subItems = arrayListOf<SubItem>()
                    for (j in 0 until type.length()) {
                        val subObject = type.getJSONObject(j).keys().next()
                        val subItem = type.getJSONObject(j).getJSONObject(subObject)
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