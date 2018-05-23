package com.dreampany.frame.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater


/**
 * Created by Hawladar Roman on 5/22/2018.
 * BJIT Group
 * hawladar.roman@bjitgroup.com
 */
abstract class BaseMenuFragmentKt: BaseFragmentKt() {

    protected var menu: Menu? = null
    protected var inflater: MenuInflater? = null

    open fun getMenuId(): Int {
        return 0
    }

    protected abstract fun onMenuCreated(menu: Menu)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        this.menu = menu
        this.inflater = inflater
        val menuId = getMenuId()

        if (menuId != 0) {
            menu!!.clear()
            inflater!!.inflate(menuId, menu)
            binding.root.post { onMenuCreated(menu) }
        }
    }
}