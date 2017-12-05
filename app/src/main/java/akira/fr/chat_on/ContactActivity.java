package akira.fr.chat_on;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import akira.fr.chat_on.adapter.AllContactsAdapter;


public class ContactActivity extends AppCompatActivity {



    public ContactActivity(){}


    RecyclerView rvContacts;

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);


        ContactHolder contactHolder = new ContactHolder(this.getApplicationContext());
        contactHolder.getContactAL();

        rvContacts = (RecyclerView) findViewById(R.id.rvContacts);
        Toast.makeText(this,"Chargement des contacts", Toast.LENGTH_LONG).show();

        AllContactsAdapter contactAdapter = new AllContactsAdapter(contactHolder.getContactAL(), getApplicationContext());
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        rvContacts.setAdapter(contactAdapter);


    }


}
