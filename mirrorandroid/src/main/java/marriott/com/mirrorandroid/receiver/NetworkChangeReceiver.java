package marriott.com.mirrorandroid.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

import marriott.com.mirrorandroid.R;
import marriott.com.mirrorandroid.commands.SendArrival;
import marriott.com.mirrorandroid.model.Model;

/**
 * Created by bbarr233 on 4/5/14.
 */
public class NetworkChangeReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(final Context context, final Intent intent){
        NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);

        if(networkInfo.getState() == NetworkInfo.State.CONNECTED){
            //check if the wifi is home or work
            String newlyConnectedSSID = networkInfo.getExtraInfo();
            boolean isMarriottLobby = isMarriottLobby(newlyConnectedSSID, context);
            if(isMarriottLobby){
                //if it is home or work, turn on that we are listening for the next time we disconnect
                Toast.makeText(context, "connected to Lobby " + newlyConnectedSSID, Toast.LENGTH_SHORT).show();
                new SendArrival(context).execute();
            }
        }
    }

    private Boolean isMarriottLobby(String ssid, Context context){
        if(ssid.contains("Vandalay") || ssid.contains("MIHQAssocPers") || ssid.contains("Marriott_Lobby") || ssid.contains("marriottguestaccess")){
            return true;
        } else {
            return false;
        }

    }
}

