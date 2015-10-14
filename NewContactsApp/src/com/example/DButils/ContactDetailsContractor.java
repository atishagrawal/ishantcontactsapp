package com.example.DButils;

import android.provider.BaseColumns;

public class ContactDetailsContractor {

	public ContactDetailsContractor() {
		//
	}

	public static abstract class ContactDetails implements BaseColumns {
		public static final String TABLE_NAME = "contact_details";
		public static final String COLUMN_NAME = "full_name";
		public static final String COLUMN_COMPANY = "company";
		public static final String COLUMN_TITLE = "title";
		public static final String COLUMN_IM = "im";
		public static final String COLUMN_NOTES = "notes";
		public static final String COLUMN_NICK_NAME = "nick_name";
		public static final String COLUMN_WEBSITE = "website";
		public static final String COLUMN_SIP = "sip";
	}

	public static abstract class PhoneDetails implements BaseColumns {
		public static final String TABLE_NAME = "phone_details";
		public static final String COLUMN_CONTACT_ID = "contact_id";
		public static final String COLUMN_PHONE_NUM = "phone_number";

	}

	public static abstract class EmailDetails implements BaseColumns {
		public static final String TABLE_NAME = "email_details";
		public static final String COLUMN_CONTACT_ID = "contact_id";
		public static final String COLUMN_EMAIL_ADDRESS = "email";

	}

	public static abstract class AddressDetails implements BaseColumns {
		public static final String TABLE_NAME = "address_details";
		public static final String COLUMN_CONTACT_ID = "contact_id";
		public static final String COLUMN_ADDRESS = "address";

	}
}
