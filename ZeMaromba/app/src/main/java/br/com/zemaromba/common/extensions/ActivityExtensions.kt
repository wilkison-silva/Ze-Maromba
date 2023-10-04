package br.com.zemaromba.common.extensions

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri


fun Activity.openVideoInYoutubeOrBrowser(urlLink: String) {
    val appIntent = Intent(
        Intent.ACTION_VIEW, Uri.parse(urlLink)
    )
    val webIntent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(urlLink)
    )
    try {
        startActivity(appIntent)
    } catch (ex: ActivityNotFoundException) {
        startActivity(webIntent)
    }
}