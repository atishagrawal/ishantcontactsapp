package com.example.DButils;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.DButils.ContactDetailsContractor.EmailDetails;
import com.example.DButils.ContactDetailsContractor.PhoneDetails;

public class EmailCRUD {
	private ContactDetailsDatabaseHelper contact_databasehelper = null;

	public EmailCRUD(Context context) {
		contact_databasehelper = new ContactDetailsDatabaseHelper(context);

	}
	public void insertData(ContentValues contentValues){
		SQLiteDatabase database=contact_databasehelper.getWritableDatabase();
		database.insert(EmailDetails.TABLE_NAME, null, contentValues);
	}

	public Cursor readData() {
		if (contact_databasehelper != null) {
			SQLiteDatabase database = contact_databasehelper
					.getReadableDatabase();
			return database.query(EmailDetails.TABLE_NAME, null, null, null,
					null, null, null);
		}
		return null;
	}
	
public void updateData(int id ,ContentValues contentValues){
	
}	

public void DeleteData(int id){
	
}
public Cursor getSpecificEmailData(int userId) {
	if (contact_databasehelper != null) {
		SQLiteDatabase database = contact_databasehelper
				.getReadableDatabase();
		final String My_QUERY = "SELECT * FROM "
				+ EmailDetails.TABLE_NAME 
				+ " where "
				+ EmailDetails.COLUMN_CONTACT_ID + " = " + userId;
		return database.rawQuery(My_QUERY, null);
	}
	return null;
}


}
