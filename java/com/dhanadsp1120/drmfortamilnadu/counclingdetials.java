package com.dhanadsp1120.drmfortamilnadu;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class counclingdetials extends AppCompatActivity {
    ImageSlider is;
    YouTubePlayerView youTubePlayerView;
    TextView vn,vc,vt;
    List<SlideModel> sm = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counclingdetials);
        is = findViewById(R.id.imageslider);
        vn=findViewById(R.id.name);
        vc=findViewById(R.id.content);
        vt=findViewById(R.id.tips);
        vn.setText(councling.rr);
        vc.setText(councling.content);
        vt.setText(councling.tips);
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {

                youTubePlayer.cueVideo(councling.utube, 0);
                //  super.onReady(youTubePlayer);
            }
        });
        sm.clear();
        Log.d("lsk",councling.imagelink);
        String s[]=councling.imagelink.split("@@");
        Log.d("lsk",String.valueOf(s.length));
        for(int i=0;i<s.length;i++) {
            Log.d("lk",s[i]);
            sm.add(new SlideModel(s[i], ScaleTypes.FIT));
        }

        is.setImageList(sm,ScaleTypes.FIT);

    }
}
