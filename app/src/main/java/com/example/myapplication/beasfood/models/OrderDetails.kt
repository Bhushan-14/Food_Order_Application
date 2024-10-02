package com.example.myapplication.beasfood.models

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

class OrderDetails() : Parcelable {
    var userUid: String? = null
    var userName: String? = null
    var foodName: MutableList<String>? = null
    var foodImages: MutableList<String>? = null
    var foodPrices: MutableList<String>? = null
    var foodQuantities: MutableList<Int>? = null
    var address: String? = null
    var totalPrice: String? = null
    var phoneNumber: String? = null
    var orderAccepted: Boolean? = null
    var paymentReceived: Boolean? = null
    var itemPushKey: String? = null
    var currentTime: Long = 0

    constructor(parcel: Parcel) : this() {
        userUid = parcel.readString()
        userName = parcel.readString()
        address = parcel.readString()
        totalPrice = parcel.readString()
        phoneNumber = parcel.readString()
        orderAccepted = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        paymentReceived = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        itemPushKey = parcel.readString()
        currentTime = parcel.readLong()
    }

    constructor(
        userId: String,
        name: String,
        foodItemName: ArrayList<String>,
        foodItemPrice: ArrayList<String>,
        foodItemImage: ArrayList<String>,
        foodItemQuantity: ArrayList<Int>,
        address: String,
        phone: String,
        time: Long,
        itemPushKey: String?,
        orderAccepted: Boolean,
        paymentReceived: Boolean
    ) : this() {
        this.userUid = userId
        this.userName = name
        this.foodName = foodItemName
        this.foodPrices = foodItemPrice
        this.foodImages = foodItemImage
        this.foodQuantities = foodItemQuantity
        this.address = address
        this.phoneNumber = phone
        this.totalPrice = foodItemPrice.sumBy { it.toInt() * (foodItemQuantity[foodItemPrice.indexOf(it)] ?: 0) }.toString() + '₹'
        this.currentTime = time
        this.itemPushKey = itemPushKey
        this.orderAccepted = orderAccepted
        this.paymentReceived = paymentReceived
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userUid)
        parcel.writeString(userName)
        parcel.writeString(address)
        parcel.writeString(totalPrice)
        parcel.writeString(phoneNumber)
        parcel.writeValue(orderAccepted)
        parcel.writeValue(paymentReceived)
        parcel.writeString(itemPushKey)
        parcel.writeLong(currentTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderDetails> {
        override fun createFromParcel(parcel: Parcel): OrderDetails {
            return OrderDetails(parcel)
        }

        override fun newArray(size: Int): Array<OrderDetails?> {
            return arrayOfNulls(size)
        }
    }
}
