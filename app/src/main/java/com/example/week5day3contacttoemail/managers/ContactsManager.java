package com.example.week5day3contacttoemail.managers;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import com.example.week5day3contacttoemail.pojos.Contact;
import java.util.ArrayList;
import java.util.List;

public class ContactsManager {
    private Context context;
    private IContractManager contractManager;

    public ContactsManager(Context context) {
        this.context = context;
        this.contractManager = (IContractManager)context;
    }
    public void getContacts(){

        //define content uri
        Uri contactUri = ContactsContract.Contacts.CONTENT_URI;

        //define columns
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
        String HAS_EMAIL = "has_email";



        //retrieve the contents from contactProvider
        Cursor contactsCursor = context.getContentResolver().query(
                contactUri, null, null, null, null
        );


        List<Contact> contactList = new ArrayList<>();
        while (contactsCursor.moveToNext()) {

            String contactName = contactsCursor.getString(contactsCursor.getColumnIndex(DISPLAY_NAME));

            //Log.d(TAG, "getContacts: " + contactName);
            //retrieve phone numbers from contacts
            int hasNumberColumnIndex = contactsCursor.getColumnIndex(HAS_PHONE_NUMBER);
            int has_phone = contactsCursor.getInt(hasNumberColumnIndex);

            if (has_phone > 0) {

                List<String> numbers = new ArrayList<>();
                String email = "";
                String contactId = contactsCursor.getString(
                        contactsCursor.getColumnIndex(
                                ContactsContract.Contacts._ID));

                Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
                Uri emailUri = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
                String EMAIL = ContactsContract.CommonDataKinds.Email.ADDRESS;

                Cursor phoneCursor = context.getContentResolver().query(
                        phoneUri,
                        new String[]{NUMBER},//projection
                        DISPLAY_NAME + "=?"
                        , new String[]{contactName}
                        , NUMBER + " ASC"
                );

                Cursor emailCursor = context.getContentResolver().query(
                        emailUri,
                        null,//projection
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId
                        , null
                        , null
                );

                //Select PROJECTION from PHONEURI where SELECTION{SELECTION ARG) SORTORDER
                while (phoneCursor.moveToNext()) {
                    String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                    numbers.add(phoneNumber);
                }
                while (emailCursor.moveToNext()){
                    String emailAddress = emailCursor.getString(emailCursor.getColumnIndex(EMAIL));
                    email = emailAddress;
                }
                Contact contact = new Contact(contactName, numbers, email);
                contactList.add(contact);
            }
        }
        contractManager.onContactsRecieved(contactList);
    }


    public interface IContractManager {
        void onContactsRecieved(List<Contact> contactsList);
    }
}