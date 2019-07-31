package com.lambton.lofterapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lambton.lofterapp.models.agents.AgentInfo;
import com.lambton.lofterapp.models.user.LoginUserProfile;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private final String TAG = DatabaseHelper.class.getSimpleName();

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "lofterAgents.db";

    // User table name
    private static final String TABLE_USER = "User";
    // Lofter Agents table name
    private static final String TABLE_LOFTERS = "Lofters";

    // User Table Columns names
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_FNAME = "fname";
    private static final String COLUMN_LNAME = "lname";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_CONTACT = "contact";
    private static final String COLUMN_PHOTO_PATH = "img_path";

    private static final String COLUMN_LOFTER_ID = "lofter_id";
    private static final String COLUMN_PROP_NAME = "prop_name";
    private static final String COLUMN_PROP_ADDRESS = "prop_address";
    private static final String COLUMN_PROP_CONTACT = "prop_contact";
    private static final String COLUMN_PROP_SIZE = "prop_size";
    private static final String COLUMN_PROP_TYPE = "prop_type";
    private static final String COLUMN_PROP_PRICE = "prop_price";
    private static final String COLUMN_PROP_IMG = "img_path";
    private static final String COLUMN_PROP_LATTITUDE = "lattitude";
    private static final String COLUMN_PROP_LONGITUDE = "longitude";

    /**
     * Constructor
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_FNAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_CONTACT + " TEXT,"
                + COLUMN_LNAME + " TEXT,"
                + COLUMN_PHOTO_PATH + " TEXT" + ")";

        String CREATE_LOFTER_TABLE = "CREATE TABLE " + TABLE_LOFTERS + "("
                + COLUMN_LOFTER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PROP_NAME + " TEXT,"
                + COLUMN_PROP_ADDRESS + " TEXT,"
                + COLUMN_PROP_CONTACT + " TEXT,"
                + COLUMN_PROP_SIZE + " TEXT,"
                + COLUMN_PROP_PRICE + " TEXT,"
                + COLUMN_PROP_TYPE + " TEXT,"
                + COLUMN_PROP_LATTITUDE + " REAL,"
                + COLUMN_PROP_LONGITUDE + " REAL,"
                + COLUMN_PROP_IMG + " TEXT" + ")";

        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_LOFTER_TABLE);

        Log.e(TAG, CREATE_USER_TABLE);
        Log.e(TAG, CREATE_LOFTER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop User Table if exist
        String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
        String DROP_LOFTER_TABLE = "DROP TABLE IF EXISTS " + TABLE_LOFTERS;
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_LOFTER_TABLE);
        onCreate(db);
    }

    /**
     * This method is to create user record
     *
     * @param userProfile: Add user profile into Database.
     */
    public void addUserProfile(LoginUserProfile userProfile) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_FNAME, userProfile.getFirstName());
        values.put(COLUMN_LNAME, userProfile.getLastName());
        values.put(COLUMN_PHOTO_PATH, userProfile.getImgPath());
        values.put(COLUMN_EMAIL, userProfile.getEmail());
        values.put(COLUMN_CONTACT, userProfile.getContact());

        long status = db.insert(TABLE_USER, null, values);
        Log.e("addUserProfile", "Login User " + status);
        db.close();
    }

    public void addLofterAgent(AgentInfo agentInfo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PROP_NAME, agentInfo.getPropName());
        values.put(COLUMN_PROP_ADDRESS, agentInfo.getPropAddress());
        values.put(COLUMN_PROP_IMG, agentInfo.getPropImgPath());
        values.put(COLUMN_PROP_PRICE, agentInfo.getPropPrice());
        values.put(COLUMN_PROP_CONTACT, agentInfo.getContact());
        values.put(COLUMN_PROP_SIZE, agentInfo.getPropSize());
        values.put(COLUMN_PROP_LATTITUDE, agentInfo.getLlat());
        values.put(COLUMN_PROP_LONGITUDE, agentInfo.getLlaong());
        values.put(COLUMN_PROP_TYPE, agentInfo.getPropType());

        long status = db.insert(TABLE_LOFTERS, null, values);
        Log.e("insert", "Add " + agentInfo.getPropName() + "'s property: " + status);
        db.close();
    }

   /* public String getLastRecord(String photographerName) {
        String pathOfAllImages = "";

        SQLiteDatabase db = this.getWritableDatabase();
        String[] projection = {photographerName};

        Cursor cursor = db.query(TABLE_USER
                , null
                , COLUMN_PHOTO_BY + "=?"
                , projection, null, null, null, null);

        if (cursor.moveToFirst()) {
            pathOfAllImages = cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO_PATH));
        }
        return pathOfAllImages;
    }*/

    /*public LoginUserProfile getLoginUser(String photographerName) {
        LoginUserProfile images = null;

        SQLiteDatabase db = this.getWritableDatabase();
        String[] projection = {photographerName};

        Cursor cursor = db.query(TABLE_USER
                , null
                , COLUMN_PHOTO_BY + "=?"
                , projection, null, null, null, null);

        if (cursor.moveToFirst()) {
            images = new Images();
            images.setPath(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO_PATH)));
            images.setClickedBy(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO_BY)));
        }
        return images;
    }*/

    public LoginUserProfile getLoginUser() {
        LoginUserProfile loginUserProfile = null;
        String selectQuery = "select * from " + TABLE_USER;
        SQLiteDatabase sqlDatabase = this.getWritableDatabase();
        Cursor cursor = sqlDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                loginUserProfile = new LoginUserProfile();
                loginUserProfile.set_id(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                loginUserProfile.setFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_FNAME)));
                loginUserProfile.setLastName(cursor.getString(cursor.getColumnIndex(COLUMN_LNAME)));
                loginUserProfile.setImgPath(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO_PATH)));
                loginUserProfile.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
                loginUserProfile.setContact(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT)));
            } while (cursor.moveToNext());
        }
        return loginUserProfile;
    }

    public List<AgentInfo> getAllLofterAgents() {
        List<AgentInfo> mAgentList = new ArrayList<>();

        String selectQuery = "select * from " + TABLE_LOFTERS;
        SQLiteDatabase sqlDatabase = this.getWritableDatabase();
        Cursor cursor = sqlDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                AgentInfo agentInfo = new AgentInfo();
                agentInfo.setPropName(cursor.getString(cursor.getColumnIndex(COLUMN_PROP_NAME)));
                agentInfo.setContact(cursor.getString(cursor.getColumnIndex(COLUMN_PROP_CONTACT)));
                agentInfo.setPropAddress(cursor.getString(cursor.getColumnIndex(COLUMN_PROP_ADDRESS)));
                agentInfo.setPropType(cursor.getString(cursor.getColumnIndex(COLUMN_PROP_TYPE)));
                agentInfo.setPropSize(cursor.getString(cursor.getColumnIndex(COLUMN_PROP_SIZE)));
                agentInfo.setPropPrice(cursor.getString(cursor.getColumnIndex(COLUMN_PROP_PRICE)));
                agentInfo.setLlat(cursor.getDouble(cursor.getColumnIndex(COLUMN_PROP_LATTITUDE)));
                agentInfo.setLlaong(cursor.getDouble(cursor.getColumnIndex(COLUMN_PROP_LONGITUDE)));
                agentInfo.setPropImgPath(cursor.getString(cursor.getColumnIndex(COLUMN_PROP_IMG)));
                mAgentList.add(agentInfo);
            } while (cursor.moveToNext());
        }
        return mAgentList;
    }

    /*public List<AgentInfo> getNearByAgents() {
        List<AgentInfo> mAgentList = new ArrayList<>();

        String selectQuery = "select * from " + TABLE_LOFTERS;
        SQLiteDatabase sqlDatabase = this.getWritableDatabase();
        Cursor cursor = sqlDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                AgentInfo agentInfo = new AgentInfo();
                agentInfo.setStrName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                agentInfo.setContact(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT)));
                agentInfo.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
                agentInfo.setLlat(cursor.getDouble(cursor.getColumnIndex(COLUMN_LATTITUDE)));
                agentInfo.setLlaong(cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE)));
                int isOnline = cursor.getInt(cursor.getColumnIndex(COLUMN_ONLINE));
                agentInfo.setOnline(isOnline == 1);
                agentInfo.setImgPath(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO_PATH)));
                mAgentList.add(agentInfo);
            } while (cursor.moveToNext());
        }
        return mAgentList;
    }
*/
    public boolean update(LoginUserProfile userProfile) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_FNAME, userProfile.getFirstName());
        values.put(COLUMN_LNAME, userProfile.getLastName());
        values.put(COLUMN_PHOTO_PATH, userProfile.getImgPath());
        values.put(COLUMN_EMAIL, userProfile.getEmail());
        values.put(COLUMN_CONTACT, userProfile.getContact());

        String where = COLUMN_ID +" = ?";

        String[] whereArgs = { Integer.toString(userProfile.get_id()) };

        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateSuccessful = db.update(TABLE_USER, values, where, whereArgs) > 0;
        db.close();

        return updateSuccessful;

    }

    public int updateLoginProfile(LoginUserProfile userProfile) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PHOTO_PATH, userProfile.getImgPath());
        values.put(COLUMN_FNAME, userProfile.getFirstName());
        values.put(COLUMN_LNAME, userProfile.getLastName());
        values.put(COLUMN_EMAIL, userProfile.getEmail());
        values.put(COLUMN_CONTACT, userProfile.getContact());

        // updating row
        int status = db.update(TABLE_USER, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(userProfile.get_id())});
        db.close();
        return status;
    }
}
