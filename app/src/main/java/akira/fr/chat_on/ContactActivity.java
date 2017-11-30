package akira.fr.chat_on;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import akira.fr.chat_on.adapter.AllContactsAdapter;


public class ContactActivity extends AppCompatActivity {

    RecyclerView rvContacts;

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
