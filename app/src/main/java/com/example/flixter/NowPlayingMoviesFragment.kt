package com.example.flixter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView.OnScrollListener
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONArray


class NowPlayingMoviesFragment : Fragment(), OnListFragmentInteractionListener {
    private lateinit var progressBar: ContentLoadingProgressBar
    private lateinit var recyclerView : RecyclerView
    private val apiKey = "5502e1574702c422bf438431d56a2184"
    private var movies: MutableList<NowPlayingMovie> = mutableListOf()
    private var page = 1

    /*
    * What happens when a particular movie is clicked.
    */
    override fun onItemClick(item: NowPlayingMovie) {
        Toast.makeText(context, item.movieId.toString() + ": " + item.title, Toast.LENGTH_LONG).show()
    }

    /*
    * Constructing the view
    */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_now_playing_movies_list, container, false)
        progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = LinearLayoutManager(context)
        Log.d("NowPlayingMoviesFragment", "Called updateAdapter ")

        updateAdapter(progressBar, recyclerView)

        return view
    }


    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

         class MyScrollListener(function: () -> Unit) :
            RecyclerView.OnScrollListener() { // abstract methods implemenations
        }

        super.onViewCreated(view, savedInstanceState)
        Log.d("NowPlayingMoviesFragment", "Called updateAdapter in Onviewcreated")

        recyclerView.addOnScrollListener( MyScrollListener {

            Log.d("NowPlayingMoviesFragment", "Called updateAdapter in Onviewcreated")
            updateAdapter(progressBar, recyclerView)
        })


    }*/
    /*
     * Updates the RecyclerView adapter with new data.  This is where the
     * networking magic happens!
    */
    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        // Create and set up an AsyncHTTPClient() here
        val client = AsyncHttpClient()
        val params = RequestParams()
        // Using the client, perform the HTTP request
        params["language"] = "en-US"
        params["region"] = "US"
        params["api_key"] = apiKey
        params["page"] = page.toString()

        client["https://api.themoviedb.org/3/movie/now_playing?", params, object :
            JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                // The wait for a response is over
                progressBar.hide()

                // If the error is not null, log it!
                throwable?.message?.let {
                    if (response != null) {
                        Log.e("NowPlayingMoviesFragment", response)
                    }
                }
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                // The wait for a response is over
                progressBar.hide()
                // Look for this in Logcat:
                Log.d("NowPlayingMoviesFragment", "response successful")

                val movieJsonArray: JSONArray? = json?.jsonObject?.getJSONArray("results")
                //Log.v("NowPlayingMoviesFragment", movieJsonArray.toString())

                movieJsonArray?.let { NowPlayingMovie.fromJsonArray(it) }?.let { movies.addAll(it) }
                recyclerView.adapter?.notifyItemInserted(movies.size - 1)

                recyclerView.adapter =
                    NowPlayingMoviesRecyclerAdapter(movies, this@NowPlayingMoviesFragment)
            }
        }]

        page++
    }
}