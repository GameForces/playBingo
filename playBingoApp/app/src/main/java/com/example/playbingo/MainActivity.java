package com.example.playbingo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;


public class MainActivity extends AppCompatActivity {

    private String username;
    private TextView PlayOnline;
    private TextView PlayWithFriends;
    private TextView ChatRoom;
    private Socket mSocket;
    private int e=0;
    private Button dailogcircle1;
    private Button dailogcircle2;
    private Button dailogcircle3;
    {
        try {
            mSocket = IO.socket("https://obscure-reaches-99859.herokuapp.com/");
        } catch (URISyntaxException e) {}
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Initializefields();
        mSocket.connect();

        SocketHandler.setSocket(mSocket);

        final Vibrator vibe = (Vibrator) MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);

        mSocket.connect();
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


        PlayOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibe.vibrate(50);

                e=1;
                JSONObject info = new JSONObject();
                try {
                    info.put("username", username);

                    mSocket.emit("playOnlineRequest",info);
                    Intent i=new Intent(MainActivity.this,dailogloading.class);
                    i.putExtra("username",username);
                    startActivity(i);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });



        PlayWithFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibe.vibrate(50);
                Intent i = new Intent(MainActivity.this,playwithfriends.class);
                i.putExtra("username",username);
                startActivity(i);
            }
        });


        ChatRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibe.vibrate(50);
                Intent i = new Intent(MainActivity.this,groupchat.class);
                i.putExtra("username",username);
                startActivity(i);
            }
        });




    }






    private void Initializefields()
    {
        PlayOnline = (TextView)findViewById(R.id.play_online);
        PlayWithFriends = (TextView)findViewById(R.id.play_with_friends);
        ChatRoom = (TextView)findViewById(R.id.chat_room);

    }
}