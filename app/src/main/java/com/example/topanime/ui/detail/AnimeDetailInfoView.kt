package com.example.topanime.ui.detail

import android.content.Context
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.buildSpannedString
import com.example.topanime.R
import com.example.topanime.domain.Anime

class AnimeDetailInfoView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : AppCompatTextView(context, attrs, defStyleAttr) {

    fun setAnimeInfo(anime: Anime?) {
        text = buildSpannedString {
            appendAnimeDetail(R.string.source, anime?.source)
            appendAnimeDetail(R.string.year, anime?.year?.toString())
            appendAnimeDetail(R.string.status, anime?.status)
            appendAnimeDetail(R.string.episodes, anime?.episodes?.toString())
            appendAnimeDetail(R.string.duration, anime?.duration)
            appendAnimeDetail(R.string.score, anime?.score.toString())
            appendAnimeDetail(R.string.rank, anime?.rank.toString())
            appendAnimeDetail(R.string.popularity, anime?.popularity.toString())
            appendAnimeDetail(R.string.favorites, anime?.favorites.toString())
            appendAnimeDetail(R.string.season, anime?.season)
            appendAnimeDetail(R.string.studio, anime?.studios)
            appendAnimeDetail(R.string.genres, anime?.genre)
            }
    }

    private fun SpannableStringBuilder.appendAnimeDetail(stringRes: Int, value : String?){
        value?.let {
            append(context.getString(stringRes))
            append(": ")
            appendLine(value)
        }
    }
}