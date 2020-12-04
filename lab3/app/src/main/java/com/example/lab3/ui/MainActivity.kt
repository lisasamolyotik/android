package com.example.lab3.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.example.lab3.R
import com.example.lab3.service.SongService

class MainActivity : AppCompatActivity() {
    private var songService: SongService? = null
    private var isBound = false
    private var isPlayed = false

    companion object {
        private const val BOUND_KEY = "is_bound"
        private const val PLAYED_KEY = "is_played"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            isBound = savedInstanceState.getBoolean(BOUND_KEY)
            isPlayed = savedInstanceState.getBoolean(PLAYED_KEY)
        }

        Intent(this, SongService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.menu_play)?.isEnabled = !isPlayed
        menu?.findItem(R.id.menu_pause)?.isEnabled = isPlayed
        menu?.findItem(R.id.menu_stop)?.isEnabled = isPlayed
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_play -> {
                if (isBound) {
                    songService?.playSong()
                    isPlayed = true
                }
                true
            }
            R.id.menu_pause -> {
                songService?.pauseSong()
                isPlayed = false
                true
            }
            R.id.menu_stop -> {
                songService?.stopSong()
                isPlayed = false
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(BOUND_KEY, isBound)
        outState.putBoolean(PLAYED_KEY, isPlayed)
    }

    override fun onStop() {
        super.onStop()
        if (isBound) {
            unbindService(connection)
            isBound = false
        }
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as SongService.SongBinder
            songService = binder.service
            isBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            isBound = false
        }
    }
}