package akira.fr.chat_on;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by nguyen on 29/11/2017.
 */

public class LogAppelAdapter extends ArrayAdapter<LogAppel> {


    private final Context context;
    private final ArrayList<LogAppel> contacts;
    private ViewHolder viewHolder;
    private final int resourceId;

    public LogAppelAdapter(Context context, int resourceId, ArrayList<LogAppel> contacts) {
        super(context, resourceId, contacts);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.contacts = contacts;
        this.resourceId = resourceId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(resourceId, parent, false);

        viewHolder = new ViewHolder();
        viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        viewHolder.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
        viewHolder.tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
        viewHolder.tv_duration = (TextView) convertView.findViewById(R.id.tv_duration);
        viewHolder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);

        LogAppel contacts_obj = contacts.get(position);

        viewHolder.tv_name.setText(contacts_obj.name);

        viewHolder.tv_phone.setText(contacts_obj.phone);
        viewHolder.tv_type.setText(" "+contacts_obj.type );
        viewHolder.tv_duration.setText(" "+contacts_obj.duration +" Sec.");
        Date call_date = new Date(Long.valueOf(contacts_obj.date));
        viewHolder.tv_date.setText(" "+call_date);

        return convertView;
    }


    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public LogAppel getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    public class ViewHolder {

        TextView tv_name;
        TextView tv_phone;
        TextView tv_type;
        TextView tv_date;
        TextView tv_duration;

    }
}
