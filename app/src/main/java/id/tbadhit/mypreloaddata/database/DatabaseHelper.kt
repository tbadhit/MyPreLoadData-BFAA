package id.tbadhit.mypreloaddata.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID
import id.tbadhit.mypreloaddata.database.DatabaseContract.MahasiswaColumn.Companion.NAMA
import id.tbadhit.mypreloaddata.database.DatabaseContract.MahasiswaColumn.Companion.NIM
import id.tbadhit.mypreloaddata.database.DatabaseContract.TABLE_NAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_DATABASE_MAHASISWA)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "dbmahasiswa"

        private const val DATABASE_VERSION = 1

        private val CREATE_DATABASE_MAHASISWA = "create table $TABLE_NAME ($_ID integer primary key autoincrement, $NAMA text not null, $NIM text not null);"
    }
}