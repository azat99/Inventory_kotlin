package com.example.testapp_v1.model.api

import java.io.IOException

class NoConnectivityException : IOException(){

    override val message: String
        get() =
            "No network available, please check your WiFi or Data connection"

}
