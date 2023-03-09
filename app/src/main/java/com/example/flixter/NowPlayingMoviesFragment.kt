package com.example.flixter

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment

private const val API_KEY = "5502e1574702c422bf438431d56a2184"

class NowPlayingMoviesFragment : Fragment(), OnListFragmentInteractionListener {

    /*
    * What happens when a particular movie is clicked.
    */
    override fun onItemClick(item: NowPlayingMovie) {
        TODO("Not yet implemented")
    }

    /*
   * Constructing the view
   */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_best_seller_books_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        updateAdapter(progressBar, recyclerView)
        return view
    }
}