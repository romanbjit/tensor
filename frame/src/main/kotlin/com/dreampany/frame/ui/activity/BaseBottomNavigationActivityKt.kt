package com.dreampany.frame.ui.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem


/**
 * Created by Hawladar Roman on 5/22/2018.
 * BJIT Group
 * hawladar.roman@bjitgroup.com
 */
abstract class BaseBottomNavigationActivityKt : BaseMenuActivityKt(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var currentNavigationId: Int = 0

    open fun getNavigationViewId(): Int {
        return 0
    }

    open fun getDefaultSelectedNavItemId(): Int {
        return 0
    }

    protected abstract fun onNavigationItem(navItemId: Int)

    override fun onCreate(savedInstanceState: Bundle?) {
        fireOnStartUi = false
        super.onCreate(savedInstanceState)
        val navigationView = findViewById<BottomNavigationView>(getNavigationViewId())
        navigationView?.setOnNavigationItemSelectedListener(this)
        setSelectedItem(getDefaultSelectedNavItemId())
        onStartUi(savedInstanceState)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val targetNavigationId = item.itemId
        if (targetNavigationId != currentNavigationId) {
            onNavigationItem(targetNavigationId)
            currentNavigationId = targetNavigationId
            return true
        }
        return false
    }

    fun setSelectedItem(navigationItemId: Int) {
        if (navigationItemId != 0) {
            val navView = findViewById<BottomNavigationView>(getNavigationViewId())
            navView?.post { navView.selectedItemId = navigationItemId }
        }
    }
}