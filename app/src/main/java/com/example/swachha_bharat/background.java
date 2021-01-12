package com.example.swachha_bharat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.SSLCertificateSocketFactory;
import android.os.AsyncTask;
import android.os.Build;

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

public class background extends AsyncTask<String, Void, String> {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    String email_1;
    AlertDialog dialog;
    Context context;
    public Boolean login = false;
    public background(Context context)
    {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onPreExecute() {
        dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Login Status");
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onPostExecute(String s) {
        dialog.setMessage(s);
        dialog.show();
        if(s.contains("Login Successful"))
        {
            /*Intent intent_name = new Intent();
            intent_name.setClass(context.getApplicationContext(), homepage.class);
            intent_name.putExtra("email_2,", email_1);
            context.startActivity(intent_name);*/
            Intent intent = new Intent(context.getApplicationContext(), homepage.class);
            intent.putExtra("email_2", email_1);
            context.startActivity(intent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... voids) {
        String result = "";
        String user = voids[0];
        String pass = voids[1];
        String connstr = "https://192.168.0.115/login.php";
        email_1 = user;


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
            String data = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(user,"UTF-8")
                    +"&&"+URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8");
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
