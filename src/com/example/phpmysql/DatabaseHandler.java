package com.example.phpmysql;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 2;
	// Database Name
	private static final String DATABASE_NAME = "phpmysql";
	// Contacts table name
	private static final String TABLE_COMMENT = "comment";
	// column
	private static final String KEY_ID = "comment_id";
	private static final String KEY_NAME = "first_name";
	private static final String KEY_DISTRICT = "district_id";
	private static final String KEY_DISTRICT_NAME = "district_name";
	private static final String KEY_VDC = "vdc_id";
	private static final String KEY_VDC_NAME = "vdc_name";
	private static final String KEY_TOLE = "tole";
	private static final String KEY_WARD = "ward_no";
	private static final String KEY_COMMENT = "comment";
	private static final String USER_ID = "user_id";
	public Context mcontext;

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		mcontext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_COMMENT_TABLE = "CREATE TABLE " + TABLE_COMMENT + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
				+ KEY_DISTRICT + " INTEGER, " + KEY_VDC + " INTEGER, "
				+ KEY_TOLE + " TEXT, " + KEY_WARD + " INTEGER, " + KEY_COMMENT
				+ " TEXT, " + USER_ID + " INTEGER)";
		Log.d("table created", CREATE_COMMENT_TABLE);
		db.execSQL(CREATE_COMMENT_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENT);
		onCreate(db);

	}

	public void addComment(FormData formdata) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, formdata.getName());
		values.put(KEY_DISTRICT, formdata.getDistrict_id());
		values.put(KEY_VDC, formdata.getVdcid());
		values.put(KEY_TOLE, formdata.getTole());
		values.put(KEY_WARD, formdata.getWardno());
		values.put(KEY_COMMENT, formdata.getComment());
		values.put(USER_ID, formdata.getUserid());
		db.insert(TABLE_COMMENT, null, values);
		//db.close();
	}

	public List<FormData> getAllComments() {
		List<FormData> commentList = new ArrayList<FormData>();
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT  A.first_name,A.tole,A.comment,A.ward_no,A.user_id,A.comment_id,B.district_name,C.vdc_name FROM  comment  AS A,district B,vdclist C WHERE A.district_id = B.district_id AND A.vdc_id = C.vdc_id AND B.district_id = c.district_id";
		System.out.println(selectQuery);
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				FormData frmdata = new FormData();
				frmdata.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
				frmdata.setDistrict_name(cursor.getString(cursor.getColumnIndex(KEY_DISTRICT_NAME)));
				frmdata.setVdc_name(cursor.getString(cursor.getColumnIndex(KEY_VDC_NAME)));
				frmdata.setTole(cursor.getString(cursor.getColumnIndex(KEY_TOLE)));				
				frmdata.setWardno(cursor.getInt(cursor.getColumnIndex(KEY_WARD)));
				frmdata.setComment(cursor.getString(cursor.getColumnIndex(KEY_COMMENT)));
				frmdata.setUserid(cursor.getInt(cursor.getColumnIndex(USER_ID)));
				frmdata.setComment_id(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
				
				// Adding contact to list
				commentList.add(frmdata);
			} while (cursor.moveToNext());
		}
		return commentList;
	}
	public void updateComment(FormData formdata,int updatid){
		System.out.println(formdata);
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, formdata.getName());
		values.put(KEY_DISTRICT, formdata.getDistrict_id());
		values.put(KEY_VDC, formdata.getVdcid());
		values.put(KEY_TOLE, formdata.getTole());
		values.put(KEY_WARD, formdata.getWardno());
		values.put(KEY_COMMENT, formdata.getComment());
		db.update(TABLE_COMMENT, values, KEY_ID + " = ?",new String[] { String.valueOf(updatid) });
	}
	public int deleteData(int comment_id){
		SQLiteDatabase db = this.getWritableDatabase();
		return  db.delete(TABLE_COMMENT,KEY_ID + " = ?",new String[] { String.valueOf(comment_id)});
		
	}
}
