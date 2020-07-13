package com.example.playbingo;

public class soundbool{

    private static boolean play=true;

    public static synchronized boolean getbool(){
        return play;
    }

    public static synchronized void setbool(boolean change){
        soundbool.play=change;
    }
}
