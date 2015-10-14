package com.example.DButils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.DButils.ContactDetailsContractor.AddressDetails;
import com.example.DButils.ContactDetailsContractor.ContactDetails;
import com.example.DButils.ContactDetailsContractor.EmailDetails;
import com.example.DButils.ContactDetailsContractor.PhoneDetails;

public class ContactDetailsDatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "ContactsApp.db";
	private static final int DATABASE_VERSION = 1;

	public ContactDetailsDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	private static final String SQL_CREATE_CONTACT_DETAILS = "CREATE TABLE "
			+ ContactDetails.TABLE_NAME + " ( " + ContactDetails._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT " + " , "
			+ ContactDetails.COLUMN_NAME + " TEXT " + " , "
			+ ContactDetails.COLUMN_COMPANY + " TEXT " + " , "
			+ ContactDetails.COLUMN_TITLE + " TEXT " + " , "
			+ ContactDetails.COLUMN_IM + " TEXT " + " , "
			+ ContactDetails.COLUMN_NOTES + " TEXT " + " , "
			+ ContactDetails.COLUMN_NICK_NAME + " TEXT " + " , "
			+ ContactDetails.COLUMN_WEBSITE + " TEXT " + " , "
			+ ContactDetails.COLUMN_SIP + " TEXT " + " )";

	private static final String SQL_CREATE_PHONE_DETAILS = "CREATE TABLE "
			+ PhoneDetails.TABLE_NAME + " ( " + PhoneDetails._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT , "
			+ PhoneDetails.COLUMN_CONTACT_ID + " INTEGER  " + " , "
			+ PhoneDetails.COLUMN_PHONE_NUM + " INTEGER " + " , "
			+ " FOREIGN KEY ( " + PhoneDetails.COLUMN_CONTACT_ID
			+ " ) REFERENCES " + ContactDetails.TABLE_NAME + " ( "
			+ ContactDetails._ID + " ) " + " )";

	private static final String SQL_CREATE_EMAIL_DETAILS = "CREATE TABLE "
			+ EmailDetails.TABLE_NAME + " ( " + EmailDetails._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT , "
			+ EmailDetails.COLUMN_CONTACT_ID + " INTEGER " + " , "
			+ EmailDetails.COLUMN_EMAIL_ADDRESS + " TEXT " + " , "
			+ " FOREIGN KEY ( " + EmailDetails.COLUMN_CONTACT_ID
			+ " ) REFERENCES " + ContactDetails.TABLE_NAME + " ( "
			+ ContactDetails._ID + " ) " + " )";

	private static final String SQL_CREATE_ADDRESS_DETAILS = "CREATE TABLE "
			+ AddressDetails.TABLE_NAME + " ( " + AddressDetails._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT , "
			+ AddressDetails.COLUMN_CONTACT_ID + " INTEGER " + " , "
			+ AddressDetails.COLUMN_ADDRESS + " TEXT " + " , "
			+ " FOREIGN KEY ( " + AddressDetails.COLUMN_CONTACT_ID
			+ " ) REFERENCES " + ContactDetails.TABLE_NAME + " ( "
			+ ContactDetails._ID + " ) " + " )";

	public static final String SQL_DELETE_CONTACT_DETAILS = "DROP TABLE IF EXISTS "
			+ ContactDetails.TABLE_NAME;
	public static final String SQL_DELETE_PHONE_DETAILS = "DROP TABLE IF EXISTS "
			+ PhoneDetails.TABLE_NAME;
	public static final String SQL_DELETE_EMAIL_DETAILS = "DROP TABLE IF EXISTS "
			+ EmailDetails.TABLE_NAME;
	public static final String SQL_DELETE_ADDRESS_DETAILS = "DROP TABLE IF EXISTS "
			+ AddressDetails.TABLE_NAME;

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_CONTACT_DETAILS);
		db.execSQL(SQL_CREATE_PHONE_DETAILS);
		db.execSQL(SQL_CREATE_EMAIL_DETAILS);
		db.execSQL(SQL_CREATE_ADDRESS_DETAILS);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL(SQL_DELETE_CONTACT_DETAILS);
		db.execSQL(SQL_DELETE_PHONE_DETAILS);
		db.execSQL(SQL_DELETE_EMAIL_DETAILS);
		db.execSQL(SQL_DELETE_ADDRESS_DETAILS);
		onCreate(db);
	}

}
