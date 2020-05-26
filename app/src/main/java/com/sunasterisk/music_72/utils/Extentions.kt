package com.sunasterisk.music_72.utils

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Disposable.addTo(compositeDisposable: CompositeDisposable)
        = apply { compositeDisposable.add(this) }