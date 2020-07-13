package com.example.playbingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class groupchat extends AppCompatActivity {


    public static String uniqueId;
    private ListView messageListView;
    private MessageAdapter messageAdapter;
    private String username;

    private ImageButton sendMsg;
    private EditText typedmessage;
    private String message;
    private Socket mSocket;
    private int vib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupchat);

        final Vibrator vibe = (Vibrator) groupchat.this.getSystemService(Context.VIBRATOR_SERVICE);

        uniqueId = UUID.randomUUID().toString();
        username= getIntent().getStringExtra("username");

        sendMsg=(ImageButton)findViewById(R.id.sendgrpmessage);
        typedmessage=(EditText)findViewById(R.id.groupMessage);

        vib=vibratefreq.getvib();


        mSocket=SocketHandler.getSocket();
        messageListView = findViewById(R.id.messageListView);

        List<MessageFormat> messageFormatList = new ArrayList<>();
        messageAdapter = new MessageAdapter(groupchat.this, R.layout.item_message, messageFormatList);
        messageListView.setAdapter(messageAdapter);





        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = typedmessage.getText().toString();

                if (!TextUtils.isEmpty(message)) {
                    vibe.vibrate(vib);
                    typedmessage.setText("");
                    JSONObject info = new JSONObject();
                    try {
                        info.put("id", uniqueId);
                        info.put("username", username);
                        info.put("message", message);
                        mSocket.emit("grpMsg", info);
                        MessageFormat format = new MessageFormat(uniqueId, username, message);
                        messageAdapter.add(format);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Toast.makeText(groupchat.this,"Write message",Toast.LENGTH_LONG).show();
                }
            }
        });

        mSocket.on("grpmsg",onMssg);



    }

    private Emitter.Listener onMssg = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {


                    String id ;
                    String uname ;
                    String umsg ;
                    JSONObject data = (JSONObject) args[0];
                    try {
                         id = data.getString("id");
                         uname = data.getString("username");
                        umsg = data.getString("message");
                        MessageFormat format = new MessageFormat(id, uname, umsg);
                        messageAdapter.add(format);

                    }catch (Exception e)
                    {
                        return;
                    }
                }
            });
        }
    };
}
