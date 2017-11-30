package akira.fr.chat_on;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import akira.fr.chat_on.model.ContactVO;

/**
 * Created by AKIRA on 30/11/2017.
 */

public class ContactHolder {
    private static Context context;
    private static ArrayList<ContactVO> contactAL;



    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    public ContactHolder(Context context)
    {this.setContext(context);
        getAllContacts();
    }


    public ArrayList<ContactVO> getContactAL() {
        return contactAL;
    }

    public void setContext(Context context) {this.context = context;}

    public void setContactAL(ArrayList<ContactVO> contactAL) {
        this.contactAL = contactAL;
    }

    public String fromTel(String tel) {
        tel=formatNumber(tel);
        for (int ai = 0; ai < contactAL.size(); ai++){
            if(contactAL.get(ai).getContactNumber().equals(tel)){
                return  contactAL.get(ai).getContactName();
            }
        }
        return "Unknown contact";
    }

    private String formatNumber(String num){
        if(num.length()==10){
            num=  num.replaceFirst("0","+33");
        }else if(num.length()==14){
            num = num.replace(" ","");
            num = num.replace(".","");
            num = num.replace("-","");
            num=  num.replaceFirst("0","+33");
        }

        return num;
    }


    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted,
                } else {

                    // permission denied
                }
                return;
            }
        }
    }

    private void getAllContacts() {
        ArrayList<ContactVO> contactVOList = new ArrayList();
        ContactVO contactVO;
        Bitmap photo = null;
        ContentResolver contentResolver = context.getContentResolver();
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
                        InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(),
                                ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id)));
                        if (inputStream != null) {
                            photo = BitmapFactory.decodeStream(inputStream);
                            contactVO.setPhoto(photo);
                        }else{contactVO.setPhoto( BitmapFactory.decodeResource(context.getResources(),R.drawable.contact)); }

                        if(inputStream != null){
                            inputStream.close();}

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    contactVOList.add(contactVO);
                }
                setContactAL(contactVOList);
            }

        }

    }

}
