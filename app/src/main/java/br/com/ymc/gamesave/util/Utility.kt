package br.com.ymc.gamesave.util

import android.accounts.NetworkErrorException
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import java.io.IOException
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.util.*

object Utility
{
    fun isOnline(context: Context): Boolean
    {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (connectivityManager != null)
        {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null)
            {
                when
                {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->
                    {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->
                    {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ->
                    {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                        return true
                    }
                }
            }
        }
        return false
    }
}

fun String.createImageURL(imageId: String): String
{
    return "$this$imageId.jpg"
}

fun Float.valueToRating(): Float
{
    return (this / 10) / 2
}

fun Long.toDate() : String
{
    val sdf = SimpleDateFormat("dd-MM-yyyy", Locale("pt", "BR"))
    val date = Date(this * 1000)
    return sdf.format(date)
}

fun Int.handleError() : String
{
    return when(this)
    {
        400 -> "Error $this Bad request"
        else -> "Unknown error"
    }
}

fun Exception.handleError() : String
{
    return when(this)
    {
        is UnknownHostException -> "No internet connection"
        is NetworkErrorException -> "Internet connection error"
        is IOException -> this.localizedMessage ?: "Unknown error"
        else -> "Unknown error"
    }
}
