package com.example.hrapp

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.listEmployees)
        val btnAdd = findViewById<Button>(R.id.btnAdd)

        btnAdd.setOnClickListener {
            startActivity(Intent(this, AddEmployeeActivity::class.java))
        }

        loadEmployees(listView)
    }

    override fun onResume() {
        super.onResume()
        loadEmployees(findViewById(R.id.listEmployees))
    }

    private fun loadEmployees(listView: ListView) {
        val cursor: Cursor? = contentResolver.query(
            EmployeeProvider.EMPLOYEE_URI,
            null, null, null, null
        )

        val list = mutableListOf<String>()

        cursor?.apply {
            while (moveToNext()) {
                val name = getString(getColumnIndexOrThrow("name"))
                val position = getString(getColumnIndexOrThrow("position"))
                val salary = getDouble(getColumnIndexOrThrow("salary"))
                list.add("$name - $position - $$salary")
            }
            close()
        }

        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
    }
}
