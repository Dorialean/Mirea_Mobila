package ru.mirea.lukutin.mirresproject.ui.hardware

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Environment
import android.os.IBinder
import java.io.File


class HardwareService : Service() {
    private var mediaPlayer: MediaPlayer? = null
    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onCreate() {
        val state = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state) {
            var sdPath = Environment.getExternalStorageDirectory()
            sdPath =
                File(sdPath.absolutePath + "/Android/data/ru.mirea.jukov.mireaproject/files/Music")
            val audioFile = File(sdPath, "rec_audio.mp3")
            mediaPlayer = MediaPlayer.create(this, Uri.fromFile(audioFile))
            mediaPlayer?.isLooping = true
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        mediaPlayer!!.start()
        return START_STICKY
    }

    override fun onDestroy() {
        mediaPlayer!!.stop()
    }
}