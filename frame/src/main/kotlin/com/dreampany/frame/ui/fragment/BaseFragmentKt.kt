package com.dreampany.frame.ui.fragment

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dreampany.frame.data.model.Task
import com.dreampany.frame.data.util.AndroidUtil
import com.dreampany.frame.data.util.TextUtil
import com.dreampany.frame.ui.activity.BaseActivity
import java.io.Serializable


/**
 * Created by Hawladar Roman on 5/22/2018.
 * BJIT Group
 * hawladar.roman@bjitgroup.com
 */
abstract class BaseFragmentKt : Fragment() {

    protected lateinit var binding: ViewDataBinding
    protected var currentTask: Task<*>? = null
    protected var currentView: View? = null

    open fun getLayoutId(): Int {
        return 0
    }

    open fun hasBackPressed(): Boolean {
        return false
    }

    protected abstract fun onStartUi(state: Bundle?)

    protected abstract fun onStopUi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (currentView != null) {
            if (currentView?.getParent() != null) {
                (currentView?.getParent() as ViewGroup).removeView(currentView)
            }
            return currentView
        }
        val layoutId = getLayoutId()
        if (layoutId != 0) {
            binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
            currentView = binding.root
        } else {
            currentView = super.onCreateView(inflater, container, savedInstanceState)
        }
        return currentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onStartUi(savedInstanceState)
    }

    override fun onDestroyView() {
        onStopUi()
        if (currentView != null) {
            val parent = currentView?.getParent() as ViewGroup
            parent?.removeAllViews()
        }
        super.onDestroyView()
    }

    override fun getContext(): Context? {
        if (AndroidUtil.hasMarshmallow()) {
            return super.getContext()
        }
        return if (currentView != null) {
            currentView?.context
        } else {
            getParent()
        }
    }

    protected fun getParent(): BaseActivity? {
        val activity = activity
        return if (!BaseActivity::class.java.isInstance(activity) || activity!!.isFinishing || activity.isDestroyed) {
            null
        } else {
            activity as BaseActivity?
        }
    }

    protected fun isParentActive(): Boolean {
        val activity = getParent()
        return if (activity == null || activity.isFinishing || activity.isDestroyed) {
            false
        } else {
            true
        }
    }

    protected fun <T : Task<*>> getCurrentTask(freshTask: Boolean): T {
        if (currentTask == null || freshTask) {
            currentTask = getIntentValue<Task<*>>(Task::class.java.simpleName)
        }
        return currentTask as T
    }

    protected fun <T> getIntentValue(key: String): T? {
        val bundle = getBundle()
        return getIntentValue<T>(key, bundle)
    }

    protected fun <T> getIntentValue(key: String, bundle: Bundle?): T? {
        var t: T? = null
        if (bundle != null) {
            t = bundle.getParcelable<Parcelable>(key) as T
        }
        if (bundle != null && t == null) {
            t = bundle.getSerializable(key) as T
        }
        return t
    }

    protected fun getBundle(): Bundle? {
        return arguments
    }

    protected fun setTitle(resId: Int) {
        if (resId <= 0) {
            return
        }
        setTitle(TextUtil.getString(context, resId))
    }

    protected fun setSubtitle(resId: Int) {
        if (resId <= 0) {
            return
        }
        setSubtitle(TextUtil.getString(context, resId))
    }

    protected fun setTitle(title: String) {
        val activity = activity
        if (BaseActivity::class.java.isInstance(activity)) {
            (activity as BaseActivity).setTitle(title)
        }
    }

    protected fun setSubtitle(subtitle: String) {
        val activity = activity
        if (BaseActivity::class.java.isInstance(activity)) {
            (activity as BaseActivity).setSubtitle(subtitle)
        }
    }

    protected fun openActivity(clazz: Class<*>) {
        startActivity(Intent(getParent(), clazz))
    }

    protected fun openActivityForResult(clazz: Class<*>, requestCode: Int) {
        startActivityForResult(Intent(getParent(), clazz), requestCode)
    }

    protected fun openActivityParcelable(clazz: Class<*>, task: Task<*>) {
        val bundle = Intent(getParent(), clazz)
        bundle.putExtra(Task::class.java.simpleName, task as Parcelable)
        startActivity(bundle)
    }

    protected fun openActivitySerializable(clazz: Class<*>, task: Task<*>) {
        val bundle = Intent(getParent(), clazz)
        bundle.putExtra(Task::class.java.simpleName, task as Serializable)
        startActivity(bundle)
    }
}