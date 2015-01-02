package marriott.com.mirrorandroid;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import marriott.com.mirrorandroid.model.Model;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {

    // UI references.
    private EditText nameTv;
    private EditText avatarUrlTv;
    private EditText messageTv;
    private RadioGroup memberRg;
    private RadioButton selectedRadio;
    private Button saveBtn;
    private EditText instagramHandleTv;
    private Button instagramBtn;
    private LinearLayout infoForm;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nameTv = (EditText) findViewById(R.id.nameTv);
        avatarUrlTv = (EditText) findViewById(R.id.avatarUrlTv);
        messageTv = (EditText) findViewById(R.id.messageTv);
        memberRg = (RadioGroup) findViewById(R.id.memberRg);
        instagramHandleTv = (EditText) findViewById(R.id.instagramHandleTv);
        instagramBtn = (Button) findViewById(R.id.instagramBtn);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        infoForm = (LinearLayout) findViewById(R.id.infoForm);

        saveBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                saveStuff();
            }
        });
        instagramBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                linkToInstagram();
            }
        });

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        Map bla = settings.getAll();
        Log.d("mirror", bla.toString());


//        mLoginFormView = findViewById(R.id.login_form);
//        mProgressView = findViewById(R.id.login_progress);
    }

    public static final String PREFS_NAME = "MyPrefsFile";

    private void saveStuff() {
        SharedPreferences settings = getSharedPreferences(Model.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("guestName", nameTv.getText().toString());
        editor.putString("avatar", avatarUrlTv.getText().toString());
        editor.putString("message", messageTv.getText().toString());
        editor.putString("handle", instagramHandleTv.getText().toString());
        int selectedId = memberRg.getCheckedRadioButtonId();
        selectedRadio = (RadioButton) findViewById(selectedId);
        editor.putString("memberType", selectedRadio.getText().toString());

        // Commit the edits!
        editor.commit();

        Toast.makeText(LoginActivity.this, "Info was saved", Toast.LENGTH_SHORT).show();
        infoForm.setVisibility(View.GONE);
        instagramBtn.setVisibility(View.VISIBLE);
    }

    private void linkToInstagram(){
//        String serverHandler = "http://ordinal-verbena-810.appspot.com/instagram?handle="
//                + instagramHandleTv.getText().toString();
//        String redirectUrl = "https://instagram.com/oauth/authorize/?client_id="
//                + Model.INSTAGRAM_CLIENT_ID
//                + "&redirect_uri="+ serverHandler +"&response_type=token";

        String serverHandler = "http://ordinal-verbena-810.appspot.com/instagram";

        String instagramUrl = "https://api.instagram.com/oauth/authorize/?"
            + "client_id=" + Model.INSTAGRAM_CLIENT_ID
            + "&redirect_uri="+ serverHandler
            + "&response_type=code";
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl));
        startActivity(browserIntent);
    }

}



