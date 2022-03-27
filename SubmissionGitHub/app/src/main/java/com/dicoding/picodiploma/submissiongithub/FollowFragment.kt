package com.dicoding.picodiploma.submissiongithub

import android.os.Build.USER
import android.os.Bundle
import android.provider.Telephony.Carriers.USER
import android.view.LayoutInflater
import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dicoding.picodiploma.submissiongithub.User
import com.dicoding.picodiploma.submissiongithub.Resource.*
import com.dicoding.picodiploma.submissiongithub.DetailActivity.*
import com.dicoding.picodiploma.submissiongithub.SearchUserAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.submissiongithub.Status.*
import com.dicoding.picodiploma.submissiongithub.databinding.FragmentFollowBinding

class FollowFragment : Fragment () {

    private var inbinding: FragmentFollowBinding? = null
    private val binding get() = inbinding!!
    private lateinit var adapter: SearchUserAdapter
    private lateinit var viewModel: FollowViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val user = activity?.intent?.getParcelableExtra<User>(DetailActivity.USER)

        initRecyclerView()
        initViewModel()
        if (index != null) {
            if (user != null) {
                setUserList(index, user)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inbinding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        inbinding = null
    }

    private fun setUserList(index: Int, user: User) {
        when (index) {
            1 -> viewModel.getFollowers(user.login).observe(viewLifecycleOwner) {
                populateFollow(it)
            }
            2 -> viewModel.getFollowing(user.login).observe(viewLifecycleOwner) {
                populateFollow(it)
            }
        }
    }

    private fun populateFollow(it: Resource<ArrayList<User>>) {
        when (it.status) {
            LOADING -> showLoading()
            SUCCESS -> {
                it.data?.let { users -> adapter.setUser(users) }
                hideLoading()
            }
            ERROR -> {
                hideLoading()
                Toast.makeText(activity, "Failure: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun hideLoading() {
        binding.apply {
            shimmerLayout.stopShimmer()
            rvUsers.visibility = View.VISIBLE
            shimmerLayout.visibility = View.GONE
        }
    }

        private fun initViewModel() {
            viewModel = ViewModelProvider(this,
                ViewModelProvider.NewInstanceFactory())[FollowViewModel::class.java]
        }

        private fun initRecyclerView() {
            adapter = SearchUserAdapter()
            adapter.notifyDataSetChanged()
            val rv = binding.rvUsers
            rv.layoutManager = LinearLayoutManager(activity)
            rv.adapter = adapter
            rv.setHasFixedSize(false)
            adapter.setOnItemClickCallback(object : SearchUserAdapter.OnItemClickCallback {
                override fun onItemClicked(user: User) {
                    DetailActivity.start(activity, user)
                }
            })
        }

        private fun showLoading() {
            binding.apply {
                rvUsers.visibility = View.GONE
                shimmerLayout.visibility = View.VISIBLE
                shimmerLayout.showShimmer(true)
            }
        }

        companion object {
            private const val ARG_SECTION_NUMBER = "section_number"
            fun newInstance(index: Int) = FollowFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, index)
                }
            }
        }
    }
