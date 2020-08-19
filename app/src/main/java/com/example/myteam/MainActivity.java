package com.example.myteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
private int SLEEP_TIMER2 = 3;
private  double SLEEP_TIMER1 =  0.25;
private  FirebaseFirestore fStore;
private FirebaseAuth fAuth;
private  FirebaseUser user;
private  String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();

        LogoLauncher logoLauncher=new LogoLauncher();
        logoLauncher.start();
    }
    private class LogoLauncher extends Thread{
        public void run() {
            if (user!=null){
                try {
                    sleep((long) (1000*SLEEP_TIMER1));
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                userid = user.getUid();
                fStore.collection("User").document(userid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        try {
                            if (task.getResult().exists()) {
                                Intent intent = new Intent(MainActivity.this, menu.class);
                                startActivity(intent);
                                MainActivity.this.finish();
                            } else {
                                Intent intent = new Intent(MainActivity.this, submit_info.class);
                                startActivity(intent);
                                MainActivity.this.finish();
                            }
                        }
                        catch (Exception e){
                            Intent intent = new Intent(MainActivity.this, submit_info.class);
                            startActivity(intent);
                            MainActivity.this.finish();
                        }
                    }
                });
            }
            else{
                try {
                    sleep(1000*SLEEP_TIMER2);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                Intent intent =new Intent(MainActivity.this, signActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        }
    }

}
