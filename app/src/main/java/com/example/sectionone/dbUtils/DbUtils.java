package com.example.sectionone.dbUtils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sectionone.models.EventItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DbUtils extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "database";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_EVENTS = "events";

    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_VIEWCOUNTS = "view_counts";
    private static final String COLUMN_ISREAD = "is_read";
    private static final String COLUMN_IMAGE1 = "image1";
    private static final String COLUMN_IMAGE2 = "image2";
    private static final String COLUMN_IMAGE3 = "image3";

    private Context context;

    public DbUtils(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_EVENTS + "(" +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_VIEWCOUNTS + " INT, " +
                COLUMN_ISREAD + " INT, " +
                COLUMN_IMAGE1 + " TEXT, " +
                COLUMN_IMAGE2 + " TEXT, " +
                COLUMN_IMAGE3 + " TEXT )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);

        onCreate(db);
    }

    public List<EventItem> getEvents() throws JSONException {
        List<EventItem> eventItemList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_EVENTS, null);
        while(cursor.moveToNext()){
            int titleIndex = cursor.getColumnIndex(COLUMN_TITLE);
            int descriptionIndex = cursor.getColumnIndex(COLUMN_DESCRIPTION);
            int viewCountIndex = cursor.getColumnIndex(COLUMN_VIEWCOUNTS);
            int isReadIndex = cursor.getColumnIndex(COLUMN_ISREAD);
            int image1Index = cursor.getColumnIndex(COLUMN_IMAGE1);
            int image2Index = cursor.getColumnIndex(COLUMN_IMAGE2);
            int image3Index = cursor.getColumnIndex(COLUMN_IMAGE3);

            List<String> images = new ArrayList<>();

            images.add(cursor.getString(image1Index));
            images.add(cursor.getString(image2Index));
            images.add(cursor.getString(image3Index));

            eventItemList.add(new EventItem(
                    cursor.getString(titleIndex),
                    cursor.getString(descriptionIndex),
                    cursor.getInt(viewCountIndex),
                    cursor.getInt(isReadIndex) == 1,
                    images
            ));
        }

        if(eventItemList.size() == 0){
            eventItemList = fetchJsonEvents();
        }

        return eventItemList;
    }

    private List<EventItem> fetchJsonEvents() throws JSONException {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        List<EventItem> eventItemList = new ArrayList<>();

        JSONObject jsonObject = loadJSONFromAsset("events.json");
        JSONArray eventsArray = jsonObject.getJSONArray("events");

        for(int i = 0; i < eventsArray.length(); i++){
            JSONObject privateJsonObject = eventsArray.getJSONObject(i);
            String title = privateJsonObject.getString("title");
            String description = privateJsonObject.getString("description");
            boolean isRead = privateJsonObject.getBoolean("isRead");
            JSONArray imagesArr = privateJsonObject.getJSONArray("images");
            int viewCounts  = privateJsonObject.getInt("viewCounts");

            List<String> images = new ArrayList<>();

            for(int j = 0; j < imagesArr.length(); j++){
                images.add(imagesArr.getString(j));
            }

            eventItemList.add(new EventItem(
                    title,
                    description,
                    viewCounts,
                    isRead,
                    images
            ));

            ContentValues contentValues = new ContentValues();

            contentValues.put(COLUMN_TITLE, title);
            contentValues.put(COLUMN_DESCRIPTION, description);
            contentValues.put(COLUMN_VIEWCOUNTS, viewCounts);
            contentValues.put(COLUMN_ISREAD, !isRead ? 0 : 1);
            contentValues.put(COLUMN_IMAGE1, images.get(0));
            contentValues.put(COLUMN_IMAGE2, images.get(1));
            contentValues.put(COLUMN_IMAGE3, images.get(2));

            sqLiteDatabase.insert(TABLE_EVENTS, null,contentValues);

        }
        return eventItemList;
    }


    private JSONObject loadJSONFromAsset(String asset){
        JSONObject jsonObject = null;
        try{
            InputStream inputStream = context.getAssets().open(asset);
            int size = inputStream.available();
            byte[] bytes = new byte[size];

            inputStream.read(bytes);
            inputStream.close();

            String json = new String(bytes, StandardCharsets.UTF_8);

            jsonObject = new JSONObject(json);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}