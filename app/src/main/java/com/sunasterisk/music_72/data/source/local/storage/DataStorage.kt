package com.sunasterisk.music_72.data.source.local.storage

import android.os.Environment
import io.reactivex.Observable
import java.io.File

class DataStorage {

    fun getAudios(): Observable<List<String>> {
        return Observable.create {
            it.onNext(getAudioByDataStorage())
            it.onComplete()
        }
    }

    private fun getAudioByDataStorage(): MutableList<String> {
        val audios = mutableListOf<String>()
        val path = File(Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_MUSIC).path, CHILD_FILE_PATH
        )
        val files = path.listFiles()
        files?.forEach {
            if(it.isFile && isFileMp3(it)){
                audios.add(it.name)
            }
        }
        return audios
    }

    private fun isFileMp3(file: File) = file.name.endsWith(FORMAT_TYPE_MP3)

    companion object {
        private const val FORMAT_TYPE_MP3 = ".mp3"
        private const val CHILD_FILE_PATH = "/SunSound/"
    }
}
