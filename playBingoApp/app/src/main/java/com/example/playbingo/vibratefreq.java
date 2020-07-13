package com.example.playbingo;

public class vibratefreq {

    private static int vib=50;

    public static synchronized int getvib(){
        return vib;
    }

    public static synchronized void setvib(int vib){
        vibratefreq.vib = vib;
    }
}
