package com.example.playbingo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
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

import static java.security.AccessController.getContext;


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
    private TextView YourTurn;
    private TextView Fuser;
    private ImageButton Mode1,Mode2;
    private RelativeLayout maing;
    private int resources;
    private View view;
    private Animation animFade;
    private TextView playonline;
    private TextView home;
    private TextView replay;
    private String username;
    private String turn;
    private String fuser;

    private Vibrator vibe;
    private TextView score1;
    private TextView score2;
    private String p1="0";
    private String p2="0";
    private int vib;
    MediaPlayer tap;
    MediaPlayer ipop;
    MediaPlayer upop;
    MediaPlayer rowformed;
    MediaPlayer outmsg;
    MediaPlayer inmsg;
    MediaPlayer ilose;
    MediaPlayer iwin;
    private boolean willplay;
    private int w=1;



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

        e=0;
        intializedfields();

        vib=vibratefreq.getvib();

        vibe = (Vibrator) onlinegame.this.getSystemService(Context.VIBRATOR_SERVICE);
        tap=MediaPlayer.create(onlinegame.this,R.raw.tapsound);
        ipop=MediaPlayer.create(onlinegame.this,R.raw.ipop);
        upop=MediaPlayer.create(onlinegame.this,R.raw.youpop);
        rowformed=MediaPlayer.create(onlinegame.this,R.raw.rowformed);
        inmsg=MediaPlayer.create(onlinegame.this,R.raw.incomingmsg);
        outmsg=MediaPlayer.create(onlinegame.this,R.raw.outgoingmsg);
        iwin=MediaPlayer.create(onlinegame.this,R.raw.youwinsound);
        ilose=MediaPlayer.create(onlinegame.this,R.raw.youlosesoundeffect);
        willplay=soundbool.getbool();

        msocket=SocketHandler.getSocket();

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(1000);

        maing.setAnimation(fadeIn);

        turnfluc();

        ObjectAnimator animation = ObjectAnimator.ofFloat(view, "translationX", 100f);
        animation.setDuration(2000);
        animation.start();

        totallinescount = 0;
        playerturn = false;
        mturn = true;
        final int n = 25;
        ArrayList<Integer> v = new ArrayList<>(n);
        generateRandom(25, v);

        turn = getIntent().getStringExtra("turn");
        fuser  = getIntent().getStringExtra("fuser");

        username = getIntent().getStringExtra("username");
        Fuser.setText(fuser);

        if (turn.equals("true")) {
            playerturn = true;
            YourTurn.setText("Your Turn");
        }

        if(w==1) {
            int k = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    a[i][j].setText(v.get(k).toString());
                    k++;
                }
            }

            Toast.makeText(onlinegame.this,w+"",Toast.LENGTH_LONG).show();
            w=0;
        }

        gamedatabase db = new gamedatabase(onlinegame.this);
            String[][] aa = new String[5][5];
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    aa[i][j] = (String) a[i][j].getText();
                }
            }

            db.savegame(aa, visited, score1.getText().toString(), score2.getText().toString());




        resources = R.drawable.gmecir;
        sendmessage.setBackgroundColor(Color.parseColor("#3498db"));
        Mode1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                vibe.vibrate(vib);
                if(willplay)
                    tap.start();
                Animation fadeIn = new AlphaAnimation(0, 1);
                fadeIn.setInterpolator(new DecelerateInterpolator());
                fadeIn.setDuration(1000);

                maing.setAnimation(fadeIn);
                maing.setBackgroundResource(R.drawable.game_wallpaper);

                for(int i=0;i<5;i++)
                {
                    for(int j=0;j<5;j++)
                    {
                        if(visited[i][j]==1)
                        {
                            a[i][j].setBackgroundResource(R.drawable.gamecircledaymode);
                        }
                        else if(visited[i][j]==2)
                        {
                            a[i][j].setBackgroundResource(R.drawable.gamecircle2);
                        }
                        else
                        {
                            a[i][j].setBackgroundResource(R.drawable.gamedaymode1);
                        }
                    }
                }
                resources = R.drawable.gamecircledaymode;
                sendmessage.setBackgroundColor(Color.parseColor("#ff0000"));
            }
        });
        Mode2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vibe.vibrate(vib);
                tap.start();

                Animation fadeIn = new AlphaAnimation(0, 1);
                fadeIn.setInterpolator(new DecelerateInterpolator());
                fadeIn.setDuration(1000);

                maing.setAnimation(fadeIn);
                maing.setBackgroundResource(R.drawable.gameback);
                for(int i=0;i<5;i++)
                {
                    for(int j=0;j<5;j++)
                    {
                        if(visited[i][j]==1)
                        {
                            a[i][j].setBackgroundResource(R.drawable.gmecir);
                        }
                        else if(visited[i][j]==2)
                        {
                            a[i][j].setBackgroundResource(R.drawable.gamecirclelast);
                        }
                        else
                        {
                            a[i][j].setBackgroundResource(R.drawable.gamecircle);
                        }
                    }
                }
                resources = R.drawable.gmecir;
                sendmessage.setBackgroundColor(Color.parseColor("#3498db"));
            }
        });



        msocket.on("ocnt", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            String data = args[0].toString();
                            JSONObject info = new JSONObject(data);
                            p1=info.getString("ocnt");
                        }catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                        if(p1.equals("0"))
                        {
                            score2.setText("0");
                        }
                        else if(p1.equals("1"))
                        {

                            score2.setText("1");
                        }
                        else if(p1.equals("2"))
                        {

                            score2.setText("2");
                        }
                        else if(p1.equals("3"))
                        {

                            score2.setText("3");
                        }
                        else if(p1.equals("4"))
                        {

                            score2.setText("4");
                        }
                        else if(p1.equals("5"))
                        {

                            score2.setText("5");
                        }
                        else if(p1.equals("6"))
                        {
                            score2.setText("6");

                        }
                        else if(p1.equals("7"))
                        {
                            score2.setText("7");

                        }
                        else
                        {
                            score2.setText("8");

                        }

                    }
                });

            }
        });


        msocket.on("cnt", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        try {
                            String data = args[0].toString();
                            JSONObject info = new JSONObject(data);
                            p1=info.getString("cnt");
                        }catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                        if(p1.equals("0"))
                        {

                            score1.setText("0");
                        }
                        else if(p1.equals("1"))
                        {

                            score1.setText("1");
                        }
                        else if(p1.equals("2"))
                        {

                            score1.setText("2");
                        }
                        else if(p1.equals("3"))
                        {

                            score1.setText("3");
                        }
                        else if(p1.equals("4"))
                        {

                            score1.setText("4");
                        }
                        else if(p1.equals("5"))
                        {

                            score1.setText("5");
                        }
                        else if(p1.equals("6"))
                        {
                            score1.setText("6");

                        }
                        else if(p1.equals("7"))
                        {
                            score1.setText("7");

                        }
                        else
                        {
                            score1.setText("8");

                        }

                    }
                });

            }
        });
        msocket.on("LetsPlay",OnPaired);
        msocket.on("friendPairing",onfriendPairing);
        JSONObject info1 = new JSONObject();
        try {
            info1.put("a11", a[0][0].getText().toString());
            info1.put("a12", a[0][1].getText().toString());
            info1.put("a13", a[0][2].getText().toString());
            info1.put("a14", a[0][3].getText().toString());
            info1.put("a15", a[0][4].getText().toString());
            info1.put("a21", a[1][0].getText().toString());
            info1.put("a22", a[1][1].getText().toString());
            info1.put("a23", a[1][2].getText().toString());
            info1.put("a24", a[1][3].getText().toString());
            info1.put("a25", a[1][4].getText().toString());
            info1.put("a31", a[2][0].getText().toString());
            info1.put("a32", a[2][1].getText().toString());
            info1.put("a33", a[2][2].getText().toString());
            info1.put("a34", a[2][3].getText().toString());
            info1.put("a35", a[2][4].getText().toString());
            info1.put("a41", a[3][0].getText().toString());
            info1.put("a42", a[3][1].getText().toString());
            info1.put("a43", a[3][2].getText().toString());
            info1.put("a44", a[3][3].getText().toString());
            info1.put("a45", a[3][4].getText().toString());
            info1.put("a51", a[4][0].getText().toString());
            info1.put("a52", a[4][1].getText().toString());
            info1.put("a53", a[4][2].getText().toString());
            info1.put("a54", a[4][3].getText().toString());
            info1.put("a55", a[4][4].getText().toString());
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        msocket.emit("yourmat", info1);



        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 5; j++) {
                visited[i - 1][j - 1] = 0;
            }
        }


        if (mturn) {

        }
        msocket.on("gamemessage", onmss);
        msocket.on("onmove", onmove);
        if(mturn) {
            msocket.on("youlose", onlose);
            msocket.on("youwin", onwin);
        }
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

                vibe.vibrate(vib);
                if(willplay)
                    outmsg.start();
                String msg = typedmessage.getText().toString();

                typedmessage.setText("");
                JSONObject info = new JSONObject();
                try {
                    vibe.vibrate(vib);

                    info.put("message", msg);
                    msocket.emit("gamechat", info);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void turnfluc()
    {
        int color = YourTurn.getCurrentTextColor();
        if(color ==Color.parseColor("#f0ff00"))
        {
            YourTurn.setTextColor(Color.parseColor("#0003ff"));
        }
        if(color == Color.parseColor("#0003ff"))
        {
            YourTurn.setTextColor(Color.parseColor("#f0ff00"));
        }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                turnfluc();
            }
        }, 200);
    }



    private Emitter.Listener onlose = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(willplay)
                        ilose.start();
                    YourTurn.setText("YOU LOSE");
                    try {
                        JSONObject info = new JSONObject();
                        info.put("username",username);
                        info.put("wins",0);
                        msocket.emit("over",info);
                    }catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    mturn = false;
                    e=0;

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            final Dialog dialog = new Dialog(onlinegame.this);
                            dialog.setContentView(R.layout.endgamedailog);
                            dialog.setCanceledOnTouchOutside(false);

                            if(!((Activity) onlinegame.this).isFinishing()) {
                                dialog.show();
                            }
                            if(((Activity) onlinegame.this).isFinishing()) {
                                dialog.dismiss();
                            }
                            replay = (TextView)dialog.findViewById(R.id.game_replay);
                            playonline = (TextView)dialog.findViewById(R.id.game_play_online);
                            home = (TextView)dialog.findViewById(R.id.game_go_home);


                            home.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(willplay)
                                        tap.start();
                                    vibe.vibrate(vib);
                                    Intent i  =new Intent(onlinegame.this,MainActivity.class);
                                    startActivity(i);
                                }
                            });
                            replay.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    vibe.vibrate(vib);
                                    if(willplay)
                                        tap.start();
                                    JSONObject info = new JSONObject();
                                    try{
                                        info.put("username",username);
                                        info.put("fusername",fuser);
                                        msocket.emit("friendRequest",info);
                                    }catch (JSONException e)
                                    {
                                        e.printStackTrace();
                                    }

                                }
                            });


                            playonline.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    vibe.vibrate(vib);
                                    if(willplay)
                                        tap.start();
                                    JSONObject info = new JSONObject();
                                    try {
                                        info.put("username", username);

                                        msocket.emit("playOnlineRequest",info);} catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });


                        }
                    }, 5000);

                }

            });
        }
    };

    @Override
    protected void onPause() {
        super.onPause();

        gamedatabase db = new gamedatabase(onlinegame.this);
        String [][] aa = new String[5][5];
        for(int i=0;i<5;i++)
        {
            for(int j=0;j<5;j++) {
                aa[i][j] = (String) a[i][j].getText();
            }
        }

        Toast.makeText(onlinegame.this,a[0][0].getText().toString()+" "+a[0][1].getText().toString()
                +" "+a[0][2].getText().toString()+" "
                +a[0][3].getText().toString()+" "+a[0][4].getText().toString()+" ",Toast.LENGTH_LONG).show();

        db.savegame(aa,visited,score1.getText().toString(),score2.getText().toString());

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    private Emitter.Listener onfriendPairing = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    String data = args[0].toString();
                    try
                    {
                        JSONObject info = new JSONObject(data);
                        finish();
                        Intent i = new Intent(onlinegame.this,onlinegame.class);
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
    private Emitter.Listener onwin = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if(willplay)
                iwin.start();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    YourTurn.setText("YOU WIN");
                    try {
                        JSONObject info =new JSONObject();
                        info.put("username",username);
                        info.put("wins",1);
                        msocket.emit("over",info);
                    }catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    mturn = false;

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            final Dialog dialog = new Dialog(onlinegame.this);

                            if(!((Activity) onlinegame.this).isFinishing()) {
                                dialog.show();
                            }
                            dialog.setContentView(R.layout.endgamedailog);
                            dialog.setCanceledOnTouchOutside(false);

                            if(!((Activity) onlinegame.this).isFinishing()) {
                                dialog.show();
                            }
                            if(((Activity) onlinegame.this).isFinishing()) {
                                dialog.dismiss();
                            }

                            replay = (TextView)dialog.findViewById(R.id.game_replay);
                            playonline = (TextView)dialog.findViewById(R.id.game_play_online);
                            home = (TextView)dialog.findViewById(R.id.game_go_home);


                            home.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    vibe.vibrate(vib);
                                    if(willplay)
                                        tap.start();
                                    Intent i  =new Intent(onlinegame.this,MainActivity.class);
                                    startActivity(i);
                                }
                            });

                            replay.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    vibe.vibrate(vib);
                                    if(willplay)
                                        tap.start();
                                    JSONObject info = new JSONObject();
                                    try{
                                        info.put("username",username);
                                        info.put("fusername",fuser);
                                        msocket.emit("friendRequest",info);
                                    }catch (JSONException e)
                                    {
                                        e.printStackTrace();
                                    }

                                }
                            });


                            playonline.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    vibe.vibrate(vib);
                                    if(willplay)
                                        tap.start();
                                    JSONObject info = new JSONObject();
                                    try {
                                        info.put("username", username);

                                        msocket.emit("playOnlineRequest",info);} catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });


                        }
                    }, 5000);

                    e=0;

                }
            });
        }
    };

    private Emitter.Listener onmss = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            inmsg.start();
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


    private Emitter.Listener OnPaired = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    String data = args[0].toString();
                    try {
                        JSONObject info = new JSONObject(data);

                        e=1;
                        finish();
                        Intent i = new Intent(onlinegame.this,onlinegame.class);
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

    private Emitter.Listener onmove = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(willplay)
                        upop.start();

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

                        a[x][y].setBackgroundResource(resources);
                        visited[x][y] = 1;

                        int cnt = 0;
                        for (int i = 0; i < 5; i++)
                        {
                            if(visited[x][i]>0)
                            {
                                cnt += 1;
                            }
                        }
                        if (cnt == 5) {
                            totallinescount += 1;
                            for (int i = 0; i < 5; i++) {
                                a[x][i].setBackgroundResource(R.drawable.gamecirclelast);
                                visited[x][i]=2;
                            }
                        }

                        cnt = 0;
                        for (int i = 0; i < 5; i++) {
                            if(visited[i][y]>0)
                            {
                                cnt += 1;
                            }
                        }
                        if (cnt == 5) {
                            totallinescount += 1;
                            for (int i = 0; i < 5; i++) {
                                a[i][y].setBackgroundResource(R.drawable.gamecirclelast);
                                visited[i][y]=2;
                            }
                        }
                        if (x + y == 4) {
                            cnt = 0;
                            for (int i = 0; i < 5; i++) {
                                if(visited[4 - i][i]>0)
                                {
                                    cnt += 1;
                                }
                            }
                            if (cnt == 5) {
                                totallinescount += 1;
                                for (int i = 0; i < 5; i++) {
                                    a[4-i][i].setBackgroundResource(R.drawable.gamecirclelast);
                                    visited[4-i][i]=2;
                                }
                            }
                        }
                        if (x == y) {
                            cnt = 0;
                            for (int i = 0; i < 5; i++) {
                                if(visited[i][i]>0)
                                {
                                    cnt += 1;
                                }
                            }
                            if (cnt == 5) {
                                totallinescount ++;
                                for (int i = 0; i < 5; i++) {
                                    a[i][i].setBackgroundResource(R.drawable.gamecirclelast);
                                    visited[i][i]=2;
                                }
                            }
                        }
                    }


                    if (mturn)
                    {
                        playerturn = true;

                        YourTurn.setText("Your Turn");
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
        Mode1 =(ImageButton) findViewById(R.id.mode1);
        Mode2 =(ImageButton) findViewById(R.id.mode2);
        YourTurn = (TextView) findViewById(R.id.your_turn);
        typedmessage = (EditText) findViewById(R.id.send_message_in_game);
        sendmessage = (ImageButton) findViewById(R.id.send_message_game);
        Fuser = (TextView)findViewById(R.id.fusername);
        maing = (RelativeLayout)findViewById(R.id.maing);
        view =(View)findViewById(R.id.v1);
        animFade = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        score1 = (TextView)findViewById(R.id.p1score);
        score2 = (TextView)findViewById(R.id.p2score);
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
        vibe.vibrate(vib);
        if(willplay)
            tap.start();
        try {
            JSONObject info =new JSONObject();
            info.put("username",username);
            info.put("wins",0);
            msocket.emit("over",info);
        }catch (JSONException e)
        {
            e.printStackTrace();
        }

        Intent i = new Intent(onlinegame.this,MainActivity.class);
        startActivity(i);

    }

    @Override
    public void onClick(View v) {
        e=1;

        vibe.vibrate(vib);
        if(willplay)
            ipop.start();
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
                a[x][y].setBackgroundResource(resources);
                visited[x][y] = 1;

                int cnt = 0;
                for (int i = 0; i < 5; i++)
                {
                    if(visited[x][i]>0)
                    {
                        cnt += 1;
                    }
                }
                if (cnt == 5) {
                    rowformed.start();
                    totallinescount += 1;
                    for (int i = 0; i < 5; i++) {
                        a[x][i].setBackgroundResource(R.drawable.gamecirclelast);
                        visited[x][i]=2;
                    }
                }

                cnt = 0;
                for (int i = 0; i < 5; i++) {
                    if(visited[i][y]>0)
                    {
                        cnt += 1;
                    }
                }
                if (cnt == 5) {
                    totallinescount += 1;
                    rowformed.start();
                    for (int i = 0; i < 5; i++) {
                        a[i][y].setBackgroundResource(R.drawable.gamecirclelast);
                        visited[i][y]=2;

                    }
                }
                if (x + y == 4) {
                    cnt = 0;
                    for (int i = 0; i < 5; i++) {
                        if(visited[4 - i][i]>0)
                        {
                            cnt += 1;
                        }
                    }
                    if (cnt == 5) {
                        rowformed.start();
                        totallinescount += 1;
                        for (int i = 0; i < 5; i++) {
                            a[4-i][i].setBackgroundResource(R.drawable.gamecirclelast);
                            visited[4-i][i]=2;
                        }
                    }
                }
                if (x == y) {
                    cnt = 0;
                    for (int i = 0; i < 5; i++) {
                        if(visited[i][i]>0)
                        {
                            cnt += 1;
                        }
                    }
                    if (cnt == 5) {
                        rowformed.start();
                        totallinescount ++;
                        for (int i = 0; i < 5; i++) {
                            a[i][i].setBackgroundResource(R.drawable.gamecirclelast);
                            visited[i][i]=2;
                        }
                    }
                }

                YourTurn.setText("");


                if(totallinescount==0)
                {

                    score1.setText("0");
                }
                else if(totallinescount==1)
                {

                    score1.setText("1");
                }
                else if(totallinescount==2)
                {

                    score1.setText("2");
                }
                else if(totallinescount==3)
                {

                    score1.setText("3");
                }
                else if(totallinescount==4)
                {

                    score1.setText("4");
                }
                else if(totallinescount==5)
                {

                    score1.setText("5");
                }
                else if(totallinescount==6)
                {
                    score1.setText("6");

                }
                else if(totallinescount==7)
                {
                    score1.setText("7");

                }
                else
                {
                    score1.setText("8");

                }
                if (totallinescount > 4) {
                    if(willplay)
                        iwin.start();
                    playerturn = false;
                    for (int i = 1; i <= 5; i++) {
                        for (int j = 1; j <= 5; j++) {
                            visited[i - 1][j - 1] = 0;
                        }
                    }

                    JSONObject info = new JSONObject();
                    try {
                        if(willplay)
                            upop.start();
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
                        info.put("e", e);
                        info.put("username",username);
                        info.put("fusername",fuser);
                    }catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                        ilose.start();
                        msocket.emit("playerwin", info);

                    String turn = getIntent().getStringExtra("turn");

                    if (turn.equals("true")) {
                        playerturn = true;
                    }


                }
                playerturn = false;
                JSONObject info = new JSONObject();
                try {
                    upop.start();
                    info.put("number", a[x][y].getText().toString());
                    info.put("x", x);
                    info.put("y", y);
                    info.put("cnt",totallinescount);
                    info.put("fusername",fuser);
                    info.put("username",username);

                    if(e==1) {
                        msocket.emit("playermove", info);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            if (mturn) {
                Toast.makeText(onlinegame.this, "Opponents turn", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(onlinegame.this, "Game Over", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}