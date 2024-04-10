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
import com.example.paint.PaintView.Companion.timer
import java.lang.Exception
import kotlin.collections.MutableList
import kotlin.math.ln



class MainActivity : ComponentActivity() {

    fun Reset(){
        isDone=true
        path.reset()
        pathLetter.reset()
        nbCheckpoint=0
        drawOnce=true
        didOnce=false
    }

    companion object {
        var path = Path()
        var listeCheckpoints: MutableList<Checkpoint> = mutableListOf()
        var pathLetter = Path()
        var pathLetter2 = Path()
        var paintBrush = Paint()
        var isDone = true
        var mediaPlayerD: MediaPlayer? = null
        var mediaPlayerG: MediaPlayer? = null
        var mediaPlayerH: MediaPlayer? = null
        var mediaPlayerB: MediaPlayer? = null
        var mediaPlayerC: MediaPlayer? = null
        var mediaPlayerLT: MediaPlayer? = null
        var selectedLetter : String? = null
        var nbCheckpoint =0
        var drawOnce =true
        var didOnce = false





    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mediaPlayerD = MediaPlayer.create(this, R.raw.droite)
        mediaPlayerG = MediaPlayer.create(this, R.raw.gauche)
        mediaPlayerH = MediaPlayer.create(this, R.raw.haut)
        mediaPlayerB = MediaPlayer.create(this, R.raw.bas)
        mediaPlayerC = MediaPlayer.create(this, R.raw.correctsound)
        mediaPlayerLT = MediaPlayer.create(this, R.raw.letterdone)








        val selectBtn = findViewById<ImageButton>(R.id.select)
        selectedLetter = intent.getStringExtra("selected_letter")
        val letterDesc = findViewById<ImageView>(R.id.description)
        val eraseBtn = findViewById<ImageButton>(R.id.erase)
        val finishBtn = findViewById<ImageButton>(R.id.finish)

        val dicoLettreDesc = mutableMapOf<String, String>()
        dicoLettreDesc["A"] = "Placez vous dans la partie basse gauche de l’écran. Orientez-vous à 1 heure. Tracez un trait long. Orientez vous à 5 heure. Tracez un trait long. Revenez de la moitié du trait. Orientez vous à 9 heure. tracez un trait court."
        dicoLettreDesc["B"] = "Placez vous dans la partie basse gauche de l’écran. Orientez vous à 0 heure. Tracez un trait long. Tracez un arc de cercle allant jusqu’à la moitié du trait précédent et orienté vers la droite. Répétez l’opération en arrêtant cette fois l’arc sur la fin de ce trait."
        dicoLettreDesc["C"] = "Placez vous dans la partie haute de l’écran. Tracez un arc de cercle orienté vers la gauche qui, si il y avait un trait long, partirait du début de ce trait et s’arreterait sur la fin de ce trait."
        dicoLettreDesc["D"] = "Placez vous dans la partie basse de l’écran. Orientez vous à 0h. Tracez un trait long. Réalisez un arc de cercle orienté vers la droite allant jusqu’à la fin du trait précédent."
        dicoLettreDesc["E"] = "Placez vous dans la partie haute de l’écran. Orientez vous à 9h. Tracez un trait moyen. Orientez vous à 6h. Tracez un trait long. Orientez vous à 3h. Tracez un trait moyen. Orientez vous à 9h. Avancez d’une longueur moyenne. Orientez vous à 3h. Tracez un trait moyen. Revenez sans écrire au centre de votre barre verticale. Orientez vous à 3h. Tracez un trait moyen."
        dicoLettreDesc["HorizLine"] = "Ceci est un dessin de calibration pour faire une ligne horizontale de la largeur des lettres que vous aurez à déssiner. Placez vous à gauche de l'écran. Orientez vous à 3 heure et tracez un trait."
        dicoLettreDesc["VertLine"] = "Ceci est un dessin de calibration pour faire une ligne verticale de la largeur des lettres que vous aurez à déssiner. Placez vous en bas de l'écran. Orientez vous à 0 heure et tracez un trait."

        if(selectedLetter !=null)
            letterDesc.contentDescription = dicoLettreDesc[selectedLetter] + "Désactivez le talkback pour dessiner et réactivez le une fois le dessin terminé pour appuyer sur le bouton valider afin de connaître votre score."

        selectBtn.setOnClickListener {
            Reset()
            listeCheckpoints.clear()
            val intent = Intent(this, LetterSelection::class.java)
            startActivity(intent)

        }

        eraseBtn.setOnClickListener {
            path.reset()
        }

        finishBtn.setOnClickListener {

            Reset()

            var base =100
            for (checkpoint in listeCheckpoints) {
                if (!checkpoint.passe) {
                    base -= 20
                }
            }
            println(base)
            var score = (base-timer/30.0).toInt()
            if(score<0)
                score=0
            finishBtn.contentDescription ="Valider, Dernier score : $score"
            listeCheckpoints.clear()

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