package com.example.hrapp

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri

class EmployeeProvider : ContentProvider() {

    companion object {
        const val AUTHORITY = "com.example.hrapp.provider"
        val EMPLOYEE_URI = Uri.parse("content://$AUTHORITY/employees")

        private const val EMPLOYEES = 1
        private const val EMPLOYEE_ID = 2

        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, "employees", EMPLOYEES)
            addURI(AUTHORITY, "employees/#", EMPLOYEE_ID)
        }
    }

    private lateinit var dbHelper: HRDatabaseHelper

    override fun onCreate(): Boolean {
        dbHelper = HRDatabaseHelper(context!!)
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val db = dbHelper.readableDatabase
        val qb = SQLiteQueryBuilder()
        qb.tables = HRDatabaseHelper.TABLE_EMPLOYEES

        return qb.query(db, projection, selection, selectionArgs, null, null, sortOrder)
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = dbHelper.writableDatabase
        val id = db.insert(HRDatabaseHelper.TABLE_EMPLOYEES, null, values)
        return Uri.withAppendedPath(EMPLOYEE_URI, id.toString())
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        val db = dbHelper.writableDatabase
        return db.update(HRDatabaseHelper.TABLE_EMPLOYEES, values, selection, selectionArgs)
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val db = dbHelper.writableDatabase
        return db.delete(HRDatabaseHelper.TABLE_EMPLOYEES, selection, selectionArgs)
    }

    override fun getType(uri: Uri): String? = null
}
