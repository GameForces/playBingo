package com.example.playbingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class dailogloading extends AppCompatActivity {
    private Button dailogcircle1;
    private Button dailogcircle2;
    private Button dailogcircle3;
    private Socket mSocket;
    private String username;
    private int e= 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dailogloading);


        mSocket=SocketHandler.getSocket();

        dailogcircle1 = (Button) findViewById(R.id.circle1);
        dailogcircle2 = (Button) findViewById(R.id.circle2);
        dailogcircle3 = (Button) findViewById(R.id.circle3);
        mSocket.on("LetsPlay",OnPaired);

        username = getIntent().getStringExtra("username");


        doit();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                doit();

                if (e == 1) {
                    finish();
                    Intent i = new Intent(dailogloading.this, MainActivity.class);
                    startActivity(i);
                }
            }
        }, 20000);


    }

    private void doit()
    {

        if(dailogcircle1.getTag().toString().equals("c1"))
        {

            dailogcircle1.setBackgroundResource(R.drawable.loadingcircle2);
            dailogcircle1.setTag("c2");
        }
        else if(dailogcircle1.getTag().toString().equals("c2"))
        {

            dailogcircle1.setBackgroundResource(R.drawable.loadingcircle3);
            dailogcircle1.setTag("c3");
        }
        else if(dailogcircle1.getTag().toString().equals("c3"))
        {
            dailogcircle1.setBackgroundResource(R.drawable.loadingcircle1);
            dailogcircle1.setTag("c1");
        }


        if(dailogcircle2.getTag().toString().equals("c1"))
        {

            dailogcircle2.setBackgroundResource(R.drawable.loadingcircle2);
            dailogcircle2.setTag("c2");
        }
        else if(dailogcircle2.getTag().toString().equals("c2"))
        {

            dailogcircle2.setBackgroundResource(R.drawable.loadingcircle3);
            dailogcircle2.setTag("c3");
        }
        else if(dailogcircle2.getTag().toString().equals("c3"))
        {
            dailogcircle2.setBackgroundResource(R.drawable.loadingcircle1);
            dailogcircle2.setTag("c1");
        }


        if(dailogcircle3.getTag().toString().equals("c1"))
        {

            dailogcircle3.setBackgroundResource(R.drawable.loadingcircle2);
            dailogcircle3.setTag("c2");
        }
        else if(dailogcircle3.getTag().toString().equals("c2"))
        {

            dailogcircle3.setBackgroundResource(R.drawable.loadingcircle3);
            dailogcircle3.setTag("c3");
        }
        else if(dailogcircle3.getTag().toString().equals("c3"))
        {
            dailogcircle3.setBackgroundResource(R.drawable.loadingcircle1);
            dailogcircle3.setTag("c1");
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                doit();

            }
        }, 200);
    }

    private Emitter.Listener OnPaired = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    e=0;
                    String data = args[0].toString();
                    try {
                        JSONObject info = new JSONObject(data);

                        finish();
                        Intent i = new Intent(dailogloading.this,onlinegame.class);
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
}
