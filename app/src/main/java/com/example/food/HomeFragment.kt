package com.example.food

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food.Adapters.PopularAdapter
import com.example.food.Fragments.MenuBottomSheetFragment
import com.example.food.Models.PopularModel
import com.example.food.Models.SharedModel
import com.example.food.db.MainDb
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private lateinit var handler: Handler
    private lateinit var popularAdapter: PopularAdapter
    private lateinit var listPopular : ArrayList<PopularModel>
    private lateinit var homeRv : RecyclerView
    private lateinit var goMenuText : TextView
    private lateinit var sharedModel : SharedModel


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        homeRv = view.findViewById(R.id.home_RV)
        goMenuText = view.findViewById(R.id.go_menu)

        goMenuText.setOnClickListener{
            val bottomSheetMenu = MenuBottomSheetFragment()
            bottomSheetMenu.show(parentFragmentManager, "Test")
        }

        sharedModel = ViewModelProvider(
            requireActivity()
        ).get(SharedModel :: class.java)

        val db = MainDb.getDb(requireContext())
        listPopular = ArrayList()

        lifecycleScope.launch {
          val foodItems = db.getDao().getAllfoodItems()

           foodItems.forEach { foodItem ->

               listPopular.add(PopularModel(foodItem.name, foodItem.price, foodItem.price, 1))

           }

            popularAdapter.notifyDataSetChanged()

        }
//        listPopular.add(PopularModel("Burger",500, 500, 1))
//        listPopular.add(PopularModel("Burger",500, 500, 1))
//        listPopular.add(PopularModel("Burger",500, 500, 1))


        popularAdapter= PopularAdapter(requireContext(), listPopular)
        popularAdapter.setSharedModel(sharedModel)
        homeRv.layoutManager = LinearLayoutManager(requireContext())
        homeRv.adapter = popularAdapter

        // Inflate the layout for this fragment
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}