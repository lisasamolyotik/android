package com.example.lab3.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.example.lab3.R

class SongService : Service() {

    companion object {
        var mediaPlayer: MediaPlayer? = null

        const val STATUS_TAG = "STATUS"
        const val START_TAG = 0
        const val PAUSE_TAG = 1
        const val RESUME_TAG = 2
        const val STOP_TAG = 3
    }

    inner class SongBinder: Binder() {
        val service: SongService
            get() = this@SongService
    }

    override fun onBind(intent: Intent?): IBinder? {
        mediaPlayer = MediaPlayer.create(applicationContext, R.raw.shark)
        return SongBinder()
    }

    fun playSong() {
        mediaPlayer!!.start()
    }

    fun pauseSong() {
        mediaPlayer!!.pause()
    }

    fun stopSong() {
        mediaPlayer!!.pause()
        mediaPlayer!!.seekTo(0)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer!!.start()
        return super.onStartCommand(intent, flags, startId)
    }
}