package com.sunasterisk.music_72.screen.service

import android.app.DownloadManager
import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.core.os.bundleOf
import com.sunasterisk.music_72.BuildConfig
import com.sunasterisk.music_72.data.model.Track
import com.sunasterisk.music_72.utils.Constants

class DownloadTrackService : IntentService(TAG) {

    override fun onHandleIntent(intent: Intent?) {
        val track = intent?.getBundleExtra(EXTRA_BUNDLE)?.getParcelable<Track>(EXTRA_TRACK)
        val urlDownload = track?.let { it.downloadUrl + AUTHORIZED_SERVER }
        val path =
            String.format(
                BASE_FILE_PATH,
                String.format(BASE_FORMAT_TRACK, track?.id, track?.title, track?.user?.username)
            )
        val downloadManager =
            getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        DownloadManager.Request(Uri.parse(urlDownload)).apply {
            setTitle(track?.title)
            setAllowedOverRoaming(true)
            allowScanningByMediaScanner()
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC, path)
            downloadManager.enqueue(this)
        }
    }

    companion object {
        private val TAG = DownloadTrackService::class.java.name
        private const val EXTRA_BUNDLE = "EXTRA_BUNDLE"
        private const val EXTRA_TRACK = "EXTRA_TRACK"
        private const val AUTHORIZED_SERVER =
            "?" + Constants.CLIENT_ID + "=" + BuildConfig.SOUNDCLOUD_API_KEY
        private const val BASE_FORMAT_TRACK = "%s,%s,%s"
        private const val BASE_FILE_PATH = "/SunSound/%s.mp3"

        fun getIntent(context: Context?, track: Track?) =
            Intent(context, DownloadTrackService::class.java).apply {
                putExtra(EXTRA_BUNDLE, bundleOf(EXTRA_TRACK to track))
            }
    }
}
