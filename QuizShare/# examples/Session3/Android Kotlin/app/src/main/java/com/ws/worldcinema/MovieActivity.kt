package com.ws.worldcinema

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ws.worldcinema.MovieActivity
import com.ws.worldcinema.databinding.ActivityMovieBinding
import com.ws.worldcinema.model.Episode
import com.ws.worldcinema.model.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MovieActivity : AppCompatActivity() {
    private var binding: ActivityMovieBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        setContentView(view)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val movieId = intent.getIntExtra(Movie.Companion.MOVIE_ID_KEY, 0)
        if (movieId == 0) {
            DialogManager.showErrorDialog(this, "Ошибка", "Фильм не найден")
        } else {
            requestMovie(movieId)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun requestMovie(movieId: Int) {
        val service = RetrofitSingleton().service
        val movieCall = service!!.getMovie(movieId, RetrofitSingleton.Companion.getToken())
        movieCall!!.enqueue(object : Callback<Movie?> {
            override fun onResponse(call: Call<Movie?>, response: Response<Movie?>) {
                setupScreen(response.body())
                requestEpisodes(response.body()!!.movieId)
            }

            override fun onFailure(call: Call<Movie?>, t: Throwable) {
                DialogManager.showErrorDialog(this@MovieActivity, "Ошибка от сервера", t.localizedMessage)
            }
        })
    }

    private fun setupScreen(movie: Movie?) {
        title = movie!!.name
        binding!!.movieDescriptionTextView.text = movie!!.description
        binding!!.ageTextView.text = movie!!.getAgeInFormat()
        binding!!.ageTextView.setTextColor(movie.ageColor)
        binding!!.imagesRecyclerView.adapter = MovieFramesAdapter()
        binding!!.imagesRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        (binding!!.imagesRecyclerView.adapter as MovieFramesAdapter?)?.setMovieFrames(movie.images)
        Glide.with(this).load(ImageHelper.getImagePath(movie.poster)).into(binding!!.posterImageView)
    }

    private fun requestEpisodes(movieId: Int) {
        val service = RetrofitSingleton().service
        val call = service!!.getMovieEpisodes(movieId)
        call!!.enqueue(object : Callback<ArrayList<Episode>?> {
            override fun onResponse(call: Call<ArrayList<Episode>?>, response: Response<ArrayList<Episode>?>) {
                setupEpisodes(response.body())
            }

            override fun onFailure(call: Call<ArrayList<Episode>?>, t: Throwable) {
                DialogManager.showErrorDialog(this@MovieActivity, "Ошибка от сервера", t.localizedMessage)
            }
        })
    }

    private fun setupEpisodes(episodes: ArrayList<Episode>?) {
        binding!!.episodesRecyclerView.adapter = EpisodesAdapter()
        binding!!.episodesRecyclerView.layoutManager = LinearLayoutManager(this)
        (binding!!.episodesRecyclerView.adapter as EpisodesAdapter?)!!.setupEpisodes(episodes)
    }

    //TODO: Перенести в окно чата, когда оно появится
    fun chatFieldValidate(message: String?): Boolean {
        return message != null && message.isNotEmpty()
    }

    fun showErrorDialog(title: String?, message: String?) {
        AlertDialog.Builder(ContextThemeWrapper(this, R.style.DarkDialogStyle))
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton("Ok") { dialog: DialogInterface?, which: Int -> }.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_CANCELED) {
            when (requestCode) {
                0  if (resultCode == Activity.RESULT_OK && data != null) {
                    val selectedImage = data.extras!!["data"] as Bitmap?
                }
                1 -> if (resultCode == Activity.RESULT_OK && data != null) {
                    val selectedImage = data.data
                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                    if (selectedImage != null) {
                        val cursor = contentResolver.query(selectedImage,
                                filePathColumn, null, null, null)
                        if (cursor != null) {
                            cursor.moveToFirst()
                            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                            val picturePath = cursor.getString(columnIndex)
                            cursor.close()
                        }
                    }
                }
            }
        }
    }
}