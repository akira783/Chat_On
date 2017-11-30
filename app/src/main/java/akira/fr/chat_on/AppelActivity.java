package akira.fr.chat_on;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;


public class AppelActivity extends AppCompatActivity {

    ListView list;
    Cursor cursor ;
    ArrayList<LogAppel> arr ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appel);

        arr = new ArrayList<LogAppel>();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_CALL_LOG)) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_CALL_LOG}, 1);
            }
            else{
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_CALL_LOG}, 1);
            }
        } else {
            System.out.println(" "+ arr);
            LogAppelTask logAppelTask = new LogAppelTask(AppelActivity.this);
            logAppelTask.execute();

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            switch (requestCode){
                case 1 : {
                     if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CALL_LOG)== PackageManager.PERMISSION_GRANTED){
                         Toast.makeText(this, "Autorisation de Permission", Toast.LENGTH_SHORT).show();
                }else{
                         Toast.makeText(this, "Absence de permission", Toast.LENGTH_SHORT).show();
                     }
            }
            return;
        }
    }

    private ArrayList<LogAppel> getMyCallLog() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Absence de permission", Toast.LENGTH_SHORT).show();
        }
        cursor = getBaseContext().getContentResolver().query(Uri.parse("content://call_log/calls"), null, null, null, null);

        /*System.out.println("cursour "+ cursor.getColumnCount());
       for (int i=0;i<cursor.getColumnCount();i++) {
            System.out.println(" " + i +" "+ cursor.getColumnName(i));
        }*/

        int name_id = cursor.getColumnIndex("name");
        int number_id = cursor.getColumnIndex("number");
        int duration_id = cursor.getColumnIndex("duration");
        int type_id = cursor.getColumnIndex("type");
        int date_id = cursor.getColumnIndex("date");

        while (cursor.moveToNext()) {

            String name = cursor.getString(name_id);
            String phone = cursor.getString(number_id);
            String type = cursor.getString(type_id);
            String duration = cursor.getString(duration_id);
            String date = cursor.getString(date_id);


            int id = Integer.parseInt(type);

            String stype = "";

            switch (id) {
                case CallLog.Calls.OUTGOING_TYPE:
                    stype = "Sortant";
                    System.out.println(" "+ type);
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    stype = "Entrant";
                    System.out.println(" "+ type);
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    stype = "Absence";
                    System.out.println(" "+ type);
                    break;
            }

            if (phone != null) {
                arr.add(new LogAppel(phone, stype, name, duration, date));
            }else{
                arr.add(new LogAppel(phone, stype, "Anonyme", duration, date));
            }
        }

        cursor.close();
        return arr;

    }


    public class LogAppelTask extends AsyncTask<ArrayList<LogAppel>, Void, ArrayList<LogAppel>>{

        private ProgressDialog progDailog;

        AppelActivity appelActivity;
        Context ctx;

        public LogAppelTask(Context ctx){
            this.ctx = ctx;
            this.appelActivity = (AppelActivity)ctx;
        }

        protected void onPreExecute() {
            progDailog = new ProgressDialog(AppelActivity.this);
            progDailog.setCancelable(false);
            progDailog.setMessage("Rechargement ...");
            progDailog.show();

        }

        @Override
        protected ArrayList<LogAppel> doInBackground(ArrayList<LogAppel>... params) {
            arr = getMyCallLog();
            return arr;
        }


        @Override
        protected void onPostExecute(ArrayList<LogAppel> result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            result = arr;
            list = (ListView) appelActivity.findViewById(R.id.list);
            LogAppelAdapter adapter = new LogAppelAdapter(appelActivity, R.layout.item_activity_appel, result);
            list.setAdapter(adapter);

            progDailog.dismiss();
        }
    }


}
