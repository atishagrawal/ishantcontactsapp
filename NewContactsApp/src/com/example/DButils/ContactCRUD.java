package com.example.DButils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.DButils.ContactDetailsContractor.ContactDetails;

public class ContactCRUD {

	private ContactDetailsDatabaseHelper contact_databasehelper = null;

	public ContactCRUD(Context context) {
		contact_databasehelper = new ContactDetailsDatabaseHelper(context);

	}

	public long insertData(ContentValues contentValues) {
		if (contact_databasehelper != null) {
			SQLiteDatabase database = contact_databasehelper
					.getWritableDatabase();
			return database.insert(ContactDetails.TABLE_NAME, null,
					contentValues);
		}

		return 0;

	}

	public Cursor readData() {
		if (contact_databasehelper != null) {
			SQLiteDatabase database = contact_databasehelper
					.getReadableDatabase();
			return database.query(ContactDetails.TABLE_NAME, null, null, null,
					null, null, null);
		}
		return null;
	}

	public void updateData(int id, ContentValues contentValues) {

	}

	public void DeleteData(int id) {

	}

	public Cursor getSpecificContactData(int userId) {
		if (contact_databasehelper != null) {
			SQLiteDatabase database = contact_databasehelper
					.getReadableDatabase();
			final String My_QUERY = "SELECT * FROM "
					+ ContactDetails.TABLE_NAME + " where "
					+ ContactDetails._ID + " = " + userId;
			return database.rawQuery(My_QUERY, null);
		}
		return null;
	}
}
