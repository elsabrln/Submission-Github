package com.dicoding.picodiploma.submissiongithub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.SearchManager
import android.view.Menu
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.submissiongithub.databinding.ActivityMainBinding

@Suppress("UNREACHABLE_CODE")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SearchUserAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        initViewModel()
        hideLoading()
    }
    private fun initViewModel(){
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
    }

    private fun initRecyclerView() {
        adapter = SearchUserAdapter()
        adapter.notifyDataSetChanged()
        val rv = binding.rvUser
        rv.layoutManager = LinearLayoutManager(this@MainActivity)
        rv.adapter = adapter
        rv.setHasFixedSize(false)
        adapter.setOnItemClickCallback(object: SearchUserAdapter.OnItemClickCallback{
            override fun onItemClicked(user: User) {
                Toast.makeText(this@MainActivity, user.login, Toast.LENGTH_SHORT).show()
                DetailActivity.start(this@MainActivity, user)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.option_menu, menu)
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search_user)?.actionView as SearchView
        
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)   
        
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                if (query != null){
                    if (query.isNotEmpty()){
                        showLoading()
                        viewModel.setSearchResult(query).observe(this@MainActivity){
                            when(it.status){
                                Status.LOADING -> showLoading()
                                Status.SUCCESS -> {
                                    it.data?.let{users-> adapter.setUser(users)}
                                    hideLoading()
                                }
                                Status.ERROR -> {
                                    hideLoading()
                                    Toast.makeText(this@MainActivity,
                                    "Failed: ${it.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                        
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
        return true
    }

    private fun hideLoading() {
        binding.apply {
            shimmerLayout.stopShimmer()
            rvUser.visibility = View.VISIBLE
            shimmerLayout.visibility = View.GONE
        }
    }

    private fun showLoading() {
        binding.apply {
            rvUser.visibility = View.GONE
            shimmerLayout.visibility = View.VISIBLE
            shimmerLayout.showShimmer(true)
        }
    }

}