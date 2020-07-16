package com.example.playbingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

public class myprofile extends AppCompatActivity {

    private String username;
    private TextView fusershow;
    private TextView matchplayedshow;
    private TextView atchwinshow;
    private EditText fusername;
    private Button senduser;
    private Socket mSocket;
    private RelativeLayout relativeLayout;
    private TextView rating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

        Initializedfields();

        username = getIntent().getStringExtra("username");
        mSocket=SocketHandler.getSocket();

        try {
            JSONObject info =new JSONObject();
            info.put("username",username);
            mSocket.emit("profileReq",info);
        }catch (JSONException e)
        {
            e.printStackTrace();
        }

        mSocket.on("profile", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String data = args[0].toString();
                        try {
                            JSONObject info = new JSONObject(data);

                            fusershow.setText(info.getString("username"));
                            matchplayedshow.setText(info.getString("matchplayed"));
                            atchwinshow.setText(info.getString("matchwin"));
                            rating.setText(info.getString("rating"));




                        }catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });

    }
    private void Initializedfields()
    {
        fusershow = (TextView)findViewById(R.id.myprousershow);
        matchplayedshow=(TextView)findViewById(R.id.mymatchplayedshow);
        atchwinshow = (TextView)findViewById(R.id.mymatchwinsshow);
        rating = (TextView)findViewById(R.id.mymatchrating);
    }
}
