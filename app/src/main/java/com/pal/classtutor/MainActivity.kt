package com.pal.classtutor

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.JsonObject
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var myTextView: TextView
    private lateinit var recyclerviewUser: RecyclerView
    private var userList : ArrayList<UserModel> = ArrayList()
    private lateinit var userDataAdapter: UserDataAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        myTextView = findViewById<TextView>(R.id.txtView)
        recyclerviewUser = findViewById<RecyclerView>(R.id.recyclerviewUser)
        callApi()

    }
    private fun callApi(){
        // Instantiate the RequestQueue
        val queue = Volley.newRequestQueue(this@MainActivity)
       // val url = "https://raw.githubusercontent.com/gayanvoice/volley-kotlin/master/data/sample.txt"
        val url ="https://reqres.in/api/users"
        // Request a string response from the provided URL
        val stringRequest = StringRequest(Request.Method.GET, url,
            object: Response.Listener<String> {
                override fun onResponse(response: String) {
                    Log.e("#@@@@JAPAN","$response")
                    val jsonObject = JSONObject(response)
                    val perPageValue = jsonObject.getInt("per_page")
                    val totalValue = jsonObject.getInt("total")
                    Log.e("#@@@@JAPAN","$perPageValue $totalValue")

                    val getSupport = jsonObject.getJSONObject("support")
                    val getUrl = getSupport.getString("url")
                    val getText = getSupport.getString("text")

                    //jsonArray
                    val getDataJsonAry =jsonObject.getJSONArray("data")
                    for (len in 0 until getDataJsonAry.length()){
                        val getObject =getDataJsonAry.getJSONObject(len)
                        val id = getObject.getInt("id")
                        val email = getObject.getString("email")
                        val first_name = getObject.getString("first_name")
                        val last_name = getObject.getString("last_name")
                        val avatar = getObject.getString("avatar")
                        userList.add(UserModel(id,email,first_name,last_name,avatar))
                        implementRecycleyerview()
                    }

                    myTextView.text = "Total pages $perPageValue $totalValue \n $getText"

                }
            },
            object:Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {
                    Log.e("#@@@@JAPANEE","$error")
                }
            })
        // Add the request to the RequestQueue.
        queue!!.add(stringRequest)
    }

    private fun implementRecycleyerview(){
        recyclerviewUser.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            userDataAdapter = UserDataAdapter(this@MainActivity)
            adapter = userDataAdapter
            userDataAdapter.setNewList(userList)
        }
    }

}