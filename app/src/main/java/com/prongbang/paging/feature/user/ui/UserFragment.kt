package com.prongbang.paging.feature.user.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.prongbang.paging.R
import com.prongbang.paging.feature.user.di.Injection
import kotlinx.android.synthetic.main.user_fragment.view.*

class UserFragment : Fragment() {

    companion object {
        fun newInstance() = UserFragment()
    }

    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, Injection.provideUserViewModelFactory()).get(UserViewModel::class.java)

        onBindView()
    }

    private fun onBindView() {
        val mAdapter = UserAdapter {
            viewModel.retry()
        }

        view?.let {
            it.rvUser.apply {
                adapter = mAdapter
                layoutManager = LinearLayoutManager(context)
                isNestedScrollingEnabled = false
            }
        }

        viewModel.getUserByPage(1)
        viewModel.users.observe(this, Observer {
            mAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {
            Log.i(UserFragment::class.java.simpleName, "networkState -> ${it.status}")
            mAdapter.setNetworkState(it)
        })

        viewModel.refreshState.observe(this, Observer {

        })
    }

}
