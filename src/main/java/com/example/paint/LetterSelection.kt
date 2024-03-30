package com.example.paint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
class LetterSelection : AppCompatActivity() {

    fun SwitchLetter(l: String){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("selected_letter", l) // Pass the selected letter as an extra parameter
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.letter_selection)

        val buttonA = findViewById<Button>(R.id.A)
        val buttonB = findViewById<Button>(R.id.B)
        val buttonC = findViewById<Button>(R.id.C)
        val buttonD = findViewById<Button>(R.id.D)
        val buttonE = findViewById<Button>(R.id.E)
        val buttonG = findViewById<Button>(R.id.G)


        // Set OnClickListener for button A
        buttonA.setOnClickListener {
            SwitchLetter("A");

        }

        // Set OnClickListener for button B
        buttonB.setOnClickListener {
            SwitchLetter("B");

        }

        buttonC.setOnClickListener {
            SwitchLetter("C");

        }

        buttonD.setOnClickListener {
            SwitchLetter("D");

        }

        buttonE.setOnClickListener {
            SwitchLetter("E");

        }
        buttonG.setOnClickListener {
            SwitchLetter("G");

        }

        // Add OnClickListener for other buttons as needed
    }
}