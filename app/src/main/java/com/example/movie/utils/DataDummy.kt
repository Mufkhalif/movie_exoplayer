package com.example.movie.utils

import com.example.movie.models.Episode

object DataDummy {

    fun generateEpisodes(): List<Episode> {
        val episodes = ArrayList<Episode>();

        episodes.add(
            Episode("Episode -1", "1", "The end of the spoot", "")
        )

        episodes.add(
            Episode("Episode -2", "2", "The end of the spoot", "")
        )

        episodes.add(
            Episode("Episode -3", "3", "The end of the spoot", "")
        )

        episodes.add(
            Episode("Episode -3", "3", "The end of the spoot", "")
        )

        episodes.add(
            Episode("Episode -3", "3", "The end of the spoot", "")
        )

        episodes.add(
            Episode("Episode -3", "3", "The end of the spoot", "")
        )

        return episodes
    }
}