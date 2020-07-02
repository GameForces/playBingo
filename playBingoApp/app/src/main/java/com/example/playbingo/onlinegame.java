package com.example.playbingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;


import java.util.*;
import java.lang.Math;


public class onlinegame extends AppCompatActivity implements View.OnClickListener {

    private Button[][] a = new Button[5][5];
    private EditText typedmessage;
    private ImageButton sendmessage;
    private Socket msocket;
    {
        try {
            msocket = IO.socket("https://obscure-reaches-99859.herokuapp.com/");
        } catch (URISyntaxException e) {}
    }


    // Function to return the next random number
    private static int getNum(ArrayList<Integer> v)
    {
        // Size of the vector
        int n = v.size();

        // Make sure the number is within
        // the index range
        int index = (int)(Math.random() * n);

        // Get random number from the vector
        int num = v.get(index);

        // Remove the number from the vector
        v.set(index, v.get(n - 1));
        v.remove(n - 1);

        // Return the removed number
        return num;
    }

    // Function to generate n
    // non-repeating random numbers
    private static void generateRandom(int n,ArrayList<Integer> vv)
    {

        // Fill the vector with the values
        // 1, 2, 3, ..., n

        ArrayList<Integer> v = new ArrayList<>(n);
        for (int i = 0; i < n; i++)
            v.add(i + 1);

        // While vector has elements
        // get a random number from the vector and print it
        while (v.size() > 0)
        {
            vv.add(getNum(v));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlinegame);



        int n = 25;
        ArrayList<Integer> v = new ArrayList<>(n);
        generateRandom(25,v);



        intializedfields();

        int k=0;
        for(int i=0;i<5;i++)
        {
            for(int j=0;j<5;j++)
            {
                a[i][j].setText(v.get(k).toString());
                k++;
            }
        }
        msocket.on("gamemessage",onmss);

        sendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msocket.connect();
                String msg = typedmessage.getText().toString();
                JSONObject info = new JSONObject();
                try{
                    info.put("message",msg);
                    msocket.emit("gamechat",info);
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Emitter.Listener onmss = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.custum_toast, null);
                    TextView text = (TextView) layout.findViewById(R.id.meage);

                    text.setText(args[0].toString());
                    Toast toast = new Toast(onlinegame.this);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                }
            });
        }
    };

    private void intializedfields()
    {

        for(int i=1;i<=5;i++)
        {
            for(int j=1;j<=5;j++)
            {
                String aid = "a"+(i)+(j);
                a[i-1][j-1]=(Button)findViewById(getResources().getIdentifier(aid,"id",getPackageName()));
                a[i-1][j-1].setOnClickListener(this);
            }
        }
        typedmessage=(EditText)findViewById(R.id.send_message_in_game);
        sendmessage=(ImageButton)findViewById(R.id.send_message_game);
    }

    @Override
    public void onClick(View v)
    {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
