package com.example.karakia.ui

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.widget.*
import com.example.karakia.R

class NowPlayingActivity : AppCompatActivity() {

    private var mp: MediaPlayer?= null
    private var currentSong:MutableList<Int> =  mutableListOf(R.raw.karakia)

    lateinit var runnable: Runnable
    private var handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_now_playing)
        val  videoView = findViewById<VideoView>(R.id.videoView);
        videoView.setVideoPath("android.resource://" + packageName + "/" + R.raw.karakia);
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)
        val seekBar = findViewById<SeekBar>(R.id.seekbar);
        seekBar.progress = 0;
        seekBar.max = videoView.duration;

        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        val params = videoView.layoutParams as LinearLayout.LayoutParams
        params.width = metrics.widthPixels
        params.height = metrics.heightPixels
        params.leftMargin = 0
        videoView.layoutParams = params

        val playbtn = findViewById<ImageButton>(R.id.play_btn);
        playbtn.setOnClickListener()
        {
            if(!videoView.isPlaying)
            {
                videoView.start()
                playbtn.setImageResource(R.drawable.ic_pause);
            }
            else
            {
                videoView.pause()
                playbtn.setImageResource(R.drawable.ic_play);
            }
        }

        val backbtn = findViewById<ImageButton>(R.id.back_btn);
        backbtn.setOnClickListener()
        {
            videoView.seekTo(-5);
        }
        val nextbtn = findViewById<ImageButton>(R.id.next_btn);
        nextbtn.setOnClickListener()
        {
            videoView.seekTo(500);
        }
        val  mainView = findViewById<RelativeLayout>(R.id.main);

/*
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, pos: Int, changed: Boolean) {
                if(changed)
                {
                    videoView.seekTo(pos);
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                TODO("Not yet implemented")
            }
        })*/

        runnable = Runnable {
            seekBar.progress = videoView.currentPosition
            handler.postDelayed(runnable,1000)
        }
        handler.postDelayed(runnable,1000)
        videoView.setOnCompletionListener {
            playbtn.setImageResource(R.drawable.ic_play);
            seekBar.progress = 0;
        }



    }

}
