package com.giridharan.json1;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

    private ProgressDialog pDialog;

    // URL to get contacts JSON
    private static String url ="http://porter.0x10.info/api/parcel?type=json&query=list_parcel";




    // JSON Node names
    private static final String TAG_PARCELS = "parcels";
    private static final String TAG_ID = "name";
    private static final String TAG_NAME = "name";
   private static final String TAG_DATE = "date";
    private static final String TAG_TYPE = "type";
    private static final String TAG_WEIGHT = "weight";
    private static final String TAG_PHONE1="phone";
    private static final String TAG_PRICE="price";
    private static final String TAG_QTY="quantity";
    private static final String TAG_COLOR="color";
    private static final String TAG_LINK="link";
    private static final String TAG_PHONE = "live_location";
    private static final String TAG_PHONE_MOBILE = "latitude";
    private static final String TAG_PHONE_HOME = "longitude";


    // contacts JSONArray
    JSONArray contacts = null;

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> contactList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = new ArrayList<HashMap<String, String>>();

        ListView lv = getListView();

        // Listview on item click listener

        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String name = ((TextView) view.findViewById(R.id.name))
                        .getText().toString();
                String cost = ((TextView) view.findViewById(R.id.email))
                        .getText().toString();
                String description = ((TextView) view.findViewById(R.id.mobile))
                        .getText().toString();

                // Starting single contact activity
                Intent in = new Intent(getApplicationContext(), MainActivity2Activity.class);
                in.putExtra(TAG_NAME, contactList.get(position).get(TAG_ID));
                in.putExtra(TAG_DATE,contactList.get(position).get(TAG_DATE));
                in.putExtra(TAG_TYPE,contactList.get(position).get(TAG_TYPE));
                in.putExtra(TAG_WEIGHT,contactList.get(position).get(TAG_WEIGHT));
                in.putExtra(TAG_PHONE1,contactList.get(position).get(TAG_PHONE1));
                in.putExtra(TAG_PRICE,contactList.get(position).get(TAG_PRICE));
                in.putExtra(TAG_QTY,contactList.get(position).get(TAG_QTY));
                in.putExtra(TAG_COLOR,contactList.get(position).get(TAG_COLOR));
                in.putExtra(TAG_LINK,contactList.get(position).get(TAG_LINK));
                in.putExtra(TAG_PHONE_MOBILE,contactList.get(position).get(TAG_PHONE_MOBILE));
                in.putExtra(TAG_PHONE_HOME,contactList.get(position).get(TAG_PHONE_HOME));
                startActivity(in);
                System.out.println(contactList.get(position).get(TAG_ID));
            }
        });

        // Calling async task to get json
        new GetContacts().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    contacts = jsonObj.getJSONArray(TAG_PARCELS);

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString(TAG_ID);
                        String name = c.getString(TAG_NAME);
                        String date = c.getString(TAG_DATE);
                        String type = c.getString(TAG_TYPE);
                        String weight = c.getString(TAG_WEIGHT);
                        String phone1=c.getString(TAG_PHONE1);
                        String price= c.getString(TAG_PRICE);
                        String qty =c.getString(TAG_QTY);
                        String color=c.getString(TAG_COLOR);
                        String link=c.getString(TAG_LINK);

                        // Phone node is JSON Object
                        JSONObject phone = c.getJSONObject(TAG_PHONE);
                        String mobile = phone.getString(TAG_PHONE_MOBILE);
                        String home = phone.getString(TAG_PHONE_HOME);
                      //  String office = phone.getString(TAG_PHONE_OFFICE);
                            System.out.println(" "+mobile+" "+home);
                        // tmp hashmap for single contact
                        HashMap<String, String> product = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        product.put(TAG_ID, id);
                        product.put(TAG_NAME, name);
                        product.put(TAG_DATE, date);
                        product.put(TAG_TYPE, type);
                        product.put(TAG_WEIGHT, weight);
                        product.put(TAG_PHONE1,phone1 );
                        product.put(TAG_PRICE,price);
                        product.put(TAG_QTY, qty);
                        product.put(TAG_COLOR,color );
                        product.put(TAG_LINK,link);
                        product.put(TAG_PHONE_MOBILE, mobile);
                        product.put(TAG_PHONE_HOME, home);

                        // adding contact to contact list
                        contactList.add(product);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, contactList,
                    R.layout.list_item, new String[] {TAG_ID,TAG_TYPE,TAG_PRICE }, new int[] { R.id.name,
                    R.id.email, R.id.mobile });

            setListAdapter(adapter);
        }
    }

}