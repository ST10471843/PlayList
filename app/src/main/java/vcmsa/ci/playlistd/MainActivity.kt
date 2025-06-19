package vcmsa.ci.playlistd

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import kotlin.system.exitProcess

/*
ST10471843
 NKHENSANI BONTLE MATHEBULA
 */


class MainActivity : AppCompatActivity() {
    //Variables
    private lateinit var etTitle: EditText
    private lateinit var etArtist: EditText
    private lateinit var etRate: EditText
    private lateinit var etComment: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnNext: Button
    private lateinit var btnExit: Button

    data class Song(val title: String, val artist: String, val rating: Int, val comments: String)

    private val playList = mutableListOf<Song>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//Initializing declarations
        etTitle = findViewById(R.id.etTitle)
        etArtist = findViewById(R.id.etArtist)
        etRate = findViewById(R.id.etRate)
        etComment = findViewById(R.id.etComment)
        btnAdd = findViewById(R.id.btnAdd)
        btnNext = findViewById(R.id.btnNext)
        btnExit = findViewById(R.id.btnExit)

        btnAdd.setOnClickListener {
            showAddItemDialog()
        }

//to allow the user to change to the next screen
        btnNext.setOnClickListener {
            val intent = Intent(this, Rating::class.java)
            startActivity(intent)
        }
//to allow user to exit the app
        btnExit.setOnClickListener {
            finishAffinity()
            exitProcess(0)

        }
    }
    //im trying to store without displaying as yet
    private fun showAddItemDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add New Song")
//variables
        val view = layoutInflater.inflate(R.layout.dialog_add_item, null)
        val etTitle: EditText = view.findViewById(R.id.etTitle)
        val etArtist: EditText = view.findViewById(R.id.etArtist)
        val etRate: EditText = view.findViewById(R.id.etRate)
        val etComment: EditText = view.findViewById(R.id.etComment)

        builder.setView(view)

        builder.setPositiveButton("Add") { dialog, _ ->
            val title = etTitle.text.toString().trim()
            val artist = etArtist.text.toString().trim()
            val ratingStr = etRate.text.toString().trim()
            val comments = etComment.text.toString().trim()


            //error handling
            if (title.isEmpty() || artist.isEmpty() || ratingStr.isEmpty() || comments.isEmpty()) {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Title, artist, rating and comment cannot be empty.",
                    Snackbar.LENGTH_SHORT
                ).show()
                return@setPositiveButton
            }

            val rating = ratingStr.toIntOrNull()
            if (rating == null || rating < 1 || rating > 5) {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Invalid rating. Please enter a number between 1 and 5.",
                    Snackbar.LENGTH_SHORT
                ).show()
                return@setPositiveButton
            }

            playList.add(Song(title, artist, rating, comments))
            Snackbar.make(findViewById(android.R.id.content),
                "$title added to the playlist.",
                Snackbar.LENGTH_SHORT).show()
            // The list will be used on the next screen.
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }
}