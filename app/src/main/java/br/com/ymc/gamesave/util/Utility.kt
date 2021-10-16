package br.com.ymc.gamesave.util

object Utility
{

}

fun String.createImageURL(imageId: String): String
{
    return "$this$imageId.jpg"
}

fun Float.valueToRating() : Float
{
    return (this / 10) / 2
}
