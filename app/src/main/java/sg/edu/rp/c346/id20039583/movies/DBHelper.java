package sg.edu.rp.c346.id20039583.movies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Movie;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_MOVIE = "Movie";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_RATING = "ratings";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createMovieTableSql = "CREATE TABLE " + TABLE_MOVIE + "("
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_GENRE + " TEXT, "
                + COLUMN_YEAR + " INTEGER, "
                + COLUMN_RATING + " INTEGER )";
        db.execSQL(createMovieTableSql);
        Log.i("info", createMovieTableSql + "\ncreated tables");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE);
        onCreate(db);
    }
    public long insertMovie(String title, String genre, int year, int rating) {
        // Get an instance of the database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_GENRE, genre);
        values.put(COLUMN_YEAR, year);
        values.put(COLUMN_RATING, rating);
        // Insert the row into the TABLE_SONG
        long result = db.insert(TABLE_MOVIE, null, values);
        // Close the database connection
        db.close();
        Log.d("SQL Insert","" + result);
        return result;
    }

    public ArrayList<Movie> getAllMovie() {
        ArrayList<Movie> movielist = new ArrayList<Movie>();
        String selectQuery = "SELECT "
                + COLUMN_TITLE + "," + COLUMN_GENRE + ","
                + COLUMN_YEAR + ","
                + COLUMN_RATING + " FROM " + TABLE_MOVIE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                String title = cursor.getString(1);
                String genre = cursor.getString(2);
                int year = cursor.getInt(3);
                int rating = cursor.getInt(4);

                Movie newMovie = new movie(title, genre, year, rating);
                movielist.add(newMovie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return movielist;
    }

}
