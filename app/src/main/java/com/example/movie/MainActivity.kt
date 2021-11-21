package com.example.movie

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie.adapter.EpisodeAdapter
import com.example.movie.databinding.ActivityMainBinding
import com.example.movie.utils.DataDummy


@SuppressLint("NotifyDataSetChanged")
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = EpisodeAdapter()
        adapter.setListEpisode(DataDummy.generateEpisodes())
        adapter.notifyDataSetChanged()

        binding.episodeRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.episodeRv.adapter = adapter
        binding.episodeRv.setHasFixedSize(true)

        val intent = Intent(this@MainActivity, WatchActivity::class.java)
        binding.imageButton.setOnClickListener { startActivity(intent) }
    }
}