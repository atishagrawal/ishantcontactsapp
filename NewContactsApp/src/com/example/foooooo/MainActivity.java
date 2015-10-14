package com.example.foooooo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

public class MainActivity extends Activity {
	boolean flag = true;
	static int i = 1;
	static String name = null, p = null;
	Spinner spinnerPhone, spinnerPhonedynamic;
	String arraySpinner[] = { "Mobile", "Home", "Work", "Work Fax", "HomeFax",
			"Pager", "Other", "Custom", "CallBack", "Car", "Company Main",
			"ISDN", "Main", "OtherFax", "Radio" };
	ArrayList<Integer> arrayListIdsphone = new ArrayList<Integer>();
	ArrayList<Integer> arrayListIdsemail = new ArrayList<Integer>();
	ArrayList<Integer> arrayListIdsaddress = new ArrayList<Integer>();

	int id_phone, id_email, id_address;

	// boolean val = true;
	EditText edtxtfirstName, edtxtnameprefix, edtxtmiddlename, edtxtlastname,
			edtxtnamesuffix, edtxtnamemain, edphone, edemail, edaddress,
			edphoneticName, edtxtcompany, edtxttitle, edemaildynamic,
			edaddressdynamic, edIM, edSIP, edNickname, edNotes, edWebsite;
	Button bupArrow, bdownArrow;
	ArrayList<View> addedView1 = new ArrayList<View>();
	ArrayList<View> addedView2 = new ArrayList<View>();
	ArrayList<View> addedView3 = new ArrayList<View>();

	LinearLayout layout1, layout2, layout3;
	private int id_spinner_phone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initialize();
		layout1 = (LinearLayout) findViewById(R.id.linearlayoutphone);
		layout2 = (LinearLayout) findViewById(R.id.linearlayoutemail);
		layout3 = (LinearLayout) findViewById(R.id.linearlayoutaddress);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		ArrayAdapter<String> spinneradapterphone = new ArrayAdapter<String>(
				MainActivity.this, android.R.layout.simple_spinner_item,
				arraySpinner);

		spinneradapterphone
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerPhone.setAdapter(spinneradapterphone);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		super.onOptionsItemSelected(item);

		int theId = item.getItemId();
		if (theId == android.R.id.home) {
			saveMethod(null);
		}

		return true;
	}

	public void saveMethod(View view) {

		// Checking for mandatory fields to be filled

		if (TextUtils.isEmpty(edtxtnamemain.getText().toString())) {
			Toast.makeText(MainActivity.this, "Please enter your name",
					Toast.LENGTH_SHORT).show();
			return;
		}
		/*
		 * if (TextUtils.isEmpty(edphone.getText().toString())) {
		 * Toast.makeText(MainActivity.this, "Please enter your Phone number",
		 * Toast.LENGTH_SHORT).show(); return; }
		 * 
		 * if (TextUtils.isEmpty(edemail.getText().toString())) {
		 * 
		 * Toast.makeText(MainActivity.this, "Please enter your Email Address",
		 * Toast.LENGTH_SHORT) .show(); return; }
		 * 
		 * if (TextUtils.isEmpty(edaddress.getText().toString())) {
		 * Toast.makeText(MainActivity.this, "Please enter your Address",
		 * Toast.LENGTH_SHORT).show(); return; }
		 */
		// All mandatory data is already filled

		// Preparing content values to save contact_details

		ContentValues contentValuesContactDetails = new ContentValues();

		contentValuesContactDetails.put(ContactDetails.COLUMN_NAME,
				edtxtnamemain.getText().toString());
		contentValuesContactDetails.put(ContactDetails.COLUMN_COMPANY,
				edtxtcompany.getText().toString());
		contentValuesContactDetails.put(ContactDetails.COLUMN_TITLE, edtxttitle
				.getText().toString());
		contentValuesContactDetails.put(ContactDetails.COLUMN_IM, edIM
				.getText().toString());
		contentValuesContactDetails.put(ContactDetails.COLUMN_NICK_NAME,
				edNickname.getText().toString());
		contentValuesContactDetails.put(ContactDetails.COLUMN_NOTES, edNotes
				.getText().toString());
		contentValuesContactDetails.put(ContactDetails.COLUMN_WEBSITE,
				edWebsite.getText().toString());
		contentValuesContactDetails.put(ContactDetails.COLUMN_SIP, edSIP
				.getText().toString());

		ContactCRUD crud = new ContactCRUD(MainActivity.this);
		long inserted_contact_id = crud.insertData(contentValuesContactDetails);

		if (inserted_contact_id == 0) {

			// Data insertion failed

			Toast.makeText(MainActivity.this, "Something went wrong",
					Toast.LENGTH_LONG).show();

			return;

		}

		/**
		 * Inserting phone number in the phone details table using the obtained
		 * contact id
		 */

		/*
		 * Inserting the mandatory phone number
		 */
		ContentValues contentValuesPhoneDetails = new ContentValues();

		contentValuesPhoneDetails.put(PhoneDetails.COLUMN_CONTACT_ID,
				inserted_contact_id);
		contentValuesPhoneDetails.put(PhoneDetails.COLUMN_PHONE_NUM, edphone
				.getText().toString());
		PhoneCRUD crudphone = new PhoneCRUD(MainActivity.this);
		crudphone.insertData(contentValuesPhoneDetails);

		// Inserting additional phone numbers if present

		for (int id_phone : arrayListIdsphone) {

			EditText editTextPhoneNumber = (EditText) findViewById(id_phone);

			ContentValues contentValuesPhoneDetailsAdditional = new ContentValues();
			contentValuesPhoneDetailsAdditional.put(
					PhoneDetails.COLUMN_CONTACT_ID, inserted_contact_id);
			contentValuesPhoneDetailsAdditional.put(
					PhoneDetails.COLUMN_PHONE_NUM, editTextPhoneNumber
							.getText().toString());

			PhoneCRUD crudphone2 = new PhoneCRUD(MainActivity.this);
			crudphone2.insertData(contentValuesPhoneDetailsAdditional);

		}

		/**
		 * Phone number inserted in the phone details table. Now inserting email
		 * id accordingly
		 * 
		 */

		/*
		 * Inserting the mandatory Email Address
		 */

		ContentValues contentValuesEmailDetails = new ContentValues();
		contentValuesEmailDetails.put(EmailDetails.COLUMN_CONTACT_ID,
				inserted_contact_id);
		contentValuesEmailDetails.put(EmailDetails.COLUMN_EMAIL_ADDRESS,
				edemail.getText().toString());

		EmailCRUD crudemail = new EmailCRUD(MainActivity.this);
		crudemail.insertData(contentValuesEmailDetails);

		// Inserting additional email addresses if present

		for (int id_email : arrayListIdsemail) {

			EditText editTextEmailAddress = (EditText) findViewById(id_email);
			ContentValues contentValuesEmailDetailsAdditional = new ContentValues();
			contentValuesEmailDetailsAdditional.put(
					EmailDetails.COLUMN_CONTACT_ID, inserted_contact_id);

			contentValuesEmailDetailsAdditional.put(
					EmailDetails.COLUMN_EMAIL_ADDRESS, editTextEmailAddress
							.getText().toString());
			EmailCRUD crudemail1 = new EmailCRUD(MainActivity.this);
			crudemail1.insertData(contentValuesEmailDetailsAdditional);

		}

		// Email Addresses inserted successfully. Now inserting the addresses in
		// the corresponding table

		ContentValues contentValuesAddressDetails = new ContentValues();
		contentValuesAddressDetails.put(AddressDetails.COLUMN_CONTACT_ID,
				inserted_contact_id);
		contentValuesAddressDetails.put(AddressDetails.COLUMN_ADDRESS,
				edaddress.getText().toString());

		AddressCRUD crudaddress = new AddressCRUD(MainActivity.this);
		crudaddress.insertData(contentValuesAddressDetails);

		for (int id_address : arrayListIdsaddress) {
			EditText editTextAddress = (EditText) findViewById(id_address);

			ContentValues contentValuesAddressDetailsAdditional = new ContentValues();
			contentValuesAddressDetailsAdditional.put(
					AddressDetails.COLUMN_CONTACT_ID, inserted_contact_id);

			contentValuesAddressDetailsAdditional.put(
					AddressDetails.COLUMN_ADDRESS, editTextAddress.getText()
							.toString());

			AddressCRUD crudaddress1 = new AddressCRUD(MainActivity.this);
			crudaddress1.insertData(contentValuesAddressDetailsAdditional);

		}

		/**
		 * All data inserted successfully. Now showing the listview of all the
		 * contacts
		 */

		startActivity(new Intent(MainActivity.this,
				ListInterface_Contacts.class));
	}

	public void updatemethodphone(View view) {

		LayoutInflater inflater = getLayoutInflater();

		View dialogview = inflater.inflate(R.layout.phonedynamics, null);
		layout1.addView(dialogview);
		addedView1.add(dialogview);
		EditText edphonedynamics = (EditText) dialogview
				.findViewById(R.id.edphonedynamic);
		spinnerPhonedynamic = (Spinner) dialogview
				.findViewById(R.id.spinner_phone_dynamic);
		id_spinner_phone = spinnerPhonedynamic.getId();
		id_phone = edphonedynamics.getId();
		arrayListIdsphone.add(id_phone);
		ArrayAdapter<String> spinneradapterphone1 = new ArrayAdapter<String>(
				MainActivity.this, android.R.layout.simple_spinner_item,
				arraySpinner);

		spinneradapterphone1
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinnerPhonedynamic.setAdapter(spinneradapterphone1);
		spinnerPhonedynamic.setSelection(i++);

	}

	/*
	 * Button btnremove=new Button(this); btnremove.setText("+");
	 * btnremove.setGravity(Gravity.RIGHT); LinearLayout ll =
	 * (LinearLayout)findViewById(R.id.phonedynmo); LayoutParams lp = new
	 * LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	 * ll.addView(btnremove, lp); btnremove.setOnClickListener(btnclick);
	 */

	/*
	 * OnClickListener btnclick=new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) {
	 * 
	 * View myview=findViewById(R.id.phonedynmo); ViewGroup parent
	 * =(ViewGroup)myview.getParent(); parent.removeView(myview); } };
	 */
	public void updatemethodemail(View view) {

		LayoutInflater inflater = getLayoutInflater();
		View dialogview = inflater.inflate(R.layout.emaildynamo, null);
		layout2.addView(dialogview);
		addedView2.add(dialogview);
		EditText edemaildynamics = (EditText) dialogview
				.findViewById(R.id.edemaildynamic);
		id_email = edemaildynamics.getId();
		arrayListIdsemail.add(id_email);

	}

	public void updatemethodaddress(View view) {

		LayoutInflater inflater = getLayoutInflater();
		View dialogview = inflater.inflate(R.layout.addressdynamo, null);
		layout3.addView(dialogview);

		addedView3.add(dialogview);
		EditText edaddressdynamics = (EditText) dialogview
				.findViewById(R.id.edaddressdynamic);
		id_address = edaddressdynamics.getId();
		arrayListIdsaddress.add(id_address);

	}

	public void removephone(View view) {

		View myview = findViewById(R.id.phonedynmo);
		ViewGroup parent = (ViewGroup) myview.getParent();
		parent.removeView(myview);

	}

	public void removeemail(View view) {

		View myview = findViewById(R.id.emaildynamo);
		ViewGroup parent = (ViewGroup) myview.getParent();
		parent.removeView(myview);

	}

	public void removeaddress(View view) {

		View myview = findViewById(R.id.addressdynamo);
		ViewGroup parent = (ViewGroup) myview.getParent();
		parent.removeView(myview);

	}

	public void showmethod(View view) {

		bupArrow = (Button) findViewById(R.id.bicon_up_arrow);
		bdownArrow = (Button) findViewById(R.id.bicon_down_arrow);

		bdownArrow.setVisibility(view.GONE);

		bupArrow.setVisibility(view.VISIBLE);
		List<String> lex = new ArrayList<String>();
		String str = edtxtnamemain.getText().toString();
		String delimiter = " ";
		String[] tokens = str.split(delimiter);
		int counttokens = tokens.length;

		for (int i = 0; i < counttokens; i++) {

			lex.add(tokens[i]);

		}

		if (counttokens == 1) {
			edtxtfirstName.setText(lex.get(0));
		} else if (counttokens == 2) {
			edtxtfirstName.setText(lex.get(0));
			edtxtmiddlename.setText(lex.get(1));
		} else {
			name = lex.get(0);
			for (int i = 1; i < counttokens - 2; i++) {
				name = name + " " + lex.get(i);
			}
			edtxtfirstName.setText(name);
			edtxtmiddlename.setText(lex.get(counttokens - 2));
			edtxtlastname.setText(lex.get(counttokens - 1));
		}
		edtxtnamemain.setVisibility(view.GONE);
		edtxtnameprefix.setVisibility(view.VISIBLE);
		int id = edtxtfirstName.getId();

		for (int i = 0; i < 4; i++) {
			EditText edtxt1 = (EditText) findViewById((id));
			edtxt1.setVisibility(view.VISIBLE);
			id++;
		}
	}

	public void hidemethod(View view) {
		bupArrow = (Button) findViewById(R.id.bicon_up_arrow);
		bdownArrow = (Button) findViewById(R.id.bicon_down_arrow);

		bdownArrow.setVisibility(view.VISIBLE);

		bupArrow.setVisibility(view.GONE);

		EditText edtxtNamePrefix = (EditText) findViewById(R.id.edtxtfirstname);
		int id = edtxtNamePrefix.getId();
		p = (edtxtnameprefix.getText().toString() + " "
				+ edtxtfirstName.getText().toString() + " "
				+ edtxtmiddlename.getText().toString() + " "
				+ edtxtlastname.getText().toString() + " " + edtxtnamesuffix
				.getText().toString()).trim();
		edtxtnameprefix.setVisibility(view.GONE);
		edtxtnamemain.setVisibility(view.VISIBLE);
		edtxtnamemain.setText(p);
		for (int i = 0; i < 4; i++) {
			EditText edtxt1 = (EditText) findViewById((id));
			edtxt1.setVisibility(view.GONE);
			id++;

		}

	}

	public void initialize() {
		// TODO Auto-generated method stub
		edtxtnamemain = (EditText) findViewById(R.id.edtxtNamemain);
		edphoneticName = (EditText) findViewById(R.id.edtxtPhoneticname);
		edtxtfirstName = (EditText) findViewById(R.id.edtxtfirstname);
		edtxtnameprefix = (EditText) findViewById(R.id.edtxtNamePrefix);
		edtxtlastname = (EditText) findViewById(R.id.edtxtlastname);
		edtxtmiddlename = (EditText) findViewById(R.id.edtxtmiddlename);
		edtxtnamesuffix = (EditText) findViewById(R.id.edtxtnamesuffix);
		edtxtcompany = (EditText) findViewById(R.id.edcompany);
		edtxttitle = (EditText) findViewById(R.id.edtitle);
		edphone = (EditText) findViewById(R.id.edphone);
		edphone.addTextChangedListener(TextChangeWatcherphone);
		edemail = (EditText) findViewById(R.id.edemail);
		edemail.addTextChangedListener(TextChangeWatcherEmail);
		edaddress = (EditText) findViewById(R.id.edaddress);
		edaddress.addTextChangedListener(TextChangeWatcheraddress);
		edemaildynamic = (EditText) findViewById(R.id.edemaildynamic);
		edaddressdynamic = (EditText) findViewById(R.id.edaddressdynamic);
		edIM = (EditText) findViewById(R.id.edIM);
		edNickname = (EditText) findViewById(R.id.edNickname);
		edNotes = (EditText) findViewById(R.id.edNotes);
		edWebsite = (EditText) findViewById(R.id.edwebsite);
		edSIP = (EditText) findViewById(R.id.edSIP);
		spinnerPhone = (Spinner) findViewById(R.id.spinnerMobile);
	}

	public void changemethod(View view) {
		TextView txtview = (TextView) findViewById(R.id.bAddOrg);
		txtview.setVisibility(view.GONE);
		edtxttitle.setVisibility(view.VISIBLE);
		edtxtcompany.setVisibility(view.VISIBLE);
	}

	private TextWatcher TextChangeWatcherphone = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (TextUtils.isEmpty(edphone.getText().toString())) {
				((Button) MainActivity.this.findViewById(R.id.baddnewphone))
						.setVisibility(View.GONE);
				((Button) MainActivity.this
						.findViewById(R.id.bremovephone_prime))
						.setVisibility(View.GONE);

			} else {
				((Button) MainActivity.this.findViewById(R.id.baddnewphone))
						.setVisibility(View.VISIBLE);
				((Button) MainActivity.this
						.findViewById(R.id.bremovephone_prime))
						.setVisibility(View.VISIBLE);

			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub

		}
	};

	private TextWatcher TextChangeWatcherEmail = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stubf
			if (TextUtils.isEmpty(edemail.getText().toString())) {
				((Button) MainActivity.this.findViewById(R.id.baddnewemail))
						.setVisibility(View.GONE);
				((Button) MainActivity.this
						.findViewById(R.id.bremoveemail_prime))
						.setVisibility(View.GONE);

			} else {
				((Button) MainActivity.this.findViewById(R.id.baddnewemail))
						.setVisibility(View.VISIBLE);
				((Button) MainActivity.this
						.findViewById(R.id.bremoveemail_prime))
						.setVisibility(View.VISIBLE);

			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub

		}
	};

	private TextWatcher TextChangeWatcheraddress = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (TextUtils.isEmpty(edaddress.getText().toString())) {
				((Button) MainActivity.this.findViewById(R.id.baddnewaddress))
						.setVisibility(View.GONE);
				((Button) MainActivity.this
						.findViewById(R.id.bremoveaddress_prime))
						.setVisibility(View.GONE);

			} else {
				((Button) MainActivity.this.findViewById(R.id.baddnewaddress))
						.setVisibility(View.VISIBLE);
				((Button) MainActivity.this
						.findViewById(R.id.bremoveaddress_prime))
						.setVisibility(View.VISIBLE);

			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub

		}
	};

	public void addAnother(View v) {

		registerForContextMenu(v);
		openContextMenu(v);

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.list_view_context_menu_extrafields,
				menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		int id1 = item.getItemId();
		switch (id1) {
		case R.id.action_view_SIP:
			((TextView) MainActivity.this.findViewById(R.id.tvSIP))
					.setVisibility(View.VISIBLE);
			((View) MainActivity.this.findViewById(R.id.viewSIP))
					.setVisibility(View.VISIBLE);
			((EditText) MainActivity.this.findViewById(R.id.edSIP))
					.setVisibility(View.VISIBLE);

			break;
		case R.id.action_view_IM:
			((TextView) MainActivity.this.findViewById(R.id.tvIM))
					.setVisibility(View.VISIBLE);
			((View) MainActivity.this.findViewById(R.id.viewIM))
					.setVisibility(View.VISIBLE);
			((LinearLayout) MainActivity.this.findViewById(R.id.linlayoutIM))
					.setVisibility(View.VISIBLE);
			break;
		case R.id.action_view_Nickname:
			((TextView) MainActivity.this.findViewById(R.id.tvnickname))
					.setVisibility(View.VISIBLE);
			((View) MainActivity.this.findViewById(R.id.viewnicname))
					.setVisibility(View.VISIBLE);
			((EditText) MainActivity.this.findViewById(R.id.edNickname))
					.setVisibility(View.VISIBLE);

			break;
		case R.id.action_view_Website:
			((TextView) MainActivity.this.findViewById(R.id.tvwebsite))
					.setVisibility(View.VISIBLE);
			((View) MainActivity.this.findViewById(R.id.viewwebsite))
					.setVisibility(View.VISIBLE);
			((EditText) MainActivity.this.findViewById(R.id.edwebsite))
					.setVisibility(View.VISIBLE);

			break;
		case R.id.action_view_Notes:
			((TextView) MainActivity.this.findViewById(R.id.tvnotes))
					.setVisibility(View.VISIBLE);
			((View) MainActivity.this.findViewById(R.id.viewnotes))
					.setVisibility(View.VISIBLE);
			((EditText) MainActivity.this.findViewById(R.id.edNotes))
					.setVisibility(View.VISIBLE);

			break;

		case R.id.action_view_phoneticname:
			edphoneticName.setVisibility(View.VISIBLE);

			break;
		default:
			break;
		}
		// TODO Auto-generated method stub
		return super.onContextItemSelected(item);

	}

}