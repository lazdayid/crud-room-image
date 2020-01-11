package com.lazday.crudroomimagekotlin.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.lazday.crudroomimagekotlin.R
import com.lazday.crudroomimagekotlin.room.DataModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), MainAdapter.AdapterListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        recyclerView.layoutManager = LinearLayoutManager(this)
        mainAdapter = MainAdapter(this)
        recyclerView.adapter = mainAdapter

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.getListData().observe(this, Observer {

            setData( it )
            Log.d("MainActivity", "size: ${it.size}")

            mainAdapter.setData( it )
        })

        fab.setOnClickListener { view ->
            startActivity(Intent(this, CreateActivity::class.java))
        }
    }

    private fun setData(dataModel: List<DataModel>){
        for (data in dataModel) {
            Log.d("MainActivity", "title: ${data.title}")
            Log.d("MainActivity", "image: ${data.image}")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDeleteClicked(dataModel: DataModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onViewClicked(dataModel: DataModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
