package com.dreampany.frame.vm

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.dreampany.frame.rx.RxFacade


/**
 * Created by Hawladar Roman on 5/22/2018.
 * BJIT Group
 * hawladar.roman@bjitgroup.com
 */
class BaseViewModelKt<T>(application: Application, facade: RxFacade) : AndroidViewModel(Application()) {
}