package com.sunasterisk.music_72.data.source.local

import com.sunasterisk.music_72.data.source.TrackDataSource
import com.sunasterisk.music_72.data.source.local.storage.DataStorage

class TrackLocalDataSource : TrackDataSource.Local {
    private val dataStorage: DataStorage by lazy { DataStorage() }

    override fun getAudiosDataStorage() = dataStorage.getAudios()
}
