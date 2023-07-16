package br.com.zemaromba.common.extensions

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri


fun Activity.openVideoInYoutubeOrBrowser(videoId: String) {
    val appIntent = Intent(
        Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$videoId")
    )
    val webIntent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("http://www.youtube.com/watch?v=$videoId")
    )
    try {
        startActivity(appIntent)
    } catch (ex: ActivityNotFoundException) {
        startActivity(webIntent)
    }
}