package com.example.myapplication


import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import com.example.myapplication.databinding.FragmentSeventhBinding


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SeventhFragment : Fragment() {

    private var _binding: FragmentSeventhBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var adapter: F7PlayerScoreAdapter? = null
    private var allPlayers = mutableListOf<CurrentPlayer>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSeventhBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        get a list of all current players from the backend, including their name and healthpoints
        the name and the healthpoints are then bound to the Listview
         */
        val winnerBind = binding.textWinnerF7
       // binding.textWinnerF7.text = allPlayers?.mostHealthpoint()?.name? : "-"

        //binding.textEnoughF7.text = "Already enough of Dodelido?"  //how do I do that shows value form strings.xml?



        val requestQueue = Volley.newRequestQueue(requireContext())
        requestQueue.add(getAllPlayers())

        adapter = F7PlayerScoreAdapter(allPlayers, requireContext())
        binding.listViewF7.adapter = adapter

        /*
        val textPLayer = binding.textPlayerF7
        binding.textPlayerF7.text = "Player:"
        binding.listViewF7.addHeaderView(textPLayer)
        */
        //val textHP = binding.textHealthpointsF7
        //binding.listViewF7.addHeaderView(textHP)
        //val inflater = layoutInflater
       // val header = inflater.inflate(R.layout.header, listViewF7, false) as ViewGroup
        //listViewF7.addHeaderView(header, null, false)


        //this part not added as unclear how and if needed
        //this.setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,names));


        //

        /*
        as we already have updated the adapter list, we use it to determine the player with the most remaining healthpoints
         */
/*
        var winner = adapter!!.mostHealthpoint()
        if (winner != null) {
            var displayTextWinner = winner.getPlayerName() + " won with " + winner.getPlayerHealthPoints() + " HP left. Congrats!"
            //binding.textWinnerF7.text = displayTextWinner
            winnerBind.text = displayTextWinner
        }

 */





        binding.buttonExitF7.setOnClickListener {
            findNavController().navigate(com.example.myapplication.R.id.action_seventhFragment_to_FirstFragment)
            // findNavController().navigate(R.id.action_seventhFragment_to_FirstFragment)
            //wieso funktioniert nicht?
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun deadPlayer() {}
    fun getAllPlayers() : StringRequest {
        //var players : MutableList<CurrentPlayer>? = null
          //  var dead = false
      //  var deadPlayer : CurrentPlayer? = null
        val url = "http://10.0.2.2:8080/players/"


        //define a request.

        val request = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
               var players = ArrayList(Klaxon().parseArray<CurrentPlayer>(response))
//Klaxon().parse<MutableList<CurrentPlayer>>(response)


                allPlayers.addAll(players!!)
                adapter?.notifyDataSetChanged()

                val textPLayer = binding.textPlayerF7
                //binding.textPlayerF7.text = "Player:"

                //binding.listViewF7.addHeaderView(textPLayer)

                //val board = Klaxon().parse<Stationboard>(response)
                //stationboardF.addAll(board!!.stationboard)
                //adapter?.notifyDataSetChanged()
/*
                if (players != null) {
                    for (player in players) {
                        var temp = player.getPlayerHealthPoints()
                        if (temp >= 0) {
                            dead = true
                        }
                        if (dead) {
                            deadPlayer = player
                        }
                    }
                }

 */

                var tempHP = 0
                var winner : CurrentPlayer? = null
                for (player in players) {
                    if (player.getPlayerHealthPoints() > tempHP) {
                        winner = player
                        tempHP = player.getPlayerHealthPoints()
                    }
                }

                if (winner != null) {
                    var displayTextWinner = winner.getPlayerName() + " won with " + winner.getPlayerHealthPoints() + " HP left. Congrats!"
                    //binding.textWinnerF7.text = displayTextWinner
                    binding.textWinnerF7.text = displayTextWinner
                }
            },

            Response.ErrorListener {
                //use the porvided VolleyError to display
                //an error message
                Log.e("ERROR", it.message!!)
            })

           //requestQueue.add(request)
/*
            if (players != null) {
                for (player in players!!) {
                var temp = player.getPlayerHealthPoints()
                if (temp >= 0) {
                dead = true
                 }
                    if (dead) {
                     deadPlayer = player
                      }
                }
            }
        return deadPlayer

 */
        return request
    }


}


/*
    fun callFromOtherFragment() {
        val fm: FragmentManager? = fragmentManager
        val fragm: SixthFragment = fm?.findFragmentById(R.id.sixth_frag_tag) as SixthFragment
        fragm.getCurrentPlayer()
    }

 */
