package akira.fr.chat_on;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import akira.fr.chat_on.adapter.AllContactsAdapter;
import akira.fr.chat_on.model.ContactVO;


public class ContactActivity extends AppCompatActivity {
    private String id;
    RecyclerView rvContacts;
    ContactHolder contactHolder;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        rvContacts = (RecyclerView) findViewById(R.id.rvContacts);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                return;
            }
        }
        Toast.makeText(this,"Chargement des contacts", Toast.LENGTH_LONG).show();
        getAllContacts();


        Log.d("Tag","1 - "+ ContactHolder.getInstance().fromTel("0468114628") );
        Log.d("Tag","2 - "+  ContactHolder.getInstance().fromTel("04 68 11 46 28") );
        Log.d("Tag","3 - "+  ContactHolder.getInstance().fromTel("04.68.11.46.28") );

    }

    private void getAllContacts() {
        ArrayList<ContactVO> contactVOList = new ArrayList();
        ContactVO contactVO;
        Bitmap photo = null;
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    contactVO = new ContactVO();
                    contactVO.setContactName(name);

                    Cursor phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id},
                            null);
                    if (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER));
                        if(phoneNumber == null){
                            phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            phoneNumber = phoneNumber.replace(" ","");
                            phoneNumber=  phoneNumber.replaceFirst("0","+33");
                        }
                        contactVO.setContactNumber(phoneNumber);
                    }

                    phoneCursor.close();

                    Cursor emailCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (emailCursor.moveToNext()) {
                        String emailId = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    }

                    try {
                        InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(getContentResolver(),
                                ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id)));

                        if (inputStream != null) {
                            photo = BitmapFactory.decodeStream(inputStream);
                            contactVO.setPhoto(photo);
                        }else{contactVO.setPhoto( BitmapFactory.decodeResource(getResources(),R.drawable.contact)); }



                        if(inputStream != null){
                        inputStream.close();}

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    contactVOList.add(contactVO);
                }
                ContactHolder.getInstance().setContactAL(contactVOList);
            }

            AllContactsAdapter contactAdapter = new AllContactsAdapter(contactVOList, getApplicationContext());
            rvContacts.setLayoutManager(new LinearLayoutManager(this));
            rvContacts.setAdapter(contactAdapter);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted,

                } else {

                    // permission denied
                }
                return;
            }

            // other 'switch' lines to check for other
            // permissions this app might request
        }
    }

}
