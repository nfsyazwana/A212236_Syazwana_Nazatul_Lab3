package com.example.a212236_syazwana_nazatul_lab3.data

data class LocationData(
    val icon: String,
    val name: String,
    val address: String,
    val openTime: String
)

val locations = listOf(
    LocationData("🏢", "Environmental Recovery Centre", "35, Jalan P10/16, Taman Industri Selaman, 43650 Bandar Baru Bangi, Selangor", "Open: 8AM"),
    LocationData("♻️", "Mudajaya Recycle Sdn Bhd", "93, Jalan Taming 6 Taman Taming Jaya Balakong, 43300 Seri Kembangan, Selangor", "Open: 9AM"),
    LocationData("📍", "CS Recycling Sdn Bhd", "Lot 21083, Persiaran Seri Timah, Taman Seri Timah, 43300 Seri Kembangan, Selangor", "Open: 9AM"),
    LocationData("🌱", "Pusat Kitar Semula Bangi, Kajang", "1, Jalan P10/16, Seksyen 10 Bandar Baru Bangi, 43650 Bangi, Selangor", "Open: 8:30AM") ,

)
