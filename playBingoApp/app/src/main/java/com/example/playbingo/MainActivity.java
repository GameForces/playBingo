package com.example.playbingo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.net.URISyntaxException;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String username;
    private RelativeLayout PlayOnline;
    private RelativeLayout PlayWithFriends;
    private RelativeLayout ChatRoom;
    private RelativeLayout findfriends;
    private Socket mSocket;
    private int e=0;
    private CircleImageView b1;
    private CircleImageView b2;
    private CircleImageView b3;
    private CircleImageView b4;
    private CircleImageView b5;
    private int bb=0;
    private CircleImageView i1;
    private CircleImageView i2;
    private CircleImageView i3;
    private CircleImageView i4;
    private CircleImageView i5;
    private int ii=3;
    private CircleImageView n1;
    private CircleImageView n2;
    private CircleImageView n3;
    private CircleImageView n4;
    private CircleImageView n5;
    private int nn=7;
    private CircleImageView g1;
    private CircleImageView g2;
    private CircleImageView g3;
    private CircleImageView g4;
    private CircleImageView g5;
    private int gg=5;
    private CircleImageView o1;
    private CircleImageView o2;
    private CircleImageView o3;
    private CircleImageView o4;
    private CircleImageView o5;
    private int oo=6;
    private ImageView setting;
    private ImageView info;
    private ImageView settingclose;
    private int vib;
    private boolean willPlay;
    private TextView soff;
    private TextView son;
    private TextView voff;
    private TextView von;
    private String vibrate;
    private String sound;
    private AppBarConfiguration mAppBarConfiguration;
    MediaPlayer tap;
    {
        try {
            mSocket = IO.socket("https://obscure-reaches-99859.herokuapp.com/");
        } catch (URISyntaxException e) {}
    }
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);




        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);


            Initializefields();
            mSocket.connect();



            doit();
            tap=MediaPlayer.create(MainActivity.this,R.raw.tapsound);
            vib=vibratefreq.getvib();
            willPlay=soundbool.getbool();
            SocketHandler.setSocket(mSocket);

            final Vibrator vibe = (Vibrator) MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);

            mSocket.connect();
            final UserDatabase db = new UserDatabase(MainActivity.this);
            username = db.getcurrentuser();
            if(TextUtils.isEmpty(username))
            {
                Intent i = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(i);
            }

            settingdatabase dbb = new settingdatabase(MainActivity.this);

            if(dbb.getsound()!=null && dbb.getsound().equals("on"))
            {
                Toast.makeText(MainActivity.this,"hii",Toast.LENGTH_LONG).show();
                soundbool.setbool(true);
                willPlay=true;
            }
            else
            {
                soundbool.setbool(false);
                willPlay=false;
            }
            if (dbb.getVibrate()!=null && dbb.getVibrate().equals("on"))
            {
                vibratefreq.setvib(50);
                vib =50;
            }
            else
            {
                vibratefreq.setvib(0);
                vib=0;
            }
        setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vibe.vibrate(vib);
                    if(willPlay)
                        tap.start();
                    final Dialog dialog = new Dialog(MainActivity.this);
                    dialog.setContentView(R.layout.settinglayout);
                    dialog.setCanceledOnTouchOutside(true);

                    settingclose = (ImageView)dialog.findViewById(R.id.closedailogsetting);
                    settingclose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();

                        }
                    });


                    final settingdatabase dbb = new settingdatabase(MainActivity.this);
                    voff=(TextView)dialog.findViewById(R.id.voff);
                    von=(TextView)dialog.findViewById(R.id.von);
                    soff=(TextView)dialog.findViewById(R.id.soff);
                    son=(TextView)dialog.findViewById(R.id.son);


                    if(willPlay)
                    {
                        soff.setBackgroundColor(Color.parseColor("#ff0000"));
                        son.setBackgroundColor(Color.parseColor("#00ff00"));

                    }
                    else
                    {
                        soff.setBackgroundColor(Color.parseColor("#00ff00"));
                        son.setBackgroundColor(Color.parseColor("#ff0000"));

                    }
                    if(vib==0)
                    {
                        voff.setBackgroundColor(Color.parseColor("#00ff00"));
                        von.setBackgroundColor(Color.parseColor("#ff0000"));
                    }
                    else
                    {

                        von.setBackgroundColor(Color.parseColor("#00ff00"));
                        voff.setBackgroundColor(Color.parseColor("#ff0000"));

                    }
                    voff.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(willPlay)
                                tap.start();
                            vibratefreq.setvib(0);
                            vib=0;
                            voff.setBackgroundColor(Color.parseColor("#00ff00"));
                            von.setBackgroundColor(Color.parseColor("#ff0000"));
                            vibrate = "off";
                            dbb.savesetting(sound,vibrate);
                        }
                    });

                    von.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(willPlay)
                                tap.start();
                            vibratefreq.setvib(50);
                            vib=50;
                            vibe.vibrate(50);
                            von.setBackgroundColor(Color.parseColor("#00ff00"));
                            voff.setBackgroundColor(Color.parseColor("#ff0000"));
                            vibrate="on";
                            dbb.savesetting(sound,vibrate);
                        }
                    });

                    soff.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            soundbool.setbool(false);
                            vibe.vibrate(vib);
                            willPlay=false;
                            soff.setBackgroundColor(Color.parseColor("#00ff00"));
                            son.setBackgroundColor(Color.parseColor("#ff0000"));
                            sound="off";
                            dbb.savesetting(sound,vibrate);
                        }
                    });

                    son.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vibe.vibrate(vib);
                            soundbool.setbool(true);
                            willPlay=true;
                            if(willPlay)
                                tap.start();
                            son.setBackgroundColor(Color.parseColor("#00ff00"));
                            soff.setBackgroundColor(Color.parseColor("#ff0000"));
                            sound="on";
                            dbb.savesetting(sound,vibrate);
                        }
                    });
                    dialog.show();
                    dbb.savesetting(sound,vibrate);
                }
            });


            info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vibe.vibrate(vib);
                    if(willPlay)
                        tap.start();
                    final Dialog dialog = new Dialog(MainActivity.this);
                    dialog.setTitle("How to play");
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.setContentView(R.layout.infodialog);
                    dialog.show();
                }
            });

            PlayOnline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vibe.vibrate(vib);
                    if(willPlay)
                        tap.start();

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
                    vibe.vibrate(vib);
                    if(willPlay)
                        tap.start();
                    Intent i = new Intent(MainActivity.this,playwithfriends.class);
                    i.putExtra("username",username);
                    startActivity(i);
                }
            });


            ChatRoom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vibe.vibrate(vib);
                    if(willPlay)
                        tap.start();
                    Intent i = new Intent(MainActivity.this,groupchat.class);
                    i.putExtra("username",username);
                    startActivity(i);
                }
            });



            findfriends.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vibe.vibrate(vib);
                    if(willPlay)
                        tap.start();
                    Intent i =new Intent(MainActivity.this,findfriends.class);
                    startActivity(i);
                }
            });

        }


    @Override
    protected void onStart() {
        super.onStart();

        if(TextUtils.isEmpty(username))
        {
            Intent i = new Intent(MainActivity.this,RegisterActivity.class);
            startActivity(i);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        System.exit(0);
    }

    private void doit()
    {
        if(bb==0)
        {
            b2.setVisibility(View.INVISIBLE);
            b1.setVisibility(View.VISIBLE);
            bb=1;
        }
        else if(bb==1)
        {
            b1.setVisibility(View.INVISIBLE);
            b2.setVisibility(View.VISIBLE);
            bb=2;
        }
        else if(bb==2)
        {
            b2.setVisibility(View.INVISIBLE);
            b3.setVisibility(View.VISIBLE);
            bb=3;
        }
        else if(bb==3)
        {
            b3.setVisibility(View.INVISIBLE);
            b4.setVisibility(View.VISIBLE);
            bb=4;
        }
        else if(bb==4)
        {
            b4.setVisibility(View.INVISIBLE);
            b5.setVisibility(View.VISIBLE);
            bb=5;
        }
        else if(bb==5)
        {
            b5.setVisibility(View.INVISIBLE);
            b4.setVisibility(View.VISIBLE);
            bb=6;
        }
        else if(bb==6)
        {
            b4.setVisibility(View.INVISIBLE);
            b3.setVisibility(View.VISIBLE);
            bb=7;
        }
        else if(bb==7)
        {
            b3.setVisibility(View.INVISIBLE);
            b2.setVisibility(View.VISIBLE);
            bb=0;
        }

        if(ii==0)
        {
            i2.setVisibility(View.INVISIBLE);
            i1.setVisibility(View.VISIBLE);
            ii=1;
        }
        else if(ii==1)
        {
            i1.setVisibility(View.INVISIBLE);
            i2.setVisibility(View.VISIBLE);
            ii=2;
        }
        else if(ii==2)
        {
            i2.setVisibility(View.INVISIBLE);
            i3.setVisibility(View.VISIBLE);
            ii=3;
        }
        else if(ii==3)
        {
            i3.setVisibility(View.INVISIBLE);
            i4.setVisibility(View.VISIBLE);
            ii=4;
        }
        else if(ii==4)
        {
            i4.setVisibility(View.INVISIBLE);
            i5.setVisibility(View.VISIBLE);
            ii=5;
        }
        else if(ii==5)
        {
            i5.setVisibility(View.INVISIBLE);
            i4.setVisibility(View.VISIBLE);
            ii=6;
        }
        else if(ii==6)
        {
            i4.setVisibility(View.INVISIBLE);
            i3.setVisibility(View.VISIBLE);
            ii=7;
        }
        else if(ii==7)
        {
            i3.setVisibility(View.INVISIBLE);
            i2.setVisibility(View.VISIBLE);
            ii=0;
        }
        if(nn==0)
        {
            n2.setVisibility(View.INVISIBLE);
            n1.setVisibility(View.VISIBLE);
            nn=1;
        }
        else if(nn==1)
        {
            n1.setVisibility(View.INVISIBLE);
            n2.setVisibility(View.VISIBLE);
            nn=2;
        }
        else if(nn==2)
        {
            n2.setVisibility(View.INVISIBLE);
            n3.setVisibility(View.VISIBLE);
            nn=3;
        }
        else if(nn==3)
        {
            n3.setVisibility(View.INVISIBLE);
            n4.setVisibility(View.VISIBLE);
            nn=4;
        }
        else if(nn==4)
        {
            n4.setVisibility(View.INVISIBLE);
            n5.setVisibility(View.VISIBLE);
            nn=5;
        }
        else if(nn==5)
        {
            n5.setVisibility(View.INVISIBLE);
            n4.setVisibility(View.VISIBLE);
            nn=6;
        }
        else if(nn==6)
        {
            n4.setVisibility(View.INVISIBLE);
            n3.setVisibility(View.VISIBLE);
            nn=7;
        }
        else if(nn==7)
        {
            n3.setVisibility(View.INVISIBLE);
            n2.setVisibility(View.VISIBLE);
            nn=0;
        }
        if(gg==0)
        {
            g2.setVisibility(View.INVISIBLE);
            g1.setVisibility(View.VISIBLE);
            gg=1;
        }
        else if(gg==1)
        {
            g1.setVisibility(View.INVISIBLE);
            g2.setVisibility(View.VISIBLE);
            gg=2;
        }
        else if(gg==2)
        {
            g2.setVisibility(View.INVISIBLE);
            g3.setVisibility(View.VISIBLE);
            gg=3;
        }
        else if(gg==3)
        {
            g3.setVisibility(View.INVISIBLE);
            g4.setVisibility(View.VISIBLE);
            gg=4;
        }
        else if(gg==4)
        {
            g4.setVisibility(View.INVISIBLE);
            g5.setVisibility(View.VISIBLE);
            gg=5;
        }
        else if(gg==5)
        {
            g5.setVisibility(View.INVISIBLE);
            g4.setVisibility(View.VISIBLE);
            gg=6;
        }
        else if(gg==6)
        {
            g4.setVisibility(View.INVISIBLE);
            g3.setVisibility(View.VISIBLE);
            gg=7;
        }
        else if(gg==7)
        {
            g3.setVisibility(View.INVISIBLE);
            g2.setVisibility(View.VISIBLE);
            gg=0;
        }
        if(oo==0)
        {
            o2.setVisibility(View.INVISIBLE);
            o1.setVisibility(View.VISIBLE);
            oo=1;
        }
        else if(oo==1)
        {
            o1.setVisibility(View.INVISIBLE);
            o2.setVisibility(View.VISIBLE);
            oo=2;
        }
        else if(oo==2)
        {
            o2.setVisibility(View.INVISIBLE);
            o3.setVisibility(View.VISIBLE);
            oo=3;
        }
        else if(oo==3)
        {
            o3.setVisibility(View.INVISIBLE);
            o4.setVisibility(View.VISIBLE);
            oo=4;
        }
        else if(oo==4)
        {
            o4.setVisibility(View.INVISIBLE);
            o5.setVisibility(View.VISIBLE);
            oo=5;
        }
        else if(oo==5)
        {
            o5.setVisibility(View.INVISIBLE);
            o4.setVisibility(View.VISIBLE);
            oo=6;
        }
        else if(oo==6)
        {
            o4.setVisibility(View.INVISIBLE);
            o3.setVisibility(View.VISIBLE);
            oo=7;
        }
        else if(oo==7)
        {
            o3.setVisibility(View.INVISIBLE);
            o2.setVisibility(View.VISIBLE);
            oo=0;
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                doit();
            }
        },100);
    }


    private void Initializefields()
    {
        PlayOnline = (RelativeLayout) findViewById(R.id.playonlineview);
        PlayWithFriends = (RelativeLayout) findViewById(R.id.playwithfriendsview);
        ChatRoom = (RelativeLayout) findViewById(R.id.chatroomview);
        findfriends = (RelativeLayout) findViewById(R.id.checkuser);
        b1= (CircleImageView)findViewById(R.id.b1);
        b2= (CircleImageView)findViewById(R.id.b2);
        b3= (CircleImageView)findViewById(R.id.b3);
        b4= (CircleImageView)findViewById(R.id.b4);
        b5= (CircleImageView)findViewById(R.id.b5);

        i1= (CircleImageView)findViewById(R.id.i1);
        i2= (CircleImageView)findViewById(R.id.i2);
        i3= (CircleImageView)findViewById(R.id.i3);
        i4= (CircleImageView)findViewById(R.id.i4);
        i5= (CircleImageView)findViewById(R.id.i5);

        n1= (CircleImageView)findViewById(R.id.n1);
        n2= (CircleImageView)findViewById(R.id.n2);
        n3= (CircleImageView)findViewById(R.id.n3);
        n4= (CircleImageView)findViewById(R.id.n4);
        n5= (CircleImageView)findViewById(R.id.n5);


        g1= (CircleImageView)findViewById(R.id.g1);
        g2= (CircleImageView)findViewById(R.id.g2);
        g3= (CircleImageView)findViewById(R.id.g3);
        g4= (CircleImageView)findViewById(R.id.g4);
        g5= (CircleImageView)findViewById(R.id.g5);

        o1= (CircleImageView)findViewById(R.id.o1);
        o2= (CircleImageView)findViewById(R.id.o2);
        o3= (CircleImageView)findViewById(R.id.o3);
        o4= (CircleImageView)findViewById(R.id.o4);
        o5= (CircleImageView)findViewById(R.id.o5);


        setting =(ImageView)findViewById(R.id.settings);
        info =(ImageView)findViewById(R.id.info);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_drawer_drawer, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        item.setChecked(true);

        if(item.getItemId()==R.id.nav_share)
        {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "hii friend! \n i am very excited to invite you to play this new playBingo game with me." +
                    "\n"+username+" is my username" );
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);

        }
        if(item.getItemId()==R.id.nav_insta)
        {
            Uri uri = Uri.parse("https://instagram.com/playbingoforces?igshid=p37k0fvlc2ij");
            Intent i =new Intent(Intent.ACTION_VIEW,uri);
            startActivity(i);
        }
        if(item.getItemId()==R.id.nav_fb)
        {
            Uri uri = Uri.parse("https://m.facebook.com/gourav.goel.9041?tsid=0.7139436374494297&source=result");
            Intent i =new Intent(Intent.ACTION_VIEW,uri);
            startActivity(i);
        }
        if(item.getItemId()==R.id.nav_email)
        {
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.developerdialog);
            dialog.setCanceledOnTouchOutside(false);

            if(!((Activity) MainActivity.this).isFinishing()) {
                dialog.show();
            }
        }

        if(item.getItemId()==R.id.nav_profile)
        {
            Intent i =new Intent(MainActivity.this,myprofile.class);
            i.putExtra("username",username);
            startActivity(i);
        }
        if(item.getItemId()==R.id.nav_rating)
        {
        }
        if(item.getItemId()==R.id.nav_logout)
        {
            UserDatabase db = new UserDatabase(MainActivity.this);
            db.delUser();
            Intent i = new Intent(MainActivity.this,RegisterActivity.class);
            startActivity(i);
        }
        return true;
    }
}