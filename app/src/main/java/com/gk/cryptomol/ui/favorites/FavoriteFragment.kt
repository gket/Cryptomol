package com.gk.cryptomol.ui.favorites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.gk.cryptomol.R
import com.gk.cryptomol.adapter.CoinAdapter
import com.gk.cryptomol.data.CoinItem
import com.gk.cryptomol.databinding.FragmentFavoritesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val db by lazy { FirebaseFirestore.getInstance() }
    private val user by lazy { FirebaseAuth.getInstance() }
    lateinit var coinAdapter: CoinAdapter
    var coinItems = ArrayList<CoinItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coinAdapter = CoinAdapter()
        binding.recyclerViewFavCoins.adapter = coinAdapter
        coinAdapter.onCoinClicked = {
            val action =
                FavoriteFragmentDirections.actionNavigationFavoriteToNavigationCoinDetail(it)
            findNavController().navigate(action)
        }

        binding.buttonSignOut.setOnClickListener {
            signOut()
        }

        loginCheckForUser()
    }

    fun getFavCoinList() {
        coinItems.clear()
        db.collection(getString(R.string.text_db_collection_fav))
            .whereEqualTo(
                getString(R.string.text_coin_db_userid),
                FirebaseAuth.getInstance().currentUser?.uid
            )
            .get()
            .addOnSuccessListener { result ->
                for (key in result) {
                    coinItems.add(
                        CoinItem(
                            key.get(getString(R.string.text_coin_db_id)) as String,
                            key.get(getString(R.string.text_coin_db_name)) as String,
                            key.get(getString(R.string.text_coin_db_symbol)) as String
                        )
                    )
                }
                coinAdapter.setData(coinItems as List<CoinItem>)
            }
            .addOnFailureListener { exception ->
                Log.d("DbFailure:", exception.toString())
            }
    }

    fun signOut() {
        context?.let {
            AuthUI.getInstance()
                .signOut(it)
                .addOnCompleteListener {
                    activity?.onBackPressed()
                }
        }
    }

    fun loginCheckForUser() {
        if (user.currentUser != null) {
            getFavCoinList()
            binding.buttonSignOut.visibility = View.VISIBLE
            binding.textViewWelcome.text = user.currentUser?.displayName
        } else {
            binding.buttonSignOut.visibility = View.GONE
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build()
            )
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
                100
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}