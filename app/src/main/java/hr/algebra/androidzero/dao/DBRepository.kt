package hr.algebra.androidzero.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import hr.algebra.androidzero.model.Item

private const val DB_NAME = "items.db"
// Povećavamo verziju na 3 (ili više) kako bi Android obrisao staru tablicu i kreirao novu s 'price' poljem
private const val DB_VERSION = 3
private const val TABLE_NAME = "items"

// SQL naredba prilagođena tvojim artiklima
private val CREATE_TABLE = "create table $TABLE_NAME( " +
        "${Item::_id.name} integer primary key autoincrement, " +
        "${Item::title.name} text not null, " +
        "${Item::explanation.name} text not null, " + // Ovdje ide description
        "${Item::picturePath.name} text not null, " +
        "${Item::price.name} real not null, " +       // REAL se koristi za Double cijenu
        "${Item::read.name} integer not null" +
        ")"

private const val DROP_TABLE = "drop table $TABLE_NAME"

class DBRepository(context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION),
    Repository {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Ako promijeniš verziju baze, stara se briše i radi se nova
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    override fun delete(selection: String?, selectionArgs: Array<String>?) =
        writableDatabase.delete(TABLE_NAME, selection, selectionArgs)

    override fun update(values: ContentValues?, selection: String?, selectionArgs: Array<String>?) =
        writableDatabase.update(TABLE_NAME, values, selection, selectionArgs)

    override fun insert(values: ContentValues?) =
        writableDatabase.insert(TABLE_NAME, null, values)

    override fun query(
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor = readableDatabase.query(
        TABLE_NAME,
        projection,
        selection,
        selectionArgs,
        null,
        null,
        sortOrder
    )
}