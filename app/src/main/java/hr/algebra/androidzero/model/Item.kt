package hr.algebra.androidzero.model

data class Item(
    var _id: Long?,
    val title: String,
    val explanation: String,
    val picturePath: String,
    val price: Double,
    val rate: Double,    // Novo: Prosječna ocjena (npr. 3.9)
    val count: Int,      // Novo: Broj glasova (npr. 120)
    var read: Boolean
)