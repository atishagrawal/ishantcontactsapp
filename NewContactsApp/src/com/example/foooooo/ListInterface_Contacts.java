package com.example.foooooo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.DButils.ContactCRUD;
import com.example.DButils.ContactDetailsContractor.ContactDetails;

public class ListInterface_Contacts extends Activity {
	SimpleCursorAdapter adapter;
	private String Contact_Id = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contactlayerlist);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		ContactCRUD crud = new ContactCRUD(ListInterface_Contacts.this);
		Cursor cursor = null;
		cursor = crud.readData();
		String[] from = new String[] { ContactDetails._ID,
				ContactDetails.COLUMN_NAME };
		int[] to = new int[] { R.id.tv_contact_id, R.id.tv_contactlist };
		adapter = new SimpleCursorAdapter(this, R.layout.innerlistview, cursor,
				from, to, 0);
		ListView list1 = (ListView) findViewById(R.id.listViewUserData);
		list1.setAdapter(adapter);
		list1.setOnItemClickListener(ClickListener);
	}

	private OnItemClickListener ClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			TextView textViewContactId = (TextView) view
					.findViewById(R.id.tv_contact_id);
			Contact_Id = textViewContactId.getText().toString();
			Intent intent = new Intent(ListInterface_Contacts.this,
					ViewContact.class);
			intent.putExtra(ContactDetails._ID, Contact_Id);
			startActivity(intent);

			Log.d("ListView Activity", " Selected Contact" + Contact_Id);

		}
	};
}
