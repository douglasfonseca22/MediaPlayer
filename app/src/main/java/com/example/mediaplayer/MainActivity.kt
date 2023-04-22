package com.example.mediaplayer

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener

class MainActivity : AppCompatActivity() {

    private lateinit var btnPlay: Button
    private lateinit var btnPause: Button
    private lateinit var btnStop: Button
    private lateinit var seekBar: SeekBar
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var audioManager: AudioManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPlay = findViewById(R.id.btnPlay)
        btnPause = findViewById(R.id.btnPause)
        btnStop = findViewById(R.id.btnStop)
        seekBar = findViewById(R.id.seekBar)

        mediaPlayer = MediaPlayer.create(application, R.raw.bach)
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        val volumeMaximo = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        seekBar.max = volumeMaximo

        val volumeAtual = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        seekBar.progress = volumeAtual

        seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                audioManager.setStreamVolume(
                    AudioManager.STREAM_MUSIC,
                    progress,
                    AudioManager.FLAG_SHOW_UI
                )
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Do Nothing
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Do Nothing
            }
        })

        btnPlay.setOnClickListener {
            if (mediaPlayer != null) {
                mediaPlayer.start()
            }
        }

        btnPause.setOnClickListener {
            if (mediaPlayer != null && mediaPlayer.isPlaying) {
                mediaPlayer.pause()
            }
        }

        btnStop.setOnClickListener {
            if (mediaPlayer != null && mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.prepare()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (mediaPlayer != null && mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }

    override fun onStop() {
        super.onStop()
        if (mediaPlayer != null && mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
    }
}