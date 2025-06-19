package vcmsa.ci.playlistd

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat



class Rating : AppCompatActivity() {

    //variables
    private lateinit var tvDisplay: TextView
    private lateinit var btnReview: Button
    private lateinit var tvAverage: TextView
    private lateinit var btnAverage: Button
    private lateinit var btnBack: Button

//array declaration

    data class Song(val title: String, val artist: String, val rating: String, val comments: String)
    val songs = mutableListOf<Song>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rating)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        tvDisplay = findViewById(R.id.tvDisplay)
        btnReview = findViewById(R.id.btnReview)
        tvAverage = findViewById(R.id.tvAverage)
        btnAverage = findViewById(R.id.btnAverage)
        btnBack = findViewById(R.id.btnBack)

        btnReview.setOnClickListener {
            displayPlaylist()

        }

        btnAverage.setOnClickListener {
           val average= displayAverage()
            tvAverage.text= "Average Rating: %.2f".format(average)

        }
// to allow the user back to the main screen
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
    // to display everything the User has inputted
    // Define this outside of your function, at the class level or top of your file

    private fun displayPlaylist() {
        if (songs.isNotEmpty()) {
            val stringBuilder = StringBuilder()
            for (song in songs) {
                stringBuilder.append("Song Title: ${song.title}\n")
                stringBuilder.append("Artist Name: ${song.artist}\n")
                stringBuilder.append("Rating: ${song.rating}\n")
                stringBuilder.append("Comments: ${song.comments}\n\n")
            }
            tvDisplay.text = stringBuilder.toString()
        } else {
            tvDisplay.text = getString(R.string.add_songs_to_playlist)
        }
    }


    //Function to display Average ratings

    private fun displayAverage(): Double {
        if (songs.isEmpty()) return 0.0

        var totalRating = 0.0
        var count = 0
        for (song in songs) {
            val rate = song.rating.toDoubleOrNull()
            if (rate != null) {
                totalRating += rate
                count++
            }
        }
        return if (count > 0) totalRating / count else 0.0
    }
}












