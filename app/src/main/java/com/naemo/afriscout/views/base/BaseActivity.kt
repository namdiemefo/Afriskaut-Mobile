package com.naemo.afriscout.views.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dagger.android.AndroidInjection

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>> : AppCompatActivity(), BaseFragment.Callback{

    protected val REQUEST_LOGIN = -1

    private var mViewDataBinding: T? = null
    private var mViewModel: V? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        performDataBinding()
    }

    private fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        this.mViewModel = if (mViewModel == null) getViewModel() else mViewModel
        mViewDataBinding?.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding?.executePendingBindings()
    }

    fun hideKeyBoard() {
        val view: View? = this.currentFocus
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager //casting
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    abstract fun getBindingVariable(): Int

    abstract fun getViewModel(): V?

    @LayoutRes
    abstract fun getLayoutId(): Int

    private fun performDependencyInjection() {
        AndroidInjection.inject(this)
    }

    fun addFragment(fragment: BaseFragment<T, V>, tag: String, idContainer: Int) {
        this.supportFragmentManager
            .beginTransaction()
            .add(idContainer, fragment, tag)
            .commit()
    }

    fun showFragment(fragment: BaseFragment<T, V>, tag: String, idContainer: Int, addToBackStack: Boolean) {
        if(addToBackStack) {
            this.supportFragmentManager
                .beginTransaction()
                .addToBackStack(tag)
                .replace(idContainer, fragment, tag)
                .commit()
        } else {
            showFragment(fragment, tag, idContainer)
        }
    }

    fun showFragment(fragment: BaseFragment<T, V>, tag: String, idContainer: Int) {
        this.supportFragmentManager
            .beginTransaction()
            .disallowAddToBackStack()
            .add(idContainer, fragment, tag)
            .commit()
    }

    fun closeFragment(tag: String, addToBackStack: Boolean) {

        val fragment = supportFragmentManager.findFragmentByTag(tag)
        fragment?.let {
            supportFragmentManager.beginTransaction().remove(it).commit()
        }
        if(addToBackStack) {
            supportFragmentManager.popBackStack()
        }
    }

   fun isOnline(): Boolean {
       val baseComponent = DaggerBaseComponent.create()
       val network = baseComponent.getNetwork()
       return network.isNetworkConnected(this)
       // return NetworkUtils.isNetworkConnected(this)
    }
}