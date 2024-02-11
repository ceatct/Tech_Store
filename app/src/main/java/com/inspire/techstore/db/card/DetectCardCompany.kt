package com.inspire.techstore.db.card

class DetectCardCompany {

    private val cardPatterns = mapOf(
        "Visa" to Regex("^4[0-9]{12,18}$"),
        "Mastercard" to Regex("^5[1-5][0-9]{14}$"),
    )

    fun getCardType(cardNumber: String): String {
        for ((key, value) in cardPatterns) {
            if (value.matches(cardNumber)) {
                return key
            }
        }
        return "Undefined"
    }
}
