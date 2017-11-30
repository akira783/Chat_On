package akira.fr.chat_on;

import java.util.ArrayList;

import akira.fr.chat_on.model.ContactVO;

/**
 * Created by AKIRA on 30/11/2017.
 */

public class ContactHolder {
    private static ArrayList<ContactVO> contactAL;
    private static ContactHolder INSTANCE = null;
    private ContactHolder()
    {}

    public static ContactHolder getInstance()
    {
        if (INSTANCE == null)
        { 	INSTANCE = new ContactHolder();
        }
        return INSTANCE;
    }

    public ArrayList<ContactVO> getContactAL() {
        return contactAL;
    }

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

}
