package com.example.playbingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    {
        try {
            mSocket = IO.socket("https://obscure-reaches-99859.herokuapp.com/");
        } catch (URISyntaxException e) {}
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupchat);


        uniqueId = UUID.randomUUID().toString();
        username= getIntent().getStringExtra("username");

        sendMsg=(ImageButton)findViewById(R.id.sendgrpmessage);
        typedmessage=(EditText)findViewById(R.id.groupMessage);



        messageListView = findViewById(R.id.messageListView);

        List<MessageFormat> messageFormatList = new ArrayList<>();
        messageAdapter = new MessageAdapter(groupchat.this, R.layout.item_message, messageFormatList);
        messageListView.setAdapter(messageAdapter);





        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = typedmessage.getText().toString();

                JSONObject info = new JSONObject();
                try {
                    info.put("id",uniqueId);
                    info.put("username",username);
                    info.put("message",message);
                    mSocket.connect();
                    mSocket.emit("grpMsg",info);
                    MessageFormat format = new MessageFormat(uniqueId, username, message);
                    messageAdapter.add(format);
                } catch (JSONException e) { e.printStackTrace(); }
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