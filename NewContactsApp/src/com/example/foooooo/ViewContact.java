package com.example.foooooo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.DButils.AddressCRUD;
import com.example.DButils.ContactCRUD;
import com.example.DButils.ContactDetailsContractor.AddressDetails;
import com.example.DButils.ContactDetailsContractor.ContactDetails;
import com.example.DButils.ContactDetailsContractor.EmailDetails;
import com.example.DButils.ContactDetailsContractor.PhoneDetails;
import com.example.DButils.EmailCRUD;
import com.example.DButils.PhoneCRUD;

public class ViewContact extends Activity {
	LinearLayout layoutphone,layoutemail,layoutaddress;
	ArrayList<View> addedViewphone = new ArrayList<View>();
	ArrayList<View> addedViewemail = new ArrayList<View>();
	ArrayList<View> addedViewaddress = new ArrayList<View>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewcontactactivty);
		layoutphone = (LinearLayout) findViewById(R.id.linearlayout_contact_view_phonedynamics);
		layoutemail=(LinearLayout)findViewById(R.id.linearlayout_contact_view_emaildynamics);
        layoutaddress=(LinearLayout)findViewById(R.id.linearlayout_contact_view_addressdynamics);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Intent intent = getIntent();

		String id = intent.getStringExtra(ContactDetails._ID);

		ContactCRUD crudcontact = new ContactCRUD(ViewContact.this);
		Cursor cursorcontact = null;
		cursorcontact = crudcontact.getSpecificContactData(Integer.valueOf(id));

		if (cursorcontact != null && cursorcontact.getCount() > 0) {
			if (cursorcontact.moveToFirst()) {
				do {
					((TextView) findViewById(R.id.tvContactName))
							.setText(cursorcontact.getString(cursorcontact
									.getColumnIndex(ContactDetails.COLUMN_NAME)));
					((TextView) findViewById(R.id.organisation))
							.setText(cursorcontact.getString(cursorcontact
									.getColumnIndex(ContactDetails.COLUMN_COMPANY)));
					((TextView) findViewById(R.id.title))
							.setText(cursorcontact.getString(cursorcontact
									.getColumnIndex(ContactDetails.COLUMN_TITLE)));
					((TextView) findViewById(R.id.IM)).setText(cursorcontact
							.getString(cursorcontact
									.getColumnIndex(ContactDetails.COLUMN_IM)));
					((TextView) findViewById(R.id.nickname))
							.setText(cursorcontact.getString(cursorcontact
									.getColumnIndex(ContactDetails.COLUMN_NICK_NAME)));
					((TextView) findViewById(R.id.website))
							.setText(cursorcontact.getString(cursorcontact
									.getColumnIndex(ContactDetails.COLUMN_WEBSITE)));
					((TextView) findViewById(R.id.tvsip))
							.setText(cursorcontact.getString(cursorcontact
									.getColumnIndex(ContactDetails.COLUMN_SIP)));
					((TextView) findViewById(R.id.Note))
							.setText(cursorcontact.getString(cursorcontact
									.getColumnIndex(ContactDetails.COLUMN_NOTES)));

				} while (cursorcontact.moveToNext());
			} else {
				Toast.makeText(ViewContact.this, "No records found",
						Toast.LENGTH_SHORT).show();
				return;
			}
		} else {
			Toast.makeText(ViewContact.this, "No records found",
					Toast.LENGTH_SHORT).show();
			return;
		}

		PhoneCRUD crudphone = new PhoneCRUD(ViewContact.this);
		Cursor cursorphone = null;
		cursorphone = crudphone.getSpecificPhoneData(Integer.valueOf(id));
		if (cursorphone != null && cursorphone.getCount() > 0) {
			if (cursorphone.moveToFirst()) {
				((TextView) findViewById(R.id.tvphone1))
						.setText(cursorphone.getString(cursorphone
								.getColumnIndex(PhoneDetails.COLUMN_PHONE_NUM)));
				do {

					LayoutInflater inflater = getLayoutInflater();

					View dialogview = inflater.inflate(
							R.layout.view_phone_dynamic, null);
					layoutphone.addView(dialogview);
					addedViewphone.add(dialogview);
					((TextView) findViewById(R.id.tvView_phone_dynamic))
							.setText(cursorphone.getString(cursorphone
									.getColumnIndex(PhoneDetails.COLUMN_PHONE_NUM)));

				} while (cursorphone.moveToNext());
			} else {
				Toast.makeText(ViewContact.this, "No records found",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(ViewContact.this, "No records found",
					Toast.LENGTH_SHORT).show();

		}

		EmailCRUD crudemail = new EmailCRUD(ViewContact.this);
		Cursor cursoremail = null;
		cursoremail = crudemail.getSpecificEmailData(Integer.valueOf(id));
		if (cursoremail != null && cursoremail.getCount() > 0) {
			if (cursoremail.moveToFirst()) {
				((TextView) findViewById(R.id.tvEmail))
				.setText(cursoremail.getString(cursoremail
						.getColumnIndex(EmailDetails.COLUMN_EMAIL_ADDRESS)));
		do {

			LayoutInflater inflater = getLayoutInflater();

			View dialogview = inflater.inflate(
					R.layout.view_email_dynamic, null);
			layoutemail.addView(dialogview);
			addedViewemail.add(dialogview);
			((TextView) findViewById(R.id.tvView_email_dynamic))
					.setText(cursoremail.getString(cursoremail
							.getColumnIndex(EmailDetails.COLUMN_EMAIL_ADDRESS)));

		} while (cursoremail.moveToNext());			} else {
				Toast.makeText(ViewContact.this, "No records found",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(ViewContact.this, "No records found",
					Toast.LENGTH_SHORT).show();

		}

		AddressCRUD crudaddress = new AddressCRUD(ViewContact.this);
		Cursor cursoraddress = null;
		cursoraddress = crudaddress.getSpecificAddressData(Integer.valueOf(id));
		if (cursoraddress != null && cursoraddress.getCount() > 0) {
			if (cursoraddress.moveToFirst()) {
				((TextView) findViewById(R.id.tvAddress))
				.setText(cursoraddress.getString(cursoraddress
						.getColumnIndex(AddressDetails.COLUMN_ADDRESS)));
		do {

			LayoutInflater inflater = getLayoutInflater();

			View dialogview = inflater.inflate(
					R.layout.view_address_dynamic, null);
			layoutaddress.addView(dialogview);
			addedViewaddress.add(dialogview);
			((TextView) findViewById(R.id.tvView_address_dynamic))
					.setText(cursoraddress.getString(cursoraddress
							.getColumnIndex(AddressDetails.COLUMN_ADDRESS)));

		} while (cursoraddress.moveToNext());			} else {
				Toast.makeText(ViewContact.this, "No records found",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(ViewContact.this, "No records found",
					Toast.LENGTH_SHORT).show();

		}
	}

}
