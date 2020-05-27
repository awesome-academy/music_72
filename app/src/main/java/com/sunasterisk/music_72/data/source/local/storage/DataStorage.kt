package com.sunasterisk.music_72.data.source.local.storage

import android.os.Environment
import com.sunasterisk.music_72.data.model.Track
import com.sunasterisk.music_72.data.model.User
import io.reactivex.Observable
import java.io.File

class DataStorage {

    fun getAudios(): Observable<MutableList<Track>> {
        return Observable.create {
            it.onNext(getAudioByDataStorage())
            it.onComplete()
        }
    }

    private fun getAudioByDataStorage(): MutableList<Track> {
        val audios = mutableListOf<Track>()
        val path = File(Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_MUSIC).path, CHILD_FILE_PATH
        )
        val files = path.listFiles()
        files?.forEach {
            if(it.isFile && isFileMp3(it)){
                audios.add(convertStringToTrack(it.name, it.path))
            }
        }
        return audios
    }

    private fun convertStringToTrack(nameFile: String, pathFile: String): Track {
        val trackElement = nameFile.split(",")
        return Track("", "",
            false, 0,
            0, "",
            trackElement[0].toInt(), 0, 0,
            pathFile, false, trackElement[1], User("", 0, trackElement[2].split(".")[0])
        )
    }

    private fun isFileMp3(file: File) = file.name.endsWith(FORMAT_TYPE_MP3)

    companion object {
        private const val FORMAT_TYPE_MP3 = ".mp3"
        private const val CHILD_FILE_PATH = "/SunSound/"
    }
}
