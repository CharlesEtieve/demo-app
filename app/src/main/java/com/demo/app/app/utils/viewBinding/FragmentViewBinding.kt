package com.demo.app.app.utils.viewBinding

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Delegate Property to simplify init of fragment view bindings
 * @author https://medium.com/@gpeal/ten-androidlifehacks-you-can-use-today-9f74186fc207
 * @sample ⚠️ MyFragment: Fragment(R.layout.fragment_my_layout) following by
 *            val binding by fragmentViewBinding<MyFragmentViewBinding>() or val binding : MyFragmentViewBinding by fragmentViewBinding()
 */
inline fun <reified T : ViewBinding> Fragment.fragmentViewBinding() = FragmentViewBindingDelegate(T::class.java, this)

@Suppress("UNCHECKED_CAST")
class FragmentViewBindingDelegate<T : ViewBinding>(
    bindingClass: Class<T>,
    val fragment: Fragment
) : ReadOnlyProperty<Fragment, T> {
    private val clearBindingHandler by lazy(LazyThreadSafetyMode.NONE) { Handler(Looper.getMainLooper()) }
    private var binding: T? = null

    private val bindMethod = bindingClass.getMethod("bind", View::class.java)

    init {
        fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewLifecycleOwner ->
            viewLifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestroy() {
                    clearBindingHandler.post { binding = null }
                }
            })
        }
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        if (binding != null && binding?.root !== thisRef.view) {
            binding = null
        }
        binding?.let { return it }

        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            error("Cannot access view bindings. View lifecycle is ${lifecycle.currentState}!")
        }

        binding = bindMethod.invoke(null, thisRef.requireView()) as T
        return binding!!
    }
}
