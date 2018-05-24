package com.dreampany.frame.ui.activity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.afollestad.aesthetic.Aesthetic
import com.dreampany.frame.R
import com.dreampany.frame.data.model.Task
import com.dreampany.frame.util.BarUtil
import com.dreampany.frame.util.FragmentUtil
import com.dreampany.frame.ui.fragment.BaseFragment
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.DexterError
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.PermissionRequestErrorListener
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import dagger.Lazy
import dagger.android.support.DaggerAppCompatActivity
import java.io.Serializable


/**
 * Created by Hawladar Roman on 5/22/2018.
 * BJIT Group
 * hawladar.roman@bjitgroup.com
 */
abstract class BaseActivityKt : DaggerAppCompatActivity(), MultiplePermissionsListener, PermissionRequestErrorListener {

    protected lateinit var binding: ViewDataBinding
    protected var currentTask: Task<*>? = null
    protected var currentFragment: BaseFragment? = null
    protected var fireOnStartUi: Boolean = true

    open fun getLayoutId(): Int {
        return 0
    }

    open fun getToolbarId(): Int {
        return R.id.toolbar
    }

    open fun isFullScreen(): Boolean {
        return false
    }

    open fun isHomeUp(): Boolean {
        return true
    }

    open fun isScreenOn(): Boolean {
        return false
    }

    protected abstract fun onStartUi(state: Bundle?)

    protected abstract fun onStopUi()

    override fun onCreate(savedInstanceState: Bundle?) {
        //Aesthetic.attach(this)
        super.onCreate(savedInstanceState)
        if (isScreenOn()) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        val layoutId = getLayoutId()
        if (layoutId != 0) {
            initLayout(layoutId)
            initToolbar()
            //initTheme()
        }
        if (fireOnStartUi) {
            onStartUi(savedInstanceState)
        }
    }

    override fun onResume() {
        super.onResume()
        //Aesthetic.resume(this)
    }

    override fun onPause() {
        //Aesthetic.pause(this)
        super.onPause()
    }

    override fun onDestroy() {
        onStopUi()
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        /*        if (!beBackPressed()) {
            return;
        }

        BaseFragment currentFragment = getCurrentFragment();
        if (currentFragment != null) {
            if (currentFragment.beBackPressed()) {
                return;
            }
        }

        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            manager.popBackStack();
            return;
        }*/

        super.onBackPressed()
    }

/*    @Nullable
    @Override
    public String key() {
        return "base";
    }*/

    override fun onPermissionsChecked(report: MultiplePermissionsReport) {

    }

    override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) {

    }

    override fun onError(error: DexterError) {

    }

    private fun initLayout(layoutId: Int) {
        if (isFullScreen()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            BarUtil.hide(this)
        } else {
            BarUtil.show(this)
        }
        binding = DataBindingUtil.setContentView(this, layoutId)
    }

    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(getToolbarId())
        if (toolbar != null) {
            if (isFullScreen()) {
                if (toolbar.isShown) {
                    toolbar.visibility = View.GONE
                }
            } else {
                if (!toolbar.isShown) {
                    toolbar.visibility = View.VISIBLE
                }
                setSupportActionBar(toolbar)
                if (isHomeUp()) {
                    val actionBar = supportActionBar
                    if (actionBar != null) {
                        actionBar.setDisplayHomeAsUpEnabled(true)
                        actionBar.setHomeButtonEnabled(true)
                    }
                }
            }
        }
    }

    private fun initTheme() {
        //if (Aesthetic.isFirstTime()) {
        Aesthetic.get()
                .colorPrimaryRes(R.color.colorPrimary)
                .colorPrimaryDarkRes(R.color.colorPrimaryDark)
                .colorAccentRes(R.color.colorAccent)
                .colorStatusBarAuto()
                .colorNavigationBarAuto()
                .textColorPrimaryRes(android.R.color.black)
                .textColorPrimaryInverseRes(android.R.color.white)
                .textColorSecondaryRes(R.color.material_grey500)
                .textColorSecondaryInverseRes(R.color.material_grey100)
                //.bottomNavigationBackgroundMode(BottomNavBgMode.PRIMARY)
                //.bottomNavigationIconTextMode(BottomNavIconTextMode.SELECTED_ACCENT)
                .apply()
        //}
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
        return intent.extras
    }

    fun setTitle(title: String) {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = title
        }
    }

    fun setSubtitle(subtitle: String) {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.subtitle = subtitle
        }
    }

    fun openActivity(clazz: Class<*>) {
        startActivity(Intent(this, clazz))
    }

    fun openActivityForResult(clazz: Class<*>, requestCode: Int) {
        startActivityForResult(Intent(this, clazz), requestCode)
    }

    fun openActivityParcelable(clazz: Class<*>, task: Task<*>) {
        val bundle = Intent(this, clazz)
        bundle.putExtra(Task::class.java.simpleName, task as Parcelable)
        startActivity(bundle)
    }

    fun openActivitySerializable(clazz: Class<*>, task: Task<*>) {
        val bundle = Intent(this, clazz)
        bundle.putExtra(Task::class.java.simpleName, task as Serializable)
        startActivity(bundle)
    }

/*    protected <T extends BaseFragment> T commitFragment(final Class<T> fragmentClass, final int parentId) {
        T currentFragment = FragmentUtil.commitFragment(this, fragmentClass, parentId);
        setCurrentFragment(currentFragment);
        return currentFragment;
    }*/

    protected fun <T : BaseFragment> commitFragment(clazz: Class<T>, fragmentProvider: Lazy<T>, parentId: Int): T {
        var fragment: T? = FragmentUtil.getFragmentByTag(this, clazz.simpleName)
        if (fragment == null) {
            fragment = fragmentProvider.get()
        }
        val currentFragment = FragmentUtil.commitFragment<T>(this, fragment, parentId)
        this.currentFragment = currentFragment
        return currentFragment
    }

    protected fun <T : BaseFragment> commitFragment(clazz: Class<T>, fragmentProvider: Lazy<T>, parentId: Int, task: Task<*>): T {
        var fragment: T? = FragmentUtil.getFragmentByTag(this, clazz.simpleName)
        if (fragment == null) {
            fragment = fragmentProvider.get()
            val bundle = Bundle()
            bundle.putParcelable(Task::class.java.simpleName, task)
            fragment!!.arguments = bundle
        } else {
            fragment.arguments!!.putParcelable(Task::class.java.simpleName, task)
        }

        val currentFragment = FragmentUtil.commitFragment(this, fragment, parentId)
        this.currentFragment = currentFragment
        return currentFragment
    }
}