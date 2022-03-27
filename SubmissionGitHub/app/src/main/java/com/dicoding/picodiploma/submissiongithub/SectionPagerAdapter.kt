package com.dicoding.picodiploma.submissiongithub

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.util.pool.StateVerifier.newInstance

class SectionPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity){
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = FollowFragment.newInstance(position+1)

}



