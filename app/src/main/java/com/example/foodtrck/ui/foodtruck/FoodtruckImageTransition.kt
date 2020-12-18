package com.example.foodtrck.ui.foodtruck

import android.transition.ChangeBounds
import android.transition.ChangeImageTransform
import android.transition.ChangeTransform
import android.transition.TransitionSet

class FoodtruckImageTransition: TransitionSet() {
    init {
        ordering = ORDERING_TOGETHER
        addTransition(ChangeBounds())
            .addTransition(ChangeTransform())
            .addTransition(ChangeImageTransform())
    }
}