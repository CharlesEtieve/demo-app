package com.demo.app.app.modules.users.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.eurosportdemo.app.R
import com.demo.app.app.modules.users.adapter.UserListAdapter
import com.demo.app.app.utils.BaseFragment
import com.demo.app.app.utils.MarginItemDecoration
import com.demo.app.app.utils.viewBinding.fragmentViewBinding
import com.eurosportdemo.app.databinding.FragmentUserListBinding
import com.demo.app.presenter.viewModels.UserListViewModel
import com.jakewharton.rxbinding4.swiperefreshlayout.refreshes
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

@AndroidEntryPoint
class UserListFragment : BaseFragment(R.layout.fragment_user_list) {

    private val viewModel: UserListViewModel by viewModels()
    private val binding by fragmentViewBinding<FragmentUserListBinding>()

    @Inject
    lateinit var adapter: UserListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.addItemDecoration(
                MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.horizontal_margin))
            )
        }

        viewModel.getViewState(
            viewCreated = viewCreatedSubject,
            displayProgress = adapter.displayProgressSubject,
            refresh = binding.swipe.refreshes()
        ).subscribe { state ->
            when (state) {
                is UserListViewModel.ViewState.ShowUserList -> {
                    with(binding) {
                        swipe.isRefreshing = false
                        viewSwitcher.displayedChild = 0
                    }
                    //toMutableList() is used to fix a bug with diffUtil https://stackoverflow.com/a/50031492/4557144
                    adapter.submitList(state.userList.toMutableList())
                }
                is UserListViewModel.ViewState.ShowErrorMessage -> {
                    with(binding) {
                        swipe.isRefreshing = false
                        viewSwitcher.displayedChild = 0
                    }
                    Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
                }
                is UserListViewModel.ViewState.ShowNoData -> {
                    with(binding) {
                        swipe.isRefreshing = false
                        viewSwitcher.displayedChild = 1
                    }
                }
            }
        }.addTo(disposable)
    }
}