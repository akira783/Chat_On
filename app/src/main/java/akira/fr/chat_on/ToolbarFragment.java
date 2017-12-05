package akira.fr.chat_on;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ToolbarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ToolbarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToolbarFragment extends Fragment {


    private TextView contact, appel, sms;

    public ToolbarFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_toolbar, container, false);
        contact = (TextView) view.findViewById(R.id.contact);
        appel = (TextView) view.findViewById(R.id.appel);
        sms = (TextView) view.findViewById(R.id.sms);

        contact.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iContact = new Intent(getActivity(), ContactActivity.class);
                startActivity(iContact);
            }
        });
        appel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iAppel = new Intent(getActivity(), AppelActivity.class);
                startActivity(iAppel);
            }
        });
        sms.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iSMS = new Intent(getActivity(), DiscussionActivity.class);
                startActivity(iSMS);
            }
        });
        return view;
    }

}
