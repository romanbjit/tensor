package com.dreampany.frame.injector

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Scope


/**
 * Created by Hawladar Roman on 5/23/2018.
 * Dreampany Ltd
 * dreampanymail@gmail.com
 */

@MustBeDocumented
@Scope
@Retention(RetentionPolicy.RUNTIME)
annotation class ActivityScopeKt