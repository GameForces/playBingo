package com.example.playbingo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {

    private String username;
    private TextView PlayOnline;
    private TextView PlayWithFriends;
    private TextView ChatRoom;
    private Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Initializefields();

        RatKiller app = (RatKiller)getApplication();
        mSocket = app.getmSocket();

        if (mSocket.connected()){
            Toast.makeText(MainActivity.this, "Connected!!",Toast.LENGTH_SHORT).show();
        }

        UserDatabase db = new UserDatabase(MainActivity.this);
        username = db.getcurrentuser();

        if(TextUtils.isEmpty(username))
        {
            Intent i = new Intent(MainActivity.this,RegisterActivity.class);
            startActivity(i);
        }
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custum_toast, null);
        TextView text = (TextView) layout.findViewById(R.id.meage);

        text.setText("Welcome "+username);
        Toast toast = new Toast(MainActivity.this);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();

        mSocket.on("LetsPlay",OnPaired);

        PlayOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                JSONObject info = new JSONObject();
                try {
                    info.put("username", username);

                    mSocket.emit("playOnlineRequest",info);} catch (JSONException e) {
                    e.printStackTrace();
                }

                final ProgressDialog progressBar = new ProgressDialog(MainActivity.this);
                progressBar.setMessage("finding oponents");
                progressBar.setTitle("Matching");
                progressBar.show();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        progressBar.cancel();
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.custum_toast, null);
                        TextView text = (TextView) layout.findViewById(R.id.meage);

                        text.setText("No user found");
                        Toast toast = new Toast(MainActivity.this);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);
                        toast.show();

                    }
                }, 20000);

            }
        });


        PlayWithFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,playwithfriends.class);
                i.putExtra("username",username);
                startActivity(i);
            }
        });


        ChatRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,groupchat.class);
                i.putExtra("username",username);
                startActivity(i);
            }
        });




    }


    private Emitter.Listener OnPaired = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Toast.makeText(MainActivity.this,"hii"+args[0].toString(),Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this,onlinegame.class);
                    i.putExtra("turn",args[0].toString());
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
            });
        }
    };


    private void Initializefields()
    {
        PlayOnline = (TextView)findViewById(R.id.play_online);
        PlayWithFriends = (TextView)findViewById(R.id.play_with_friends);
        ChatRoom = (TextView)findViewById(R.id.chat_room);
    }
}
