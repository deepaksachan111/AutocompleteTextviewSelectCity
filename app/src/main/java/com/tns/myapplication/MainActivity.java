package com.tns.myapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> name = new ArrayList<String>();
    //  private  ProgressDialog  pDialog;
   // String[] language = {"C", "C++", "Java", ".NET", "iPhone", "Android", "ASP.NET", "PHP"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        volleyJsonObjectRequest();

    }

    public void volleyJsonObjectRequest() {
        String tag_json_obj = "json_obj_req";

        String url = "http://www.androidbegin.com/tutorial/jsonparsetutorial.txt";

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response", response.toString());
                        pDialog.hide();
                        parseJson(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                pDialog.hide();
            }
        });

// Adding request to request queue
        AppSingleton.getInstance(this).addToRequestQueue(jsonObjReq, tag_json_obj);


    }

    private void setAdapter(ArrayList<String> list) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, list);
        //Getting the instance of AutoCompleteTextView
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        actv.setTextColor(Color.RED);
        //volleyJsonObjectRequest();
    }

    private void parseJson(JSONObject response) {
        ArrayList<String> list = new ArrayList<String>();
        JSONObject jsonobject = response;
        JSONArray jsonarray;


        // loop through each json object
        try {
            // Locate the array name in JSON
            jsonarray = jsonobject.getJSONArray("worldpopulation");

            for (int i = 0; i < jsonarray.length(); i++) {

                jsonobject = jsonarray.getJSONObject(i);
                String country = jsonobject.getString("country");
                list.add(country);

            }
            setAdapter(list);
        } catch (JSONException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
    }
}











