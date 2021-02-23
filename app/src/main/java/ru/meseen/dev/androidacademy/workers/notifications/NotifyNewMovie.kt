package ru.meseen.dev.androidacademy.workers.notifications

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.CalendarContract
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.NotificationManagerCompat.IMPORTANCE_HIGH
import androidx.core.net.toUri
import ru.meseen.dev.androidacademy.MainActivity
import ru.meseen.dev.androidacademy.R

class NotifyNewMovie(
    context: Context,
    private val notifiable: Notifiable,
    bitmap: Bitmap
) : MovieNotifiable {

    companion object {
        private const val CHANNEL_DEFAULT = "CHANNEL_DEFAULT"
        private const val REQUEST_CONTENT = 8880
    }

    private val timeStamp = System.currentTimeMillis()
    private val contentUri = "https://www.themoviedb.org/movie/${notifiable.movieId}".toUri()

    private val pendingIntent = PendingIntent.getActivities(
        context,
        REQUEST_CONTENT,
        arrayOf(
            Intent(context, MainActivity::class.java)
                .setAction(Intent.ACTION_VIEW)
                .setData(contentUri)
        ),
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    private val notificationManagerCompat: NotificationManagerCompat =
        NotificationManagerCompat.from(context)

    private val channel =
        NotificationChannelCompat.Builder(CHANNEL_DEFAULT, NotificationCompat.PRIORITY_HIGH)
            .setName(context.resources.getString(R.string.channel_new_movie))
            .setDescription(context.resources.getString(R.string.channel_new_movie_description))
            .setImportance(IMPORTANCE_HIGH)
            .build()
            .also {
                notificationManagerCompat.createNotificationChannel(it)
            }

    private val action = NotificationCompat.Action.Builder(
        R.drawable.ic_round_calendar,
        context.getString(R.string.notification_add_to_calendar),
        PendingIntent.getActivity(
            context, REQUEST_CONTENT, Intent(Intent.ACTION_INSERT).also {
                it.data = CalendarContract.Events.CONTENT_URI
                it.putExtra(CalendarContract.Events.TITLE, notifiable.title)
                it.putExtra(
                    CalendarContract.Events.DESCRIPTION,
                    "${notifiable.description} url: $contentUri"
                )
            }, PendingIntent.FLAG_UPDATE_CURRENT
        )

    ).setAllowGeneratedReplies(true)
        .build()

    private val bigIcon = BitmapFactory.decodeResource(context.resources,R.drawable.ic_launcher)

    private val bigPicStyle = NotificationCompat.BigPictureStyle()
        .bigLargeIcon(bigIcon)
        .bigPicture(bitmap) // а вот и изображение

    private var notification = NotificationCompat.Builder(context, CHANNEL_DEFAULT)
        .setSmallIcon(R.drawable.ic_launcher)
        .setContentTitle("${context.resources.getString(R.string.notification_new_movie)} ${notifiable.title}")
        .setContentText(notifiable.description)
        .setContentIntent(pendingIntent)
        .setCategory(Notification.CATEGORY_RECOMMENDATION) // Может использоваться системой для ранжирования/фильтрации
        .setOnlyAlertOnce(true)
        .setAutoCancel(true)
        .setStyle(bigPicStyle)
        .addAction(action)
        .setLargeIcon(bitmap)
        .setWhen(timeStamp)
        .setPriority(NotificationCompat.PRIORITY_HIGH) // для поддержки API level 25 и ниже



    override fun show(): NotifyNewMovie {
        notificationManagerCompat.notify(
            Tag.NEW_MOVIE.toString(),
            Tag.NEW_MOVIE.uniqueIDN,
            notification.build()
        )
        return this
    }


    override fun dismiss(): NotifyNewMovie {
        notificationManagerCompat.cancel(
            Tag.NEW_MOVIE.toString(),
            Tag.NEW_MOVIE.uniqueIDN
        )
        return this
    }
}

enum class Tag(val uniqueIDN: Int) {
    NEW_MOVIE(51818)
}




