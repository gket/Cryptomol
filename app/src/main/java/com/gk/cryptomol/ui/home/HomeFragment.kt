package com.gk.cryptomol.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.gk.cryptomol.adapter.CoinAdapter
import com.gk.cryptomol.databinding.FragmentHomeBinding
import org.koin.android.ext.android.inject


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by inject()
    lateinit var coinAdapter: CoinAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeProcess()
        searchProcess()
        homeViewModel.getCoinList()
        coinAdapter = CoinAdapter()
        binding.recyclerViewCoins.adapter = coinAdapter
        coinAdapter.onCoinClicked = { coinId ->
           // val bundle = bundleOf("coinId" to coinId)
            //findNavController().navigate(R.id.action_navigation_home_to_navigation_coin_detail, bundle)
            val action = HomeFragmentDirections.actionNavigationHomeToNavigationCoinDetail(coinId)
            findNavController().navigate(action)
        }
    }


    private fun getSearchResultFromDb(query: String?) {
        var searchText = query
        searchText = "%$searchText%"
        homeViewModel.getSearchResult(searchText)
    }

    private fun observeProcess() {
        homeViewModel.searchResult.observe(viewLifecycleOwner, Observer {
            coinAdapter.setData(it)
        })

        homeViewModel.allCoins.observe(viewLifecycleOwner, Observer { coinItems ->
            coinAdapter.setData(coinItems)
        })
    }

    private fun searchProcess() {
        binding.searchView.isFocusable = true;
        binding.searchView.isFocusableInTouchMode = false
        binding.searchView.requestFocusFromTouch();

        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(queryString: String?): Boolean {
                getSearchResultFromDb(queryString)
                return false
            }

            override fun onQueryTextChange(queryString: String?): Boolean {
                getSearchResultFromDb(queryString)
                return false
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}