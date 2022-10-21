package com.jwdfhi.meal_up.firebase

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MealUpFirebaseCloudMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        // Log.d("TAG", "onMessageReceived: ${message.data}")
    }

}