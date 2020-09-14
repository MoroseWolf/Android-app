package c.morosewolf.moodradio;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;

public class PlayerActivity_Ambient extends AppCompatActivity {

    MediaPlayer mPlayer;
    private Button startButton, stopButton;
    SeekBar volumeControl;
    AudioManager audioManager;


    private Context mContext;
    private Activity mActivity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);


        mContext = getApplicationContext();
        mActivity = PlayerActivity_Ambient.this;

        startButton = (Button) findViewById(R.id.startPlay);
        stopButton = (Button) findViewById(R.id.stopPlay);


        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curValue = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        volumeControl = (SeekBar) findViewById(R.id.volumeControl);
        volumeControl.setMax(maxVolume);
        volumeControl.setProgress(curValue);
        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }


        });


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Отклчение клавиши Play
                startButton.setEnabled(false);
                stopButton.setEnabled(true);

                // URL аудиостанции
                String audioUrl = "https://str2b.openstream.co/390?aw_0_1st.stationid=2676&aw_0_1st.publisherId=210&aw_0_1st.serverId=str2b";

                // Инициализация нового экземпляра
                mPlayer = new MediaPlayer();

                // Задается аудио-поток, который будет использован для проигрывания
                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


                try {
                    // Задача источника аудио
                    mPlayer.setDataSource(audioUrl);

                    mPlayer.prepare();

                    // Начало проигрывания аудиостанции
                    mPlayer.start();


                    // Информирование пользователя о начале проигрывания
                    Toast.makeText(mContext, "Playing", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {

                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }

            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPlayer.isPlaying()) {
                    mPlayer.stop();
                    startButton.setEnabled(true);
                    stopButton.setEnabled(false);

                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        if(mPlayer!=null)
        {
            if(mPlayer.isPlaying())
            {
                mPlayer.stop();
            }

            mPlayer.release();
            mPlayer = null;
        }

        super.onDestroy();
    }
}
