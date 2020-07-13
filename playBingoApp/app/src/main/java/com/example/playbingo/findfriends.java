package com.example.playbingo;

import androidx.appcompat.app.AppCompatActivity;

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

public class findfriends extends AppCompatActivity {

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
        setContentView(R.layout.activity_findfriends);


        Initializedfields();

        mSocket=SocketHandler.getSocket();

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

                            relativeLayout.setVisibility(View.VISIBLE);



                        }catch (JSONException e)
                        {
                            relativeLayout.setVisibility(View.INVISIBLE);
                            Toast.makeText(findfriends.this,e.toString(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }
                });
            }
        });


        senduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = fusername.getText().toString();
                try {
                    JSONObject info =new JSONObject();
                    info.put("username",fname);
                    mSocket.emit("profileReq",info);
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    private void Initializedfields()
    {
        fusershow = (TextView)findViewById(R.id.findfusershow);
        matchplayedshow=(TextView)findViewById(R.id.matchplayedshow);
        atchwinshow = (TextView)findViewById(R.id.matchwinsshow);
        senduser = (Button)findViewById(R.id.sendfuser);
        fusername = (EditText)findViewById(R.id.findfuser);
        relativeLayout = (RelativeLayout)findViewById(R.id.findfrienduser);
        rating = (TextView)findViewById(R.id.matchrating);
    }
}
