package com.example.movie

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.ImageView
import com.example.movie.databinding.ActivityWatchBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.util.Util

class WatchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWatchBinding

    private val defaultUrl =
        "https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4"
    private lateinit var exoPlayer: ExoPlayer
    private lateinit var btnFullScreen: ImageView

    private var currentWindow = 0
    private var playbackPosition = 0L
    private var isFullScreen = false

    private val playbackStateListener: Player.EventListener = playbackStateListener()


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWatchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnFullScreen = findViewById(R.id.bt_fullscreen)

        btnFullScreen.setOnClickListener { setFullScreen() }

        if (savedInstanceState != null) {
            with(savedInstanceState) {
                playbackPosition = getLong(PLAYBACK_POSITION, 0L)
                currentWindow = getInt(CURRENT_WINDOW_INDEX, 0)
            }
        }

        initializePlayer()
    }

    private fun initializePlayer() {

        setFullScreen()

        exoPlayer = ExoPlayer.Builder(this)
            .setSeekBackIncrementMs(5000)
            .setSeekForwardIncrementMs(5000)
            .build()

        binding.player.player = exoPlayer
        binding.player.keepScreenOn = true

        val videoSource = Uri.parse(defaultUrl)
        val mediaItem = MediaItem.fromUri(videoSource)

        with(exoPlayer) {
            setMediaItem(mediaItem)
            playWhenReady = playWhenReady
            seekTo(currentWindow, playbackPosition)
            addListener(playbackStateListener)
            prepare()
            play()
        }
    }

    private fun playbackStateListener() = object : Player.EventListener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)
            if (playbackState == Player.STATE_BUFFERING) {
                binding.progressBar.visibility = View.VISIBLE
            } else if (playbackState == Player.STATE_READY) {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setFullScreen() {
        if (!isFullScreen) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            btnFullScreen.setImageDrawable(getDrawable(R.drawable.ic_baseline_fullscreen_exit))
        } else {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            btnFullScreen.setImageDrawable(getDrawable(R.drawable.ic_baseline_fullscree))
        }
        isFullScreen = !isFullScreen
    }

    private fun releasePlayer() {
        exoPlayer.run {
            playbackPosition = this.currentPosition
            currentWindow = this.currentWindowIndex
            playWhenReady = this.playWhenReady
            removeListener(playbackStateListener)
            release()
        }
    }

    public override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }


    public override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putLong(PLAYBACK_POSITION, playbackPosition)
        outState.putInt(CURRENT_WINDOW_INDEX, currentWindow)
    }

    companion object {
        private const val CURRENT_WINDOW_INDEX = "current_window_index"
        private const val PLAYBACK_POSITION = "playback_position"
    }
}