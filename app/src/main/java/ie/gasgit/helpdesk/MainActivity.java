package ie.gasgit.helpdesk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Time;
import java.util.Date;

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


    private Ticket ticket;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_name = findViewById(R.id.et_name);
//        et_email = findViewById(R.id.et_email);
//        et_subject = findViewById(R.id.et_subject);
//        et_center = findViewById(R.id.et_center);
//        et_emp = findViewById(R.id.et_emp);
//        et_phone = findViewById(R.id.et_phone);



        submit = findViewById(R.id.btn_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                Log.d(et_name.getText().toString(), "onClick: ");
                sendPost();
                //testAPI();
            }
        });


    }
    public void sendPost() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("name", et_name.getText());
                    jsonParam.put("gordon", 1234567);
                    URL url = new URL("http://192.168.0.164:5000/api");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Accept", "application/json");

                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());

                    os.write(jsonParam.toString().getBytes());
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
