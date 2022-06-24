package com.intershala.covid19tracker

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException


class MainActivity : AppCompatActivity(){

    lateinit var txtWorldRecord: TextView
    lateinit var txtWorldCases: TextView
    lateinit var txtWorldRecovered:TextView
    lateinit var txtWorldDeaths: TextView

    lateinit var txtIndiaRecord:TextView
    lateinit var txtIndiaCases:TextView
    lateinit var txtIndiaRecovered: TextView
    lateinit var txtIndiaDeaths: TextView

    lateinit var RecyclerViewStates: RecyclerView
    lateinit var stateRecyclerViewAdapter:StateRecyclerViewAdapter
    lateinit var stateList:List<StateModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtWorldRecord=findViewById(R.id.txtWorldRecord)
        txtWorldCases=findViewById(R.id.txtWorldCases)
        txtWorldRecovered=findViewById(R.id.txtWorldRecovered)
        txtWorldDeaths=findViewById(R.id.txtWorldDeaths)

        txtIndiaRecord=findViewById(R.id.txtIndiaRecord)
        txtIndiaCases=findViewById(R.id.txtIndiaCases)
        txtIndiaRecovered=findViewById(R.id.txtIndiaRecovered)
        txtIndiaDeaths=findViewById(R.id.txtIndiaDeaths)

        RecyclerViewStates=findViewById(R.id.RecyclerViewStates)
        stateList=ArrayList<StateModel>()

        getStateinfo()
        getWorldInfo()
    }
    fun getStateinfo(){
        val url="https://api.rootnet.in/covid19-in/stats/latest"
        val queue= Volley.newRequestQueue(this@MainActivity)
        val request= JsonObjectRequest(Request.Method.GET,url,null,{ response->
            try {
                val dataObj=response.getJSONObject("data")
                val summaryObj=dataObj.getJSONObject("summary")
                val cases:Int=summaryObj.getInt("total")
                val recovered:Int=summaryObj.getInt("discharged")
                val deaths:Int=summaryObj.getInt("deaths")

                txtIndiaCases.text=cases.toString()
                txtIndiaRecovered.text=recovered.toString()
                txtIndiaDeaths.text=deaths.toString()

                val regionalArray=dataObj.getJSONArray("regional")
                for(i in 0 until regionalArray.length()){
                    val regionalObj=regionalArray.getJSONObject(i)
                    val state: String =regionalObj.getString("loc")
                    val cases: Int =regionalObj.getInt("totalConfirmed")
                    val recovered: Int =regionalObj.getInt("discharged")
                    val deaths: Int =regionalObj.getInt("deaths")
                    val stateModel=StateModel(state,recovered,deaths,cases)
                    stateList=stateList+stateModel
                }
                stateRecyclerViewAdapter=StateRecyclerViewAdapter(stateList)
                RecyclerViewStates.layoutManager=LinearLayoutManager(this)
                RecyclerViewStates.adapter=stateRecyclerViewAdapter
            }catch (e:JSONException){
                e.printStackTrace()
            }

        }, {
                error->
            {
            Toast.makeText(this,"Fail to get Data",Toast.LENGTH_SHORT).show()
        }
        })
        queue.add(request)
    }
    fun getWorldInfo(){
        val url="https://corona.lmao.ninja/v3/covid-19/all"
        val queue= Volley.newRequestQueue(this@MainActivity)
        val request= JsonObjectRequest(Request.Method.GET,url,null,{ response->
            try {
                val worldCases:Int=response.getInt("cases")
                val worldRecovered:Int=response.getInt("recovered")
                val worldDeaths:Int=response.getInt("deaths")
                txtWorldCases.text=worldCases.toString()
                txtWorldRecovered.text=worldRecovered.toString()
                txtWorldDeaths.text=worldDeaths.toString()

            }catch (e:JSONException){
                e.printStackTrace()
            }

        }, { error ->{
            Toast.makeText(this,"Fail to get Data",Toast.LENGTH_SHORT).show()
        }
        })
        queue.add(request)
    }

}

