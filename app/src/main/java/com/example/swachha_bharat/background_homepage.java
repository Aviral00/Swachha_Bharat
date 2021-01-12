package com.example.swachha_bharat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.SSLCertificateSocketFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

public class background_homepage extends AsyncTask<String, Void, String> {


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    AlertDialog dialog;
    Context context;
    public Boolean login = false;
    public background_homepage(Context context)
    {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onPreExecute() {
        dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Complaint Status");
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onPostExecute(String s) {
        dialog.setMessage(s);
        dialog.show();
        if(s.contains("Complaint registered successfully"))
        {
            Intent intent_name = new Intent();
            intent_name.setClass(context.getApplicationContext(),homepage.class);
            context.startActivity(intent_name);
        }
    }

    @Override
    protected String doInBackground(String... voids) {
        String result = "";
        String iLoc = voids[0];
        String issue = voids[1];
        String email_2 = voids[2];
        String image_string = voids[3];
        String connstr = "https://192.168.0.115/update.php";


        try {

            URL url = new URL(connstr);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            if (http instanceof HttpsURLConnection) {
                HttpsURLConnection httpsConn = (HttpsURLConnection) http;
                httpsConn.setSSLSocketFactory(SSLCertificateSocketFactory.getInsecure(0, null));
                httpsConn.setHostnameVerifier(new AllowAllHostnameVerifier());
            }
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);

            OutputStream ops = http.getOutputStream();
            BufferedWriter writer = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                writer = new BufferedWriter(new OutputStreamWriter(ops, StandardCharsets.UTF_8));
            }
            String data = URLEncoder.encode("iLoc","UTF-8")+"="+URLEncoder.encode(iLoc,"UTF-8")
                    +"&&"+URLEncoder.encode("issue","UTF-8")+"="+URLEncoder.encode(issue,"UTF-8")
                    +"&&"+URLEncoder.encode("email_2","UTF-8")+"="+URLEncoder.encode(email_2,"UTF-8")
                    +"&&"+URLEncoder.encode("image_string","UTF-8")+"="+URLEncoder.encode(image_string,"UTF-8");
            writer.write(data);
            writer.flush();
            writer.close();
            ops.close();

            InputStream ips = http.getInputStream();
            BufferedReader reader = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                reader = new BufferedReader(new InputStreamReader(ips, StandardCharsets.ISO_8859_1));
            }
            String line ="";
            while ((line = reader.readLine()) != null)
            {
                result += line;
            }
            reader.close();
            ips.close();
            http.disconnect();
            return result;

        } catch (MalformedURLException e) {
            result = e.getMessage();
        } catch (IOException e) {
            result = e.getMessage();
        }

        return result;
    }
}



