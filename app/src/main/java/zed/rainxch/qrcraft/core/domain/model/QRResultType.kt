package zed.rainxch.qrcraft.core.domain.model

enum class QRResultType(val title: String) {
    TEXT(title = "Text"),
    LINK(title = "Link"),
    CONTACT(title = "Contact"),
    PHONE_NUMBER(title = "Phone Number"),
    GEOLOCATION(title = "Geolocation"),
    WIFI(title = "Wi-Fi"),
}