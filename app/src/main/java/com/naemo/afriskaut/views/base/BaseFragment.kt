package com.naemo.afriskaut.views.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment


abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel<*>> : DaggerFragment() {

    private var mActivity: BaseActivity<*,*>? = null
    private var mViewDataBinding: T? = null
    private var mViewModel: V? = null
    private var mRootView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = getViewModel()
       // setHasOptionsMenu(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mRootView = mViewDataBinding?.root
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding?.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding?.executePendingBindings()
       // ToolbarManager(builder(), view).prepareToolbar()
    }

    //protected abstract fun builder(): FragmentToolbar

    override fun onAttach(context: Context) {
        performDependencyInjection()
        super.onAttach(context)
        if(context is BaseActivity<*, *>) {
            val activity = context
            this.mActivity = activity
        }
    }

    override fun onDetach() {
        mActivity = null
        super.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun getBaseActivity(): BaseActivity<*, *>? {
        return mActivity
    }

    fun getViewDataBinding(): T? {
        return mViewDataBinding
    }

    abstract fun getBindingVariable(): Int

    @LayoutRes
    abstract fun getLayoutId(): Int

    fun performDependencyInjection() {
        AndroidSupportInjection.inject(this)
    }

    abstract fun getViewModel(): V?

    fun hideKeyBoard() {
        val imm = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
    

    interface Callback {

        fun onFragmentAttached()

        fun onFragmentDetached(tag: String)
    }
}