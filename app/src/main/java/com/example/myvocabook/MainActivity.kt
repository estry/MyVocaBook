package com.example.myvocabook

import android.content.res.AssetManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_words, R.id.navigation_todaywords,
                R.id.navigation_bookmark
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }



    private fun init() {
        val assetManager: AssetManager = resources.assets
        val inputStream: InputStream = assetManager.open("voca2.txt")
        var cnt = 1L
        /*inputStream.bufferedReader().readLines().forEach {
            var token = it.split("::")
            var input = Vocabulary(cnt, token[0], token[1], token[2], false)
            cnt++
            CoroutineScope(Dispatchers.Main).launch {
                Log.d("file_test", token.toString())
                AppDataBase.getInstance(applicationContext)
                    .vocabularyDao().insert(input)
            }
        }*/
        CoroutineScope(Dispatchers.IO).launch {
            val output = AppDataBase.getInstance(applicationContext)
                .vocabularyDao().getAll()
            Log.d("db_test", "$output")
        }
    }
}




