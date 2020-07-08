package com.example.playbingo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class playwithfriends extends AppCompatActivity {

    private EditText FriendsUsername;
    private Button send;
    private String fusername;
    private String username;
    private Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playwithfriends);


        username=getIntent().getStringExtra("username");

        Initializefields();


        mSocket=SocketHandler.getSocket();

        final Vibrator vibe = (Vibrator) playwithfriends.this.getSystemService(Context.VIBRATOR_SERVICE);

        mSocket.on("friendPairing",onfriendPairing);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fusername = FriendsUsername.getText().toString();

                if(TextUtils.isEmpty(fusername))
                {
                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.custum_toast, null);
                            TextView text = (TextView) layout.findViewById(R.id.meage);

                            text.setText("type friend username");
                            Toast toast = new Toast(playwithfriends.this);
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.setView(layout);
                            toast.show();
                }
                else
                {
                    JSONObject info = new JSONObject();
                    try{
                        info.put("username",username);
                        info.put("fusername",fusername);
                        mSocket.emit("friendRequest",info);
                    }catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    final ProgressDialog progressBar = new ProgressDialog(playwithfriends.this);
                    progressBar.setMessage("finding oponents");
                    progressBar.setTitle("Matching");
                    progressBar.show();

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.cancel();
                        }
                    }, 20000);
                }
            }
        });

    }

    private Emitter.Listener onfriendPairing = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    String data = args[0].toString();
                    try {
                        JSONObject info = new JSONObject(data);

                        finish();
                        Intent i = new Intent(playwithfriends.this,onlinegame.class);
                        i.putExtra("turn",info.getString("bool"));
                        i.putExtra("fuser",info.getString("fuser"));
                        i.putExtra("username",username);
                        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }catch (JSONException er)
                    {
                        er.printStackTrace();
                    }
                }
            });
        }
    };
    private void Initializefields()
    {
        FriendsUsername=(EditText)findViewById(R.id.friends_username);
        send=(Button)findViewById(R.id.send_friends_username);
    }
}
