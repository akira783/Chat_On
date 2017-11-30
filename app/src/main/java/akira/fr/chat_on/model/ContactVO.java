package akira.fr.chat_on.model;

import android.graphics.Bitmap;

/**
 * Created by AKIRA on 29/11/2017.
 */

public class ContactVO {
    private String ContactImage;
    private String ContactName;
    private String ContactNumber;
    private Bitmap photo = null;

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getContactImage() {
        return ContactImage;
    }

    public void setContactImage(String contactImage) {
        this.ContactImage = ContactImage;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }
}