package com.example.karakia.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.karakia.R
import com.example.karakia.data.Item
import com.example.karakia.data.ItemAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_items.*

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), ItemAdapter.OnItemClickListener {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val exampleList = generateList(5)
    private val adapter = ItemAdapter(exampleList,this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        //recycler_view.adapter = adapter
        //recycler_view.layoutManager = LinearLayoutManager(this)
        //recycler_view.setHasFixedSize(true)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.aboutFragment),
            drawer_layout
        )
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController,appBarConfiguration)
        bottom_nav.setupWithNavController(navController)
        nav_view.setupWithNavController(navController)

    }
    override fun onItemClick(position: Int) {
        Toast.makeText(this, "Item $position clicked", Toast.LENGTH_SHORT).show()
        val clickedItem = exampleList[position]
        clickedItem.text1 = "Clicked"
        adapter.notifyItemChanged(position)
    }
    private fun generateList(size: Int): ArrayList<Item> {

        val list = ArrayList<Item>()

        for (i in 0 until size) {
             val item = Item(R.drawable.ic_play, "Item $i", "Line 2")
            list += item
        }

        return list
    }
}
