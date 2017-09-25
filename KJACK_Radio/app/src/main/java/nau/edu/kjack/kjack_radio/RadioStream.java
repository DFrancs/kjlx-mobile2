package nau.edu.kjack.kjack_radio;

import android.media.Image;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import java.io.IOException;

public class RadioStream extends AppCompatActivity {

    private MediaPlayer player;
    private ImageButton btnRadioControl;
    private ProgressBar loadingBarRadioControl;
    //ran on start up always
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_stream);

        initializeUI();
        initializeMediaPlayer();

    }

    //sets play/pause button, play icon and play/pause btn listener
    public void initializeUI(){
        btnRadioControl = (ImageButton) findViewById(R.id.btnRadioControl);
        loadingBarRadioControl = (ProgressBar) findViewById(R.id.loadingBarRadioControl);

        btnRadioControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(player.isPlaying())){
                    setRadioControlToLoading();
                    playRadio();
                    //NOTE: setting the btnRadioControl to puase is in the OnPrepareListener of startRadio();
                }
                else if(player.isPlaying()){
                    stopRadio();
                    setRadioControlToPlay();
                }
            }
        });
    }

    /**
     * setRadioControlTo functions will check for visibility of elements before changing to
     * insure loadingbar and play/pause buttons arent visible at the same time.
     */

    private void setRadioControlToPause(){
        btnRadioControl.setVisibility(View.VISIBLE);
        loadingBarRadioControl.setVisibility(View.INVISIBLE);
        btnRadioControl.setImageDrawable(getResources().getDrawable(R.drawable.pausebutton_icon));
    }

    private void setRadioControlToPlay(){
        btnRadioControl.setVisibility(View.VISIBLE);
        loadingBarRadioControl.setVisibility(View.INVISIBLE);
        btnRadioControl.setImageDrawable(getResources().getDrawable(R.drawable.playbutton_icon));
    }

    private void setRadioControlToLoading(){
        btnRadioControl.setVisibility(View.INVISIBLE);
        loadingBarRadioControl.setVisibility(View.VISIBLE);
    }

    //reference the Android MediaPlayer Class API to understand
    private void playRadio(){
        player.prepareAsync();
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                player.start();
                setRadioControlToPause();
            }
        });
    }

    //stops radio and releases data so that when started again it starts live.
    private void stopRadio(){
        player.stop();
        player.release();
        initializeMediaPlayer();
    }

    //creates media player from streamURL
    public void initializeMediaPlayer(){
        String streamURL = "http://134.114.118.89:88/broadwavehigh.mp3";

        try{
            player = new MediaPlayer();
            player.setDataSource(streamURL);
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

}
