package marriott.com.mirrorandroid.commands;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.location.Geofence;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import marriott.com.mirrorandroid.model.Model;

/**
 * Created by bbarr233 on 12/31/14.
 */
public class SendArrival extends AsyncTask<Void, Integer, Integer> {

    private Context context;

    public SendArrival(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {
        //
    }

    @Override
    protected void onPostExecute(Integer success) {
       //nothing?
        Log.d("Mirror", "finished " + success.toString());
    }

    @Override
    protected Integer doInBackground(Void... Void) {
        try {
            URL url = new URL("http://ordinal-verbena-810.appspot.com/arrive");
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://ordinal-verbena-810.appspot.com/arrive");

            try {
                SharedPreferences sp = context.getSharedPreferences(Model.PREFS_NAME, 0);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("deviceId", "12345"));
                nameValuePairs.add(new BasicNameValuePair("guestName", sp.getString("guestName","Android Man")));
                nameValuePairs.add(new BasicNameValuePair("avatar", sp.getString("avatar","http://blogs-images.forbes.com/thomasbrewster/files/2014/09/Android1.png")));
                nameValuePairs.add(new BasicNameValuePair("message", sp.getString("message","Better than iOS")));
                nameValuePairs.add(new BasicNameValuePair("memberType", sp.getString("memberType","Green")));
                nameValuePairs.add(new BasicNameValuePair("handle", sp.getString("handle","no-handle from Android")));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                Log.d("mirror", response.toString());
                if(response.getStatusLine().getStatusCode() != 200){
                    Log.d("mirror", "error occurred " + response.getStatusLine().getReasonPhrase());
                    return 0;
                }

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                Log.e("mirror", "Send Arrival client prototcol error " + e.toString());
                return 0;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                Log.e("mirror", "Send Arrival  io error " + e.toString());
                return 0;
            }
        } catch (Exception e) {
            Log.d("sp", e.getMessage());
            return 0;
        }

        return 1;
    }
}
