package com.example.flixter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flixter.R.id
import android.content.res.Configuration

class NowPlayingMoviesRecyclerAdapter(
    private val movies: List<NowPlayingMovie>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<NowPlayingMoviesRecyclerAdapter.MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_now_playing_movie, parent, false)
        return MovieViewHolder(view)
    }

    /**
     * This inner class lets us refer to all the different View elements
     * (Yes, the same ones as in the XML layout files!)
     */
    inner class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: NowPlayingMovie? = null
        val mMoviePoster: ImageView = mView.findViewById<View>(id.movie_image) as ImageView
        val mMovieTitle: TextView = mView.findViewById<View>(id.movie_title) as TextView
        val mMovieOverview: TextView = mView.findViewById<View>(id.movie_overview) as TextView
            val resources = context.resources
            var image = ""
            val orientation = resources.configuration.orientation
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                image = movie.posterImageUrl
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                image = movie.backdropImageUrl
            }
            Glide.with(context).load(image).into(ivPoster)
        }
        override fun toString(): String {
            return mMovieTitle.toString() + " '" + mMovieOverview.text + "'"
        }
    }

    /**
     * This lets us "bind" each Views in the ViewHolder to its' actual data!
     */
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        holder.mItem = movie
        Glide.with(holder.mView)
            .load("https://image.tmdb.org/t/p/w342/" + movie.posterPath)
            .centerInside()
            .into(holder.mMoviePoster)
        holder.mMovieTitle.text = movie.title
        holder.mMovieOverview.text = movie.overview


        holder.mView.setOnClickListener {
            holder.mItem?.let { movie ->
                mListener?.onItemClick(movie)
            }
        }
    }

    /**
     * Remember: RecyclerView adapters require a getItemCount() method.
     * */
    override fun getItemCount(): Int {
        return movies.size
    }
}