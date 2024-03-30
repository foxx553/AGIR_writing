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
import android.widget.ImageView
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
        var selectedLetter : String? = null



    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mediaPlayerD = MediaPlayer.create(this, R.raw.droite)
        mediaPlayerG = MediaPlayer.create(this, R.raw.gauche)
        mediaPlayerH = MediaPlayer.create(this, R.raw.haut)
        mediaPlayerB = MediaPlayer.create(this, R.raw.bas)






        val selectBtn = findViewById<ImageButton>(R.id.select)
        selectedLetter = intent.getStringExtra("selected_letter")
        val letterDesc = findViewById<ImageView>(R.id.description)
        val eraseBtn = findViewById<ImageButton>(R.id.erase)
        val finishBtn = findViewById<ImageButton>(R.id.finish)

        val dicoLettreDesc = mutableMapOf<String, String>()
        dicoLettreDesc["A"] = "Placez vous dans la partie basse gauche de l’écran. Orientez-vous à 1h. Tracez un trait long. Orientez vous à 5h. Tracez un trait long. Revenez de la moitié du trait. Orientez vous à 9h. tracez un trait court."
        dicoLettreDesc["B"] = "Placez vous dans la partie basse gauche de l’écran. Orientez vous à 0h. Tracez un trait long. Tracez un arc de cercle allant jusqu’à la moitié du trait précédent et orienté vers la droite. Répétez l’opération en arrêtant cette fois l’arc sur la fin de ce trait."
        dicoLettreDesc["C"] = "Placez vous dans la partie haute de l’écran. Tracez un arc de cercle orienté vers la gauche qui, si il y avait un trait long, partirait du début de ce trait et s’arreterait sur la fin de ce trait."
        dicoLettreDesc["D"] = "Placez vous dans la partie basse de l’écran. Orientez vous à 0h. Tracez un trait long. Réalisez un arc de cercle orienté vers la droite allant jusqu’à la fin du trait précédent."
        dicoLettreDesc["E"] = "Placez vous dans la partie haute de l’écran. Orientez vous à 9h. Tracez un trait moyen. Orientez vous à 6h. Tracez un trait long. Orientez vous à 3h. Tracez un trait moyen. Orientez vous à 9h. Avancez d’une longueur moyenne. Orientez vous à 3h. Tracez un trait moyen. Revenez sans écrire au centre de votre barre verticale. Orientez vous à 3h. Tracez un trait moyen."

        if(selectedLetter !=null)
            letterDesc.contentDescription = dicoLettreDesc[selectedLetter] + "Désactivez le talkback pour dessiner et réactivez le une fois le dessin terminé pour appuyer sur le bouton valider."

        selectBtn.setOnClickListener {
            isDone=true
            path.reset()
            pathLetter.reset()
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