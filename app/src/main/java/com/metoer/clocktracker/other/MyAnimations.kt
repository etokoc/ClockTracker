package com.metoer.clocktracker.other

import android.content.Context
import android.view.animation.Animation
import android.view.animation.AnimationUtils

class MyAnimations() {

     fun animationSet(context:Context,animId:Int): Animation {
         val rotateOpen:Animation by lazy { AnimationUtils.loadAnimation(context,animId) }
         return rotateOpen
     }

}