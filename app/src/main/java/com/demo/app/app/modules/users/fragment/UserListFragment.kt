package com.demo.app.app.modules.users.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.app.app.modules.users.adapter.UserAdapterDelegate
import com.eurosportdemo.app.R
import com.demo.app.app.modules.users.adapter.UserListAdapter
import com.demo.app.app.utils.BaseFragment
import com.demo.app.app.utils.MarginItemDecoration
import com.demo.app.app.utils.viewBinding.fragmentViewBinding
import com.demo.app.presenter.models.UIUserItem
import com.eurosportdemo.app.databinding.FragmentUserListBinding
import com.demo.app.presenter.viewModels.UserListViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

@AndroidEntryPoint
class UserListFragment : BaseFragment(R.layout.fragment_user_list), UserAdapterDelegate {

    private val viewModel: UserListViewModel by viewModels()
    private val binding by fragmentViewBinding<FragmentUserListBinding>()

    @Inject
    lateinit var adapter: UserListAdapter

    private var refresh: BehaviorSubject<Unit> = BehaviorSubject.create()
    private var load: BehaviorSubject<Unit> = BehaviorSubject.create()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.delegate = this
        with(binding) {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.addItemDecoration(
                MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.horizontal_margin))
            )
            swipe.setOnRefreshListener {
                refresh.onNext(Unit)
            }
        }

        viewModel.getViewState(
            load = load.flatMap { Observable.just(Unit) },
            refresh = refresh.flatMap { Observable.just(Unit) }
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

        load.onNext(Unit)
    }

    override fun didSelectUser(item: UIUserItem) {
        //not implemented yet
    }

    override fun didDisplayProgress() {
        load.onNext(Unit)
    }
}