package com.gs.learn.media.util;

import java.util.ArrayList;

import com.gs.learn.media.bean.CallRecord;
import com.gs.learn.media.bean.Contact;
import com.gs.learn.media.bean.SmsContent;

import android.annotation.TargetApi;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.util.Log;

public class CommunicationUtil {
	private final static String TAG = "CommunicationUtil";
	private static Uri mContactUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
	private static String[] mContactColumn = new String[] {
		ContactsContract.CommonDataKinds.Phone.NUMBER,
		ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME };

	public static int readPhoneContacts(ContentResolver resolver) {
		ArrayList<Contact> contactArray = new ArrayList<Contact>();
		Cursor cursor = resolver.query(mContactUri, mContactColumn, null, null, null);
		if (cursor.moveToFirst()) {
			for (;; cursor.moveToNext()) {
				Contact contact = new Contact();
				contact.phone = cursor.getString(0).replace("+86", "").replace(" ", "");
				contact.name = cursor.getString(1);
				Log.d(TAG, contact.name+" "+contact.phone);
				contactArray.add(contact);
				if (cursor.isLast() == true) {
					break;
				}
			}
		}
		cursor.close();
		return contactArray.size();
	}

	public static int readSimContacts(ContentResolver resolver) {
		Uri simUri = Uri.parse("content://icc/adn");
		ArrayList<Contact> contactArray = new ArrayList<Contact>();
		Cursor cursor = resolver.query(simUri, mContactColumn, null, null, null);
		if (cursor.moveToFirst()) {
			for (;; cursor.moveToNext()) {
				Contact contact = new Contact();
				contact.phone = cursor.getString(0).replace("+86", "").replace(" ", "");
				contact.name = cursor.getString(1);
				Log.d(TAG, contact.name+" "+contact.phone);
				contactArray.add(contact);
				if (cursor.isLast() == true) {
					break;
				}
			}
		}
		cursor.close();
		return contactArray.size();
	}
	
	public static void addContacts(ContentResolver resolver, Contact contact) {
		// 往 raw_contacts 中添加数据，并获取添加的id号
		Uri raw_uri = Uri.parse("content://com.android.contacts/raw_contacts");
		ContentValues values = new ContentValues();
		long contactId = ContentUris.parseId(resolver.insert(raw_uri, values));

		// 往 data 中添加数据（要根据前面获取的id号）
		Uri uri = Uri.parse("content://com.android.contacts/data");
		ContentValues name = new ContentValues();
		name.put("raw_contact_id", contactId);
		name.put("mimetype", "vnd.android.cursor.item/name");
		name.put("data2", contact.name);
		resolver.insert(uri, name);

		ContentValues phone = new ContentValues();
		phone.put("raw_contact_id", contactId);
		phone.put("mimetype", "vnd.android.cursor.item/phone_v2");
		phone.put("data2", "2");
		phone.put("data1", contact.phone);
		resolver.insert(uri, phone);

		ContentValues email = new ContentValues();
		email.put("raw_contact_id", contactId);
		email.put("mimetype", "vnd.android.cursor.item/email_v2");
		email.put("data2", "2");
		email.put("data1", contact.email);
		resolver.insert(uri, email);
	}

	public static void addFullContacts(ContentResolver resolver, Contact contact) {
		Uri raw_uri = Uri.parse("content://com.android.contacts/raw_contacts");
		Uri uri = Uri.parse("content://com.android.contacts/data");

		ContentProviderOperation op_main = ContentProviderOperation
				.newInsert(raw_uri).withValue("account_name", null).build();
		ContentProviderOperation op_name = ContentProviderOperation
				.newInsert(uri).withValueBackReference("raw_contact_id", 0)
				.withValue("mimetype", "vnd.android.cursor.item/name")
				.withValue("data2", contact.name).build();
		ContentProviderOperation op_phone = ContentProviderOperation
				.newInsert(uri).withValueBackReference("raw_contact_id", 0)
				.withValue("mimetype", "vnd.android.cursor.item/phone_v2")
				.withValue("data2", "2").withValue("data1", contact.phone)
				.build();
		ContentProviderOperation op_email = ContentProviderOperation
				.newInsert(uri).withValueBackReference("raw_contact_id", 0)
				.withValue("mimetype", "vnd.android.cursor.item/email_v2")
				.withValue("data2", "2").withValue("data1", contact.email)
				.build();

		ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();
		operations.add(op_main);
		operations.add(op_name);
		operations.add(op_phone);
		operations.add(op_email);
		try {
			resolver.applyBatch("com.android.contacts", operations);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//private static Uri mSmsUri = Uri.parse("content://sms"); //该地址表示所有短信，包括收件箱和发件箱
	//private static Uri mSmsUri = Uri.parse("content://sms/inbox"); //该地址为收件箱
	
	private static Uri mSmsUri;
	private static String[] mSmsColumn;
	
	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static int readSms(ContentResolver resolver, String phone, int gaps) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			mSmsUri = Telephony.Sms.Inbox.CONTENT_URI;
			mSmsColumn = new String[] { 
					Telephony.Sms.ADDRESS, Telephony.Sms.PERSON,
					Telephony.Sms.BODY, Telephony.Sms.DATE,
					Telephony.Sms.TYPE};
		} else {
			mSmsUri = Uri.parse("content://sms/inbox");
			mSmsColumn = new String[] { "address","person","body","date","type" };
		}
		ArrayList<SmsContent> smsArray = new ArrayList<SmsContent>();
		String selection = "";
		if (phone!=null && phone.length()>0) {
			selection = String.format("address='%s'", phone);
		}
		if (gaps > 0) {
			selection = String.format("%s%sdate>%d", selection, 
					(selection.length()>0)?" and ":"", System.currentTimeMillis()-gaps*1000);
		}
		Cursor cursor = resolver.query(mSmsUri, mSmsColumn, selection, null, "date desc");
		if (cursor.moveToFirst()) {
			for (;; cursor.moveToNext()) {
				SmsContent sms = new SmsContent();
				sms.address = cursor.getString(0);
				sms.person = cursor.getString(1);
				sms.body = cursor.getString(2);
				sms.date = Utils.formatDate(cursor.getLong(3));
				sms.type = cursor.getInt(4);  //type=1表示收到的短信，type=2表示发送的短信
				Log.d(TAG, sms.address+" "+sms.person+" "+sms.date+" "+sms.type+" "+sms.body);
				smsArray.add(sms);
				if (cursor.isLast() == true) {
					break;
				}
			}
		}
		cursor.close();
		return smsArray.size();
	}
	
	private static Uri mRecordUri = CallLog.Calls.CONTENT_URI;
	private static String[] mRecordColumn = new String[] { 
		CallLog.Calls.CACHED_NAME, CallLog.Calls.NUMBER, CallLog.Calls.TYPE,
		CallLog.Calls.DATE, CallLog.Calls.DURATION, CallLog.Calls.NEW };
	
	public static int readCallRecord(ContentResolver resolver, int gaps) {
		ArrayList<CallRecord> recordArray = new ArrayList<CallRecord>();
		String selection = String.format("date>%d", System.currentTimeMillis()-gaps*1000);
		Cursor cursor = resolver.query(mRecordUri, mRecordColumn, selection, null, "date desc");
		//Cursor cursor = resolver.query(mRecordUri, mRecordColumn, null, null, "date desc");
		if (cursor.moveToFirst()) {
			for (;; cursor.moveToNext()) {
				CallRecord record = new CallRecord();
				record.name = cursor.getString(0);
				record.phone = cursor.getString(1);
				record.type = cursor.getInt(2);  //type=1表示接听，2表示拨出，3表示未接
				record.date = Utils.formatDate(cursor.getLong(3));
				record.duration = cursor.getLong(4);
				record._new = cursor.getInt(5);
				Log.d(TAG, record.name+" "+record.phone+" "+record.type+" "+record.date+" "+record.duration);
				recordArray.add(record);
				if (cursor.isLast() == true) {
					break;
				}
			}
		}
		cursor.close();
		return recordArray.size();
	}

	public static String readAllContacts(ContentResolver resolver) {
		ArrayList<Contact> contactArray = new ArrayList<Contact>();
		Cursor cursor = resolver.query(
				ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		int contactIdIndex = 0;
		int nameIndex = 0;

		//int count = cursor.getCount();
		if (cursor.getCount() > 0) {
			contactIdIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
			nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
		}
		while (cursor.moveToNext()) {
			Contact contact = new Contact();
			contact.contactId = cursor.getString(contactIdIndex);
			contact.name = cursor.getString(nameIndex);
			contactArray.add(contact);
		}
		cursor.close();
		
		for (int i=0; i<contactArray.size(); i++) {
			Contact contact = contactArray.get(i);
			contact.phone = getColumn(resolver, contact.contactId, 
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
					ContactsContract.CommonDataKinds.Phone.NUMBER);
			contact.email = getColumn(resolver, contact.contactId, 
					ContactsContract.CommonDataKinds.Email.CONTENT_URI,
					ContactsContract.CommonDataKinds.Email.CONTACT_ID,
					ContactsContract.CommonDataKinds.Email.DATA);
			contactArray.set(i, contact);
			Log.d(TAG, contact.contactId+" "+contact.name+" "+contact.phone+" "+contact.email);
		}
		String result = "";
		for (int i=0; i<contactArray.size(); i++) {
			Contact contact = contactArray.get(i);
			result = String.format("%s%s	%s	%s\n", result, contact.name, contact.phone, contact.email);
		}

		return result;
	}

	private static String getColumn(ContentResolver resolver, String contactId,
			Uri uri, String selection, String column) {
		String value = "";
		Cursor cursor = resolver.query(uri, null, selection+"="+contactId, null, null);
		int index = 0;
		if (cursor.getCount() > 0) {
			index = cursor.getColumnIndex(column);
		}
		while (cursor.moveToNext()) {
			value = cursor.getString(index);
		}
		cursor.close();
		return value;
	}

}
