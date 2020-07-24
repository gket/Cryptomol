package com.gk.cryptomol.ui.coindetail

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.gk.cryptomol.R
import com.gk.cryptomol.data.CoinDetail
import com.gk.cryptomol.databinding.FragmentCoinDetailBinding
import com.gk.cryptomol.utils.Constants
import com.gk.cryptomol.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoinDetailFragment : Fragment() {
    private var isCameFromRefresh: Boolean = false
    private var editText: EditText? = null
    lateinit var coinDetailResponse: CoinDetail
    private val db by lazy { FirebaseFirestore.getInstance() }
    private val user by lazy { FirebaseAuth.getInstance() }
    private var isAddOperation = false
    private var _binding: FragmentCoinDetailBinding? = null
    private val binding get() = _binding!!
    private val coinDetailViewModel: CoinDetailViewModel by viewModel()
    private val args by navArgs<CoinDetailFragmentArgs>()
    lateinit var coinId : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCoinDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coinId = args.coinId
        observeProcess()
        coinDetailViewModel.getCoinDetail(coinId, 0)
        setIntervalTime()
        binding.buttonAddFav.setOnClickListener {
            if (user.currentUser != null) {
                if (isAddOperation) {
                    insertFav()
                } else {
                    Toast.makeText(context, getString(R.string.text_coin_added), Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(
                    context,
                    getString(R.string.text_sign_in_sign_up),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    private fun setIntervalTime() {
        binding.editTextRefreshInterval.setOnEditorActionListener { query, editorInfo, p2 ->
            if (editorInfo == EditorInfo.IME_ACTION_DONE) {
                if (query?.text.toString().isNotEmpty()) {
                    Constants.REFRESH_INTERVAL = query?.text.toString().toLong()
                    editText?.clearFocus();
                }
            }
            false;
        }
    }

    private fun insertFav() {
        var favoriteItem = hashMapOf(
            getString(R.string.text_coin_db_name) to coinDetailResponse.coinName,
            getString(R.string.text_coin_db_id) to coinDetailResponse.coinId,
            getString(R.string.text_coin_db_symbol) to coinDetailResponse.coinSymbol,
            getString(R.string.text_coin_db_userid) to FirebaseAuth.getInstance().currentUser?.uid
        )
        db.collection(getString(R.string.text_db_collection_fav))
            .add(favoriteItem)
            .addOnSuccessListener {
                Toast.makeText(
                    context,
                    getString(R.string.text_added_favorite_list),
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    private fun observeProcess(){
        coinDetailViewModel.coinDetailResource.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    if (!isCameFromRefresh) {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.constraintContainer.visibility = View.VISIBLE
                    binding.buttonAddFav.visibility = View.VISIBLE
                    if (it.data != null) {
                        coinDetailResponse = it.data
                        if (!isCameFromRefresh) {
                            setUi()
                        } else {
                            setRefreshUi()
                        }
                        coinDetailViewModel.getCoinDetail(
                            coinId,
                            Constants.REFRESH_INTERVAL * 1000
                        )
                        isCameFromRefresh = true
                        checkFav()
                    }

                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun checkFav() {
        if (user.currentUser != null) {
            db.collection(getString(R.string.text_db_collection_fav))
                .whereEqualTo(
                    getString(R.string.text_coin_db_userid),
                    FirebaseAuth.getInstance().currentUser?.uid
                )
                .whereEqualTo(getString(R.string.text_coin_db_id), coinDetailResponse.coinId)
                .get()
                .addOnSuccessListener { result ->
                    isAddOperation = result.isEmpty
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(context, exception.toString(), Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun setUi() {
        binding.textViewCoinName.text = coinDetailResponse.coinName
        Glide.with(this).load(coinDetailResponse.image.large)
            .into(binding.imageViewCoinIcon)
        binding.textViewHashingAlgorithm.text =
            coinDetailResponse.hashingAlgorithm
        binding.textViewDescription.text = coinDetailResponse.description.en
        binding.textViewDescription.setMovementMethod(ScrollingMovementMethod())
        setRefreshUi()
    }

    private fun setRefreshUi() {
        binding.textViewPrice.text =
            coinDetailResponse.marketData.currentPrice.usd.toString()
        binding.textViewPriceChange.text =
            coinDetailResponse.marketData.priceChangeDaily.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}