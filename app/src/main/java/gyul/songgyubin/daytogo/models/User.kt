package gyul.songgyubin.daytogo.models

data class User(
    val uid: String,
    val email: String,
    val locationList: MutableList<Location> = mutableListOf()
)