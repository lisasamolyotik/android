package com.example.lab3.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.example.lab3.R

class SongService : Service() {

    companion object {

        private var instance: MediaPlayer? = null
        fun getInstance(context: Context): MediaPlayer {
            if (instance == null) {
                instance = MediaPlayer.create(context, R.raw.shark)
            }

            return instance!!
        }
    }

    inner class SongBinder: Binder() {
        val service: SongService
            get() = this@SongService
    }

    override fun onBind(intent: Intent?): IBinder? {
       // mediaPlayer = getInstance(applicationContext)
        return SongBinder()
    }

    fun playSong() {
        getInstance(applicationContext).start()
    }

    fun pauseSong() {
        getInstance(applicationContext).pause()
    }

    fun stopSong() {
        getInstance(applicationContext).pause()
        getInstance(applicationContext).seekTo(0)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        getInstance(applicationContext).start()
        return super.onStartCommand(intent, flags, startId)
    }
}