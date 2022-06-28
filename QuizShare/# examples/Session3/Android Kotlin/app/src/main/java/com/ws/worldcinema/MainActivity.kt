package com.ws.worldcinema

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ws.worldcinema.DialogManager.showErrorDialog
import com.ws.worldcinema.MainActivity
import com.ws.worldcinema.MainActivity.LandscapeMovieAdapter.LandscapeMovieViewHolder
import com.ws.worldcinema.MainActivity.PortraitMovieAdapter.PortraitMovieViewHolder
import com.ws.worldcinema.databinding.ActivityMainBinding
import com.ws.worldcinema.model.Cover
import com.ws.worldcinema.model.Movie
import com.ws.worldcinema.model.User
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.util.*

class MainActivity : AppCompatActivity() {
    interface OnMovieClickListener {
        fun onMovieClick(movie: Movie)
    }

    private var binding: ActivityMainBinding? = null
    private var coverMovieId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        setContentView(view)
        val service = RetrofitSingleton().service
        val call2 = service!!.getCover(RetrofitSingleton.Companion.getToken())
        call2!!.enqueue(object : Callback<Cover?> {
            override fun onResponse(call: Call<Cover?>, response: Response<Cover?>) {
                Glide.with(this@MainActivity).load(ImageHelper.getImagePath(response.body()!!.backgroundImage)).into(binding!!.include2.imageView4)
                Glide.with(this@MainActivity).load(ImageHelper.getImagePath(response.body()!!.foregroundImage)).into(binding!!.include2.imageView8)
                coverMovieId = response.body()!!.movieId
            }

            override fun onFailure(call: Call<Cover?>, t: Throwable) {
                DialogManager.showErrorDialog(this@MainActivity, "Ошибка от сервера", t.localizedMessage)
            }
        })
        binding!!.include3.recyclerView3.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding!!.include3.recyclerView3.adapter = PortraitMovieAdapter()
        (binding!!.include3.recyclerView3.adapter as PortraitMovieAdapter?)!!.setOnClickListener(object : OnMovieClickListener {
            override fun onMovieClick(movie: Movie) {
                val intent = Intent(this@MainActivity, MovieActivity::class.java)
                intent.putExtra(Movie.Companion.MOVIE_ID_KEY, movie.movieId)
                startActivity(intent)
            }
        })
        val call3 = service.getMovies("inTrend")
        call3!!.enqueue(object : Callback<ArrayList<Movie>?> {

            override fun onFailure(call: Call<ArrayList<Movie>?>, t: Throwable) {
                DialogManager.showErrorDialog(this@MainActivity, "Ошибка от сервера", t.localizedMessage)
            }

            override fun onResponse(
                call: Call<ArrayList<Movie>?>,
                response: Response<ArrayList<Movie>?>
            ) {
                (binding!!.include3.recyclerView4.adapter as PortraitMovieAdapter?)?.setupMovies(response.body()!!)
            }
        })
        binding!!.include3.recyclerView4.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding!!.include3.recyclerView4.adapter = LandscapeMovieAdapter()
        (binding!!.include3.recyclerView4.adapter as LandscapeMovieAdapter?)!!.setOnClickListener(object : OnMovieClickListener {
            override fun onMovieClick(movie: Movie) {
                val intent = Intent(this@MainActivity, MovieActivity::class.java)
                intent.putExtra(Movie.Companion.MOVIE_ID_KEY, movie.movieId + 1)
                startActivity(intent)
            }
        })
        val call4 = service.getMovies("new")
        call4!!.enqueue(object : Callback<ArrayList<Movie>?> {
            override fun onResponse(call: Call<ArrayList<Movie>?>, response: Response<ArrayList<Movie>?>) {
                (binding!!.include3.recyclerView4.adapter as LandscapeMovieAdapter?)!!.setupMovies(response?.body()!!)
            }

            override fun onFailure(call: Call<ArrayList<Movie>?>, t: Throwable) {
                DialogManager.showErrorDialog(this@MainActivity, "Ошибка от сервера", t.localizedMessage)
            }
        })
        binding!!.include3.recyclerView5.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding!!.include3.recyclerView5.adapter = PortraitMovieAdapter()
        (binding!!.include3.recyclerView5.adapter as PortraitMovieAdapter?)!!.setOnClickListener(object : OnMovieClickListener {
            override fun onMovieClick(movie: Movie) {
                val intent = Intent(this@MainActivity, MovieActivity::class.java)
                intent.putExtra(Movie.Companion.MOVIE_ID_KEY, movie.movieId - 1)
                startActivity(intent)
            }
        })
        val call5 = service.getMovies("forMe")
        call5!!.enqueue(object : Callback<ArrayList<Movie>?> {
            override fun onResponse(call: Call<ArrayList<Movie>?>, response: Response<ArrayList<Movie>?>) {
                (binding!!.include3.recyclerView4.adapter as PortraitMovieAdapter?)!!.movies = response.body()!!
            }

            override fun onFailure(call: Call<ArrayList<Movie>?>, t: Throwable) {
                DialogManager.showErrorDialog(this@MainActivity, "Ошибка от сервера", t.localizedMessage)
            }
        })
        val call6 = service.getProfile(RetrofitSingleton.Companion.getToken())
        call6!!.enqueue(object : Callback<User?> {
            override fun onResponse(call: Call<User?>, response: Response<User?>) {
                if (response.body() != null && response!!.body()?.avatarId != null) {
                    Glide.with(this@MainActivity).load(ImageHelper.getImagePath(response!!.body()?.avatarId)).into(binding!!.include.imageView3)
                    binding!!.include.textView.text = response!!.body()?.firstName + " " + response!!.body()?.lastName
                    binding!!.include.textView2.text = response!!.body()?.email
                }
            }

            override fun onFailure(call: Call<User?>, t: Throwable) {
                DialogManager.showErrorDialog(this@MainActivity, "Ошибка от сервера", t.localizedMessage)
            }
        })
        binding!!.include.changeButton.setOnClickListener {
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle("Choose your profile picture")
            val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
            builder.setItems(options) { dialog, item ->
                if (options[item] == "Take Photo") {
                    val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(takePicture, 0)
                } else if (options[item] == "Choose from Gallery") {
                    val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(pickPhoto, 1)
                } else if (options[item] == "Cancel") {
                    dialog.dismiss()
                }
            }
            builder.show()
        }
        binding!!.button8.setOnClickListener {
            RetrofitSingleton.Companion.setToken(null)
            finish()
        }
        val call7 = service.getUserMovies(RetrofitSingleton.Companion.getToken(), "lastView")
        call7!!.enqueue(object : Callback<ArrayList<Movie>?> {
            override fun onResponse(call: Call<ArrayList<Movie>?>, response: Response<ArrayList<Movie>?>) {
                if (response.body() != null && response.body()!!.size > 0) {
                    binding!!.include.lastViewName.text = response.body()!![0].name
                    Glide.with(this@MainActivity).load(ImageHelper.getImagePath(response.body()!![0].poster)).into(binding!!.include.lastViewPoster)
                    binding!!.include.lastViewPoster.setOnClickListener {
                        val intent = Intent(this@MainActivity, MovieActivity::class.java)
                        intent.putExtra(Movie.Companion.MOVIE_ID_KEY, response.body()!![0].movieId)
                        startActivity(intent)
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<Movie>?>, t: Throwable) {
                DialogManager.showErrorDialog(this@MainActivity, "Ошибка от сервера", t.localizedMessage)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_CANCELED) {
            when (requestCode) {
                0 -> if (resultCode == Activity.RESULT_OK && data != null) {
                    val selectedImage = data.extras!!["data"] as Bitmap?
                    try {
                        sendAvatar(selectedImage)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
                1 -> if (resultCode == Activity.RESULT_OK && data != null) {
                    val pickedImage = data.data
                    var bitmap: Bitmap? = null
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(
                            this.contentResolver, pickedImage)
                    } catch (e: Exception) {
                    }

                    try {
                        sendAvatar(bitmap)
                    } catch (e: IOException) {
                        e.printStackTrace()

                    }
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun sendAvatar(bitmap: Bitmap?) {
        val service = RetrofitSingleton().service
        val f = File(cacheDir, "avatar")
        f.createNewFile()
        val bos = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos)
        val bitmapdata = bos.toByteArray()
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(f)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        try {
            fos!!.write(bitmapdata)
            fos.flush()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val map: MutableMap<String, RequestBody> =
            HashMap()
        map["token"] = RequestBody.create(MediaType.parse("text/plain"), RetrofitSingleton.tokenWithoutBarrier!!)

        val fileBody = RequestBody.create(MediaType.parse("image/png"), f)
        map["file\"; filename=\"avatar.png\""] = fileBody

        val call: Call<ArrayList<User>> =
            service.editUser(map)
        call.enqueue(object :
            Callback<ArrayList<User>?> {
            override fun onResponse(
                call: Call<ArrayList<User>?>,
                response: Response<ArrayList<User>?>
            ) {
                Log.v("Upload", "success")
            }

            override fun onFailure(
                call: Call<ArrayList<User>?>,
                t: Throwable
            ) {
                showErrorDialog(
                    this@MainActivity,
                    "Ошибка от сервера",
                    t.localizedMessage
                )
            }
        })
    }

    inner class PortraitMovieAdapter : RecyclerView.Adapter<PortraitMovieViewHolder>() {
        var movies: ArrayList<Movie>? = ArrayList<Movie>()
        private var onClickListener: OnMovieClickListener? = null

        fun setupMovies(movies: ArrayList<Movie>?) {
            this.movies = movies
            notifyDataSetChanged()
        }

        fun setOnClickListener(onClickListener: OnMovieClickListener?) {
            this.onClickListener = onClickListener
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortraitMovieViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_film, parent, false)
            return PortraitMovieViewHolder(view)
        }

        override fun onBindViewHolder(holder: PortraitMovieViewHolder, position: Int) {
            Glide.with(this@MainActivity).load(ImageHelper.getImagePath(movies?.get(position)?.poster)).into(holder.poster)
            holder.itemView.setOnClickListener { if (onClickListener != null) movies?.get(position)?.let { it1 ->
                onClickListener!!.onMovieClick(
                    it1
                )
            }
            }
        }

        override fun getItemCount(): Int {
            return movies?.size ?: 0
        }

        inner class PortraitMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val poster: ImageView

            init {
                poster = itemView.findViewById(R.id.poster)
            }
        }
    }

    inner class LandscapeMovieAdapter : RecyclerView.Adapter<LandscapeMovieViewHolder>() {
        private var movies: ArrayList<Movie>? = ArrayList<Movie>()
        private var onClickListener: OnMovieClickListener? = null
        fun setupMovies(movies: ArrayList<Movie>?) {
            this.movies = movies
            notifyDataSetChanged()
        }

        fun setOnClickListener(onClickListener: OnMovieClickListener?) {
            this.onClickListener = onClickListener
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LandscapeMovieViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_film_landscape, parent, false)
            return LandscapeMovieViewHolder(view)
        }

        override fun onBindViewHolder(holder: LandscapeMovieViewHolder, position: Int) {
            Glide.with(this@MainActivity).load(ImageHelper.getImagePath(this!!.movies?.get(position)?.poster)).into(holder.poster)
            holder.itemView.setOnClickListener { if (onClickListener != null) movies?.get(position)
                ?.let { it1 ->
                onClickListener!!.onMovieClick(
                    it1
                )
            } }
        }

        override fun getItemCount(): Int {
            return movies?.size ?: 0
        }

        inner class LandscapeMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val poster: ImageView = itemView.findViewById(R.id.poster)

        }
    }
}