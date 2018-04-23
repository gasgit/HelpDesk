package ie.gasgit.helpdesk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
    private EditText et_center;
    private Spinner support_type;
    private Button submit;
    private Button consume;
    private AutoCompleteTextView et_support;

    private Ticket ticket;

    private  AutoCompleteTextView textView;
    private  ArrayAdapter<String> adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_subject = findViewById(R.id.et_subject);
        et_center = findViewById(R.id.et_center);
        et_emp = findViewById(R.id.et_emp);
        et_phone = findViewById(R.id.et_phone);
        et_details = findViewById(R.id.et_details);

        et_support = findViewById(R.id.et_support);
        // Get the string array
        String[] supports = getResources().getStringArray(R.array.support_array);
        // Create the adapter and set it to the AutoCompleteTextView
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, supports);
        et_support.setAdapter(adapter);






        buttonListener();


    }

    public void buttonListener(){

        submit = findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(et_name.getText().toString(), "onClick: ");

                sendPost();
            }
        });



        consume = findViewById(R.id.btn_consume);
        consume.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                testAPI();
            }
        });

    }
    public void sendPost() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    JsonObject jsonParams = new JsonObject();


                    JsonObject request = new JsonObject();

                    jsonParams.add("request", request);

                    JsonObject requester = new JsonObject();
                    requester.addProperty("requester", String.valueOf(et_email.getText()));

                    request.add("requester", requester);

                    request.addProperty("subject", String.valueOf(et_subject.getText()));

                    JsonArray customFields = new JsonArray();
                    request.add("custom_fields",customFields);


                    JsonObject c_fields1 = new JsonObject();

                    c_fields1.addProperty("id", "360000030009");
                    c_fields1.addProperty("value", et_phone.getText().toString());
                    customFields.add(c_fields1);

                    JsonObject c_fields2 = new JsonObject();

                    c_fields2.addProperty("id", "360000028405");
                    c_fields2.addProperty("value", et_emp.getText().toString());
                    customFields.add(c_fields2);

                    JsonObject c_fields3 = new JsonObject();

                    c_fields3.addProperty("id", "360000028405");
                    c_fields3.addProperty("value", et_support.getListSelection());
                    customFields.add(c_fields3);

                    JsonObject c_fields4 = new JsonObject();

                    c_fields4.addProperty("id", "360000027785");
                    c_fields4.addProperty("value", et_center.getText().toString());
                    customFields.add(c_fields4);


                    JsonObject comment = new JsonObject();
                    request.add("comment", comment);
                    comment.addProperty("body", String.valueOf(et_details.getText()));



                    JsonArray attachments = new JsonArray();
                    comment.add("attachments", attachments);

                    JsonObject att = new JsonObject();

                    att.addProperty("file_name", "file upload");
                    att.addProperty("url", "FOLDER");
                    att.addProperty("content_type", "content");
                    attachments.add(att);


                    URL url = new URL("http://192.168.0.164:5000/api");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Accept", "application/json");

                    Log.i("JSON", jsonParams.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());

                    os.write(jsonParams.toString().getBytes());
                    os.flush();
                    os.close();

                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }



    public void testAPI() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.0.164:5000/api/hello");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("content-type","application/text");
                    try {
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        BufferedReader r = new BufferedReader(new InputStreamReader(in));
                        StringBuilder total = new StringBuilder();
                        String line;
                        while ((line = r.readLine()) != null) {
                            total.append(line).append('\n');
                        }
                        Log.d("RESULT", total.toString());
                    }
                    finally {
                            urlConnection.disconnect();
                        }
                    }

                    catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }




}
