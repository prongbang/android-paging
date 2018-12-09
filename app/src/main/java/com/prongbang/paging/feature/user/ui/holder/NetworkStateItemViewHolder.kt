package com.prongbang.paging.feature.user.ui.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prongbang.paging.R
import com.prongbang.paging.feature.user.model.NetworkState
import com.prongbang.paging.feature.user.model.Status
import kotlinx.android.synthetic.main.network_state_item.view.*

/**
 * A View Holder that can display a loading or have click action.
 * It is used to show the network state of paging.
 */
class NetworkStateItemViewHolder(
    private val view: View,
    private val retryCallback: () -> Unit
) : RecyclerView.ViewHolder(view) {

    fun bindTo(networkState: NetworkState?) {
        view.let {
            it.progressBar.visibility = toVisibility(networkState?.status == Status.RUNNING)
            it.btnRetry.visibility = toVisibility(
                when (networkState?.status) {
                    Status.FAILED,
                    Status.CLIENT_FAILED,
                    Status.TIMEOUT,
                    Status.UNAUTHORIZED,
                    Status.EMPTY,
                    Status.OTHER_FAILED,
                    Status.REDIRECTION -> true
                    else -> false
                }
            )
            it.tvErrorMsg.visibility = toVisibility(networkState?.msg != null)
            it.tvErrorMsg.text = networkState?.msg
            it.btnRetry.setOnClickListener {
                retryCallback()
            }
        }
    }

    companion object {

        fun create(parent: ViewGroup, retryCallback: () -> Unit): NetworkStateItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.network_state_item, parent, false)
            return NetworkStateItemViewHolder(view, retryCallback)
        }

        fun toVisibility(constraint: Boolean): Int {
            return if (constraint) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }
}