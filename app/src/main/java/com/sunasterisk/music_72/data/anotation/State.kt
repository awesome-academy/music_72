package com.sunasterisk.music_72.data.anotation

import androidx.annotation.IntDef

@IntDef(State.PLAY, State.PAUSE)
annotation class State {
    companion object {
        const val PLAY = 0
        const val PAUSE = 1
    }
}
