package com.example.hrapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HRDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "hr.db"
        const val DATABASE_VERSION = 1

        const val TABLE_EMPLOYEES = "employees"
        const val COL_ID = "id"
        const val COL_NAME = "name"
        const val COL_POSITION = "position"
        const val COL_SALARY = "salary"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_EMPLOYEES (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NAME TEXT NOT NULL,
                $COL_POSITION TEXT NOT NULL,
                $COL_SALARY REAL NOT NULL
            )
        """.trimIndent()

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_EMPLOYEES")
        onCreate(db)
    }
}
