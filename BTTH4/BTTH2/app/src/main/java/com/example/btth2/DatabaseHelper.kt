package com.example.btth2

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "ContactDB", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE Contact (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS Contact")
        onCreate(db)
    }

    fun insertData(name: String, phone: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", name)
            put("phone", phone)
        }
        db.insert("Contact", null, values)
        db.close()
    }

    fun updateData(id: Int, name: String, phone: String): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", name)
            put("phone", phone)
        }
        val result = db.update("Contact", values, "id=?", arrayOf(id.toString()))
        db.close()
        return result
    }

    fun deleteData(id: Int): Int {
        val db = writableDatabase
        val result = db.delete("Contact", "id=?", arrayOf(id.toString()))
        db.close()
        return result
    }

    fun getContacts(): List<Triple<Int, String, String>> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Contact", null)
        val contacts = mutableListOf<Triple<Int, String, String>>()
        while (cursor.moveToNext()) {
            contacts.add(Triple(cursor.getInt(0), cursor.getString(1), cursor.getString(2)))
        }
        cursor.close()
        db.close()
        return contacts
    }
}