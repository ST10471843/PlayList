package vcmsa.ci.playlistd

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar


class Rating : AppCompatActivity() {
    //variables
    private lateinit var tvDisplay: TextView
    private lateinit var btnReview: Button
    private lateinit var tvAverage: TextView
    private lateinit var btnAverage: Button
    private lateinit var btnBack: Button
//array declaration
    private val title = mutableListOf<String>()
    private val artist = mutableListOf<String>()
    private val rating = mutableListOf<Int>()
    private val comments = mutableListOf<String>()

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
           displayAverage()

        }
// to allow the user back to the main screen
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
    // to display everything the User has inputted
    // Define this outside of your function, at the class level or top of your file
    data class Song(val title: String, val artist: String, val rating: String, val comments: String)

    // Also at the class level, keep your playlist here
    val songs = mutableListOf<Song>()

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
        if (rating.isEmpty()) return 0.0

        var totalRating = 0
        for (i in rating.indices) {
            totalRating += rating[i]
        }
        return totalRating.toDouble() / rating.size
    }

}









