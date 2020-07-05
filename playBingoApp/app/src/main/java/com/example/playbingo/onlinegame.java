package com.example.playbingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
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
    private int[][] visited = new int[5][5];
    private EditText typedmessage;
    private ImageButton sendmessage;
    private int totallinescount = 0;
    private boolean playerturn = false;
    private Socket msocket;
    private boolean mturn = true;
    private int e=0;

    private static int getNum(ArrayList<Integer> v) {
        int n = v.size();
        int index = (int) (Math.random() * n);
        int num = v.get(index);
        v.set(index, v.get(n - 1));
        v.remove(n - 1);
        return num;
    }

    private static void generateRandom(int n, ArrayList<Integer> vv) {
        ArrayList<Integer> v = new ArrayList<>(n);
        for (int i = 0; i < n; i++)
            v.add(i + 1);
        while (v.size() > 0) {
            vv.add(getNum(v));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlinegame);
        e+=1;

        intializedfields();

        RatKiller app = (RatKiller)getApplication();
        msocket = app.getmSocket();



        totallinescount = 0;
        playerturn = false;
        mturn = true;
        int n = 25;
        ArrayList<Integer> v = new ArrayList<>(n);
        generateRandom(25, v);

        String turn = getIntent().getStringExtra("turn");

        if (turn.equals("true")) {
            playerturn = true;
        }

        int k = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                a[i][j].setText(v.get(k).toString());
                k++;
            }
        }

        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 5; j++) {
                visited[i - 1][j - 1] = 0;
            }
        }


        if (mturn) {

        }
        msocket.on("getmove", onmove);
        msocket.on("gamemessage", onmss);
        msocket.on("youlose", onlose);
        msocket.on("youwin", onwin);

        sendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Toast.makeText(onlinegame.this, visited[0][0] + " " + visited[0][1] + " "
                                + visited[0][2] + " " + visited[0][3] + " " + visited[0][4] + "\n" +
                                visited[1][0] + " " + visited[1][1] + " "
                                + visited[1][2] + " " + visited[1][3] + " " + visited[1][4] + "\n" +
                                visited[2][0] + " " + visited[2][1] + " "
                                + visited[2][2] + " " + visited[2][3] + " " + visited[2][4] + "\n" +
                                visited[3][0] + " " + visited[3][1] + " "
                                + visited[3][2] + " " + visited[3][3] + " " + visited[3][4] + "\n" +
                                visited[4][0] + " " + visited[4][1] + " "
                                + visited[4][2] + " " + visited[4][3] + " " + visited[4][4] + "\n"
                        , Toast.LENGTH_SHORT).show();
                */
                String msg = typedmessage.getText().toString();

                JSONObject info = new JSONObject();
                try {
                    info.put("message", msg);
                    msocket.emit("gamechat", info);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    private Emitter.Listener onlose = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(onlinegame.this, "YOU LOSE", Toast.LENGTH_SHORT).show();
                    mturn = false;
                    msocket.disconnect();
                    finish();
                }
            });
        }
    };

    private Emitter.Listener onwin = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(onlinegame.this, "YOU WIN", Toast.LENGTH_SHORT).show();
                    mturn = false;
                    msocket.disconnect();
                    finish();

                }
            });
        }
    };

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
    private Emitter.Listener onmove = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (mturn) {
                        playerturn = true;
                    }
                    int x = 0, y = 0;

                    String m = "a";
                    try {
                        JSONObject object = new JSONObject(args[0].toString());
                        m = object.getString("number");

                    } catch (JSONException e) {
                        Toast.makeText(onlinegame.this, e.toString(), Toast.LENGTH_SHORT).show();

                        e.printStackTrace();
                    }

                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 5; j++) {
                            if (a[i][j].getText().toString().equals(m)) {
                                x = i;
                                y = j;
                            }
                        }
                    }

                    Toast.makeText(onlinegame.this, "Oponents Move: " + m, Toast.LENGTH_SHORT).show();
                    if (visited[x][y] == 0) {
                        a[x][y].setBackgroundColor(Color.parseColor("#ff0000"));
                        visited[x][y] = 1;
                        JSONObject info1 = new JSONObject();
                        try {
                            info1.put("cnt",totallinescount);
                            info1.put("a11", visited[0][0]);
                            info1.put("a12", visited[0][1]);
                            info1.put("a13", visited[0][2]);
                            info1.put("a14", visited[0][3]);
                            info1.put("a15", visited[0][4]);
                            info1.put("a21", visited[1][0]);
                            info1.put("a22", visited[1][1]);
                            info1.put("a23", visited[1][2]);
                            info1.put("a24", visited[1][3]);
                            info1.put("a25", visited[1][4]);
                            info1.put("a31", visited[2][0]);
                            info1.put("a32", visited[2][1]);
                            info1.put("a33", visited[2][2]);
                            info1.put("a34", visited[2][3]);
                            info1.put("a35", visited[2][4]);
                            info1.put("a41", visited[3][0]);
                            info1.put("a42", visited[3][1]);
                            info1.put("a43", visited[3][2]);
                            info1.put("a44", visited[3][3]);
                            info1.put("a45", visited[3][4]);
                            info1.put("a51", visited[4][0]);
                            info1.put("a52", visited[4][1]);
                            info1.put("a53", visited[4][2]);
                            info1.put("a54", visited[4][3]);
                            info1.put("a55", visited[4][4]);
                            info1.put("val", e);
                        }catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                        msocket.emit("playerw",info1);
                        int cnt = 0;
                        for (int i = 0; i < 5; i++) {
                            cnt += visited[x][i];
                        }
                        if (cnt == 5) {
                            totallinescount += 1;
                            for (int i = 0; i < 5; i++) {
                                a[x][i].setBackgroundColor(Color.parseColor("#000000"));
                            }
                        }

                        cnt = 0;
                        for (int i = 0; i < 5; i++) {
                            cnt += visited[i][y];
                        }
                        if (cnt == 5) {
                            totallinescount += 1;
                            for (int i = 0; i < 5; i++) {
                                a[i][y].setBackgroundColor(Color.parseColor("#000000"));
                            }
                        }
                        if (x + y == 4) {
                            cnt = 0;
                            for (int i = 0; i < 5; i++) {
                                cnt += visited[4 - i][i];
                            }
                            if (cnt == 5) {
                                totallinescount += 1;
                                for (int i = 0; i < 5; i++) {
                                    a[4 - i][i].setBackgroundColor(Color.parseColor("#000000"));
                                }
                            }
                        }
                        if (x == y) {
                            cnt = 0;
                            for (int i = 0; i < 5; i++) {
                                cnt += visited[i][i];
                            }
                            if (cnt == 5) {
                                totallinescount ++;
                                for (int i = 0; i < 5; i++) {
                                    a[i][i].setBackgroundColor(Color.parseColor("#000000"));
                                }
                            }
                        }

                        if (totallinescount > 4) {

                            playerturn = false;

                            for (int i = 1; i <= 5; i++) {
                                for (int j = 1; j <= 5; j++) {
                                    visited[i - 1][j - 1] = 0;
                                }
                            }

                            JSONObject info = new JSONObject();
                            try {
                                info.put("cnt",totallinescount);

                                info.put("a11", visited[0][0]);
                                info.put("a12", visited[0][1]);
                                info.put("a13", visited[0][2]);
                                info.put("a14", visited[0][3]);
                                info.put("a15", visited[0][4]);
                                info.put("a21", visited[1][0]);
                                info.put("a22", visited[1][1]);
                                info.put("a23", visited[1][2]);
                                info.put("a24", visited[1][3]);
                                info.put("a25", visited[1][4]);
                                info.put("a31", visited[2][0]);
                                info.put("a32", visited[2][1]);
                                info.put("a33", visited[2][2]);
                                info.put("a34", visited[2][3]);
                                info.put("a35", visited[2][4]);
                                info.put("a41", visited[3][0]);
                                info.put("a42", visited[3][1]);
                                info.put("a43", visited[3][2]);
                                info.put("a44", visited[3][3]);
                                info.put("a45", visited[3][4]);
                                info.put("a51", visited[4][0]);
                                info.put("a52", visited[4][1]);
                                info.put("a53", visited[4][2]);
                                info.put("a54", visited[4][3]);
                                info.put("a55", visited[4][4]);
                            }catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                            msocket.emit("playerwin",info);
                            mturn = false;

                            totallinescount = 0;
                            mturn = true;
                            int n = 25;
                            ArrayList<Integer> vv = new ArrayList<>(n);
                            generateRandom(25, vv);

                            String turn1 = getIntent().getStringExtra("turn");

                            if (turn1.equals("true")) {
                                playerturn = true;
                            }

                            int k = 0;
                            for (int i = 0; i < 5; i++) {
                                for (int j = 0; j < 5; j++) {
                                    a[i][j].setText(vv.get(k).toString());
                                    k++;
                                }
                            }

                            for (int i = 1; i <= 5; i++) {
                                for (int j = 1; j <= 5; j++) {
                                    visited[i - 1][j - 1] = 0;
                                }
                            }
                        }
                    }

                }
            });
        }
    };

    private void intializedfields() {

        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 5; j++) {
                String aid = "a" + (i) + (j);
                a[i - 1][j - 1] = (Button) findViewById(getResources().getIdentifier(aid, "id", getPackageName()));
                a[i - 1][j - 1].setOnClickListener(this);
            }
        }
        typedmessage = (EditText) findViewById(R.id.send_message_in_game);
        sendmessage = (ImageButton) findViewById(R.id.send_message_game);
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
        msocket.disconnect();
        finish();
        Intent i = new Intent(onlinegame.this,MainActivity.class);
        startActivity(i);


    }

    @Override
    public void onClick(View v) {
        if (playerturn) {
            int x = 0, y = 0;
            String vtag = v.getTag().toString();
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (vtag.equals(a[i][j].getTag().toString())) {
                        x = i;
                        y = j;
                    }
                }
            }

            Toast.makeText(onlinegame.this, a[x][y].getText().toString(), Toast.LENGTH_SHORT).show();
            if (visited[x][y] == 0) {
                a[x][y].setBackgroundColor(Color.parseColor("#ff0000"));
                visited[x][y] = 1;
                int cnt = 0;
                for (int i = 0; i < 5; i++) {
                    cnt += visited[x][i];
                }
                if (cnt == 5) {
                    totallinescount += 1;
                    for (int i = 0; i < 5; i++) {
                        a[x][i].setBackgroundColor(Color.parseColor("#000000"));
                    }
                }

                cnt = 0;
                for (int i = 0; i < 5; i++) {
                    cnt += visited[i][y];
                }
                if (cnt == 5) {
                    totallinescount += 1;
                    for (int i = 0; i < 5; i++) {
                        a[i][y].setBackgroundColor(Color.parseColor("#000000"));
                    }
                }
                if (x + y == 4) {
                    cnt = 0;
                    for (int i = 0; i < 5; i++) {
                        cnt += visited[4 - i][i];
                    }
                    if (cnt == 5) {
                        totallinescount += 1;
                        for (int i = 0; i < 5; i++) {
                            a[4 - i][i].setBackgroundColor(Color.parseColor("#000000"));
                        }
                    }
                }
                if (x == y) {
                    cnt = 0;
                    for (int i = 0; i < 5; i++) {
                        cnt += visited[i][i];
                    }
                    if (cnt == 5) {
                        totallinescount += 1;
                        for (int i = 0; i < 5; i++) {
                            a[i][i].setBackgroundColor(Color.parseColor("#000000"));
                        }
                    }
                }


                if (totallinescount > 4) {

                    playerturn = false;

                    for (int i = 1; i <= 5; i++) {
                        for (int j = 1; j <= 5; j++) {
                            visited[i - 1][j - 1] = 0;
                        }
                    }

                    JSONObject info = new JSONObject();
                    try {
                        info.put("cnt",totallinescount);

                        info.put("a11", visited[0][0]);
                        info.put("a12", visited[0][1]);
                        info.put("a13", visited[0][2]);
                        info.put("a14", visited[0][3]);
                        info.put("a15", visited[0][4]);
                        info.put("a21", visited[1][0]);
                        info.put("a22", visited[1][1]);
                        info.put("a23", visited[1][2]);
                        info.put("a24", visited[1][3]);
                        info.put("a25", visited[1][4]);
                        info.put("a31", visited[2][0]);
                        info.put("a32", visited[2][1]);
                        info.put("a33", visited[2][2]);
                        info.put("a34", visited[2][3]);
                        info.put("a35", visited[2][4]);
                        info.put("a41", visited[3][0]);
                        info.put("a42", visited[3][1]);
                        info.put("a43", visited[3][2]);
                        info.put("a44", visited[3][3]);
                        info.put("a45", visited[3][4]);
                        info.put("a51", visited[4][0]);
                        info.put("a52", visited[4][1]);
                        info.put("a53", visited[4][2]);
                        info.put("a54", visited[4][3]);
                        info.put("a55", visited[4][4]);
                    }catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    msocket.emit("playerwin",info);
                    mturn = false;
                    totallinescount = 0;
                    mturn = true;
                    int n = 25;
                    ArrayList<Integer> vv = new ArrayList<>(n);
                    generateRandom(25, vv);

                    String turn = getIntent().getStringExtra("turn");

                    if (turn.equals("true")) {
                        playerturn = true;
                    }

                    int k = 0;
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 5; j++) {
                            a[i][j].setText(vv.get(k).toString());
                            k++;
                        }
                    }

                    for (int i = 1; i <= 5; i++) {
                        for (int j = 1; j <= 5; j++) {
                            visited[i - 1][j - 1] = 0;
                        }
                    }

                }
                playerturn = false;
                JSONObject info = new JSONObject();
                try {
                    info.put("number", a[x][y].getText().toString());
                    info.put("cnt", totallinescount);
                    info.put("a11", visited[0][0]);
                    info.put("a12", visited[0][1]);
                    info.put("a13", visited[0][2]);
                    info.put("a14", visited[0][3]);
                    info.put("a15", visited[0][4]);
                    info.put("a21", visited[1][0]);
                    info.put("a22", visited[1][1]);
                    info.put("a23", visited[1][2]);
                    info.put("a24", visited[1][3]);
                    info.put("a25", visited[1][4]);
                    info.put("a31", visited[2][0]);
                    info.put("a32", visited[2][1]);
                    info.put("a33", visited[2][2]);
                    info.put("a34", visited[2][3]);
                    info.put("a35", visited[2][4]);
                    info.put("a41", visited[3][0]);
                    info.put("a42", visited[3][1]);
                    info.put("a43", visited[3][2]);
                    info.put("a44", visited[3][3]);
                    info.put("a45", visited[3][4]);
                    info.put("a51", visited[4][0]);
                    info.put("a52", visited[4][1]);
                    info.put("a53", visited[4][2]);
                    info.put("a54", visited[4][3]);
                    info.put("a55", visited[4][4]);
                    msocket.emit("playermove", info);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            if (mturn) {
                Toast.makeText(onlinegame.this, "Opponents turn", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(onlinegame.this, "Game Over", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}