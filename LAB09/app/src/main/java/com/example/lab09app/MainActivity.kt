package com.example.lab09app

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val taskList = List(20) { index ->
            TaskItem(
                id = index + 1,
                title = "Zadanie #${index + 1}",
                category = if (index % 2 == 0) "Uczelnia" else "Dom"
            )
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)


































        recyclerView.adapter = TaskAdapter(taskList) { clickedTask ->
            Log.d("LAB9_LOGCAT", "Kliknięto element: ID=${clickedTask.id}, Tytuł='${clickedTask.title}'")
        }
    }
}
