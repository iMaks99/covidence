package com.sr.covidence.profile.mask

import android.app.Notification
import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.sr.covidence.MainActivity
import com.sr.covidence.R
import kotlinx.android.synthetic.main.custom_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_mask.*


class MaskFragment : Fragment() {

    private lateinit var pref: SharedPreferences

    private var countMask = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mask, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = context!!.getSharedPreferences("sharedPreferences", AppCompatActivity.MODE_PRIVATE)

        val v = layoutInflater.inflate(R.layout.custom_toolbar, null)
        mask_toolbar.addView(v)
        v.profile_title.text = "Информация о масках"
        v.profile_back_btn.setOnClickListener {

            pref.edit().putString("countMask", countMask.toString()).apply()

            fragmentManager!!.popBackStack()
        }
        v.profile_settings_image.visibility = View.GONE

        countMask = pref.getString("countMask", "0")!!.toInt()
        count_mask.setText(pref.getString("countMask", "0"), TextView.BufferType.EDITABLE)

        buttonMinus.setOnClickListener {
            if (countMask > 0) {
                countMask--
                count_mask.setText(countMask.toString(), TextView.BufferType.EDITABLE)
            }
        }

        bottomAdd.setOnClickListener {
            countMask++
            count_mask.setText(countMask.toString(), TextView.BufferType.EDITABLE)
        }

        set_timer_btn.setOnClickListener {

            val intent = Intent(context, MainActivity::class.java)
            val contentIntent =
                PendingIntent.getBroadcast(
                    context,
                    0,
                    intent,
                    0
                )

            val b: NotificationCompat.Builder = NotificationCompat.Builder(context)

            b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_profile_mask)
                .setTicker("Hearty365")
                .setContentTitle("3 часа прошло")
                .setContentText("Пора сменить маску. Не забудь")
                .setDefaults(Notification.DEFAULT_LIGHTS or Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent)
                .setChannelId("Covidence")
                .setContentInfo("Info")

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                (context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
                    NotificationChannel(
                        "Covidence",
                        "Covidence",
                        NotificationManager.IMPORTANCE_LOW
                    )
                )
            }

            (context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(
                1,
                b.build()
            )

        }

    }

}
