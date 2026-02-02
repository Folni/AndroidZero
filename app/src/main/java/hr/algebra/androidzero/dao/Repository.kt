package hr.algebra.androidzero.dao

import android.content.ContentValues
import android.database.Cursor

// Ovaj interface ostaje identičan jer radi s ContentValues i Cursorom,
// što su standardni Android objekti za bilo koju vrstu podataka.
interface Repository {
    fun delete(selection: String?, selectionArgs: Array<String>?): Int

    fun update(
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int

    fun query(
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor

    fun insert(values: ContentValues?): Long
}