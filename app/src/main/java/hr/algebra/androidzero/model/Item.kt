package hr.algebra.androidzero.model

data class Item(
    var _id: Long?,
    val title: String,
    val explanation: String,
    val picturePath: String,
    val price: Double,       // Dodano polje za tvoju cijenu
    var read: Boolean
)