package com.metoer.clocktracker.base

import androidx.fragment.app.Fragment

abstract class BaseFragment:Fragment() {

    open fun hideBar(){}
    open fun visibleBar(){}
}