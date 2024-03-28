package com.example.paint

import android.content.Intent
import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.paint.PaintView.Companion.maxX
import com.example.paint.PaintView.Companion.maxY
import com.example.paint.PaintView.Companion.minX
import com.example.paint.PaintView.Companion.minY
import android.media.MediaPlayer
import java.lang.Exception


class MainActivity : ComponentActivity() {

    companion object {
        var path = Path()
        var pathLetter = Path()
        var paintBrush = Paint()
        var isDone = true
        var mediaPlayerD: MediaPlayer? = null
        var mediaPlayerG: MediaPlayer? = null
        var mediaPlayerH: MediaPlayer? = null
        var mediaPlayerB: MediaPlayer? = null



    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mediaPlayerD = MediaPlayer.create(this, R.raw.droite)
        mediaPlayerG = MediaPlayer.create(this, R.raw.gauche)
        mediaPlayerH = MediaPlayer.create(this, R.raw.haut)
        mediaPlayerB = MediaPlayer.create(this, R.raw.bas)






        val selectBtn = findViewById<ImageButton>(R.id.select)
        val selectedLetter = intent.getStringExtra("selected_letter")
        val letterDesc = findViewById<ImageButton>(R.id.description)
        val eraseBtn = findViewById<ImageButton>(R.id.erase)
        val finishBtn = findViewById<ImageButton>(R.id.finish)

        letterDesc.contentDescription = selectedLetter

        selectBtn.setOnClickListener {
            val intent = Intent(this, LetterSelection::class.java)
            startActivity(intent)

        }

        eraseBtn.setOnClickListener {
            path.reset()
        }

        finishBtn.setOnClickListener {
            isDone=true
            path.reset()
            pathLetter.reset()

            // Square frame

            /*var maxDelta = 0
            var minDelta = 0
            if (maxX - minX >= maxY - minY) {
                maxDelta = maxX - minX
                minDelta = maxY - minY
                minY -= (maxDelta - minDelta) / 2
                maxY += (maxDelta - minDelta) / 2
            } else {
                maxDelta = maxY - minY
                minDelta = maxX - minX
                minX -= (maxDelta - minDelta) / 2
                maxX += (maxDelta - minDelta) / 2
            }*/
        }
    }

}