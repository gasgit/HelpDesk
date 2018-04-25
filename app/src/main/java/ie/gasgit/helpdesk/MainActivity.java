package ie.gasgit.helpdesk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    private EditText et_name;
    private EditText et_email;
    private EditText et_subject;
    private EditText et_phone;
    private EditText et_emp;
    private EditText et_details;
    private AutoCompleteTextView et_support;
    private AutoCompleteTextView et_center;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create views
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_subject = findViewById(R.id.et_subject);
        et_emp = findViewById(R.id.et_emp);
        et_phone = findViewById(R.id.et_phone);
        et_details = findViewById(R.id.et_details);
        // create text fields with suggestions
        et_center = findViewById(R.id.et_center);
        String[] centers = getResources().getStringArray(R.array.centers_array);
        ArrayAdapter<String> centerAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, centers);
        et_center.setAdapter(centerAdapter);
        et_support = findViewById(R.id.et_support);
        String[] supports = getResources().getStringArray(R.array.support_array);
        ArrayAdapter supportAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, supports);
        et_support.setAdapter(supportAdapter);
        // listen for button events
        buttonListener();


    }

    public void buttonListener() {
        // build ticket
        // convert to json
        // post to server
        Button submit = findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(et_name.getText().toString(), "onClick: ");
                Log.d("ticket", buildTicket().getName());
                ConvertToJSON toj = new ConvertToJSON();
                Log.d("JSON TICKET", toj.toJSON(buildTicket()));
                sendPost(toj.toJSON(buildTicket()));
            }
        });

        // test api
        Button consume = findViewById(R.id.btn_consume);
        consume.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                testAPI();
            }
        });

    }


    // TODO
    // break up code/ get detail/ http request/ db helper
    // create connection file use git ignore
    // add toast/ dialog message to verify post
    // add attachment button and display field
    // create sqlite db to save tickets
    // populate arrays
    //

    // build and return ticket object from detail obtained
    public Ticket buildTicket() {

        Ticket ticket = new Ticket();
        ticket.setName(et_name.getText().toString());
        ticket.setEmail(et_email.getText().toString());
        ticket.setContact(et_phone.getText().toString());
        ticket.setSubject(et_subject.getText().toString());
        ticket.setSchool(et_center.getText().toString());
        ticket.setEmp_id(et_emp.getText().toString());
        ticket.setSupport_type(et_support.getText().toString());
        ticket.setDetailed_message(et_details.getText().toString());

        return ticket;

    }

    // accept json string from json object
    // create http connection
    // post json

    public void sendPost(final String jsonPost) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    URL url = new URL("http://192.168.0.164:5000/api");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Accept", "'text/plain");

                    Log.i("JSON", jsonPost);
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());

                    os.write(jsonPost.getBytes());
                    os.flush();
                    os.close();

                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG", conn.getResponseMessage());

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }



    // connect and call test method from dev server
    public void testAPI() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.0.164:5000/api/hello");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("content-type", "application/text");
                    try {
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        BufferedReader r = new BufferedReader(new InputStreamReader(in));
                        StringBuilder total = new StringBuilder();
                        String line;
                        while ((line = r.readLine()) != null) {
                            total.append(line).append('\n');
                        }
                        Log.d("RESULT", total.toString());
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }


}
