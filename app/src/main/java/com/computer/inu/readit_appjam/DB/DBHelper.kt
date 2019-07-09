package com.computer.inu.readit_appjam.DB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.computer.inu.readit_appjam.Data.LatestSearchKeyword

class DBHelper(ctx: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(ctx, DB_NAME, factory, DB_VERSION) {

    companion object {
        private val DB_NAME = "searchKeywordDB"
        private val DB_VERSION = 1
        val TABLE_NAME = "searchKeywords"
        val COLUMN_ID = "id"
        val COLUMN_KEYWORD = "searchKeyword"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE = ("CREATE TABLE " +
                TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_KEYWORD + " TEXT)")
        db?.execSQL(CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun add(keyword: String) {
        val values = ContentValues()
        values.put(COLUMN_KEYWORD, keyword)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun delete(keyword: String): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "ID = ?", arrayOf(keyword))
    }

    fun getAllKeyword(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME Order by ID DESC limit 8", null)
    }

    /*fun toList(): ArrayList<LatestSearchKeyword>{

    }*/
}

/*
class Keword {
    var id: Int = 0
    var keyword: String? = null

    constructor(id: Int, keyword: String) {
        this.id = id
        this.keyword = keyword
    }

    constructor(keyword: String) {
        this.keyword = keyword
    }
}*/
