package com.github.nyanfantasia.shizurunotes.data

interface Item {
    val itemId: Int
    val itemName: String
    val iconUrl: String
    val itemType: ItemType
}