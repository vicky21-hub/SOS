# SOS Emergency Protection App 🆘

A modern, reliable Android emergency-safety application designed for instant distress alerts, live GPS location sharing, automated emergency calls, and background protection monitoring.

---

## 📱 Features

* **One-Tap SOS Emergency Alert:** Instantly triggers distress protocol with a prominent home-screen emergency button.
* **Live GPS Location Sharing:** Automatically fetches high-accuracy GPS coordinates and generates Google Maps links (`https://maps.google.com/?q=lat,lng`) sent via SMS to all emergency contacts.
* **Shake Gesture Trigger (`ShakeService`):** Background accelerometer sensor monitoring automatically activates SOS when the device is vigorously shaken.
* **Power Button SOS (`PowerButtonService`):** Monitors rapid power button / screen toggles to silently trigger emergency alerts.
* **Emergency Audio Recording (`AudioRecordService`):** Captures background audio during emergency situations.
* **Automated Emergency Calling:** Initiates immediate call placement to designated emergency services (`112`).
* **Emergency Contact Management:** Add, view, and delete multiple emergency contacts seamlessly.
* **Custom Distress Message:** Customize emergency SMS content saved in application preferences.
* **Modern Material Design UI:** Designed with high contrast emergency safety theme, rounded Material cards, status indicators, and clean typography.

---

## 🛠️ Tech Stack & Architecture

* **Language:** Java
* **UI Framework:** Material Components (Material 3 / Material Design), ConstraintLayout, NestedScrollView, RecyclerView
* **Location Services:** Google Play Services Location (`FusedLocationProviderClient`)
* **Background Processing:** Android Services, Foreground Services with Notification Channels, BroadcastReceivers (`BootReceiver`, `PowerButtonReceiver`, `BatteryReceiver`)
* **Storage:** SharedPreferences (`ContactManager`, `PreferencesHelper`)
* **Minimum SDK:** API 26 (Android 8.0)
* **Target SDK:** API 35 (Android 15)

---

## 🔐 Permissions Used

* `ACCESS_FINE_LOCATION` / `ACCESS_COARSE_LOCATION` - Live GPS location tracking for emergency SMS
* `SEND_SMS` - Dispatching emergency text alerts to contacts
* `CALL_PHONE` - Making emergency assistance phone calls
* `READ_CONTACTS` - Selecting emergency contacts from device address book
* `RECORD_AUDIO` - Background emergency audio capture
* `FOREGROUND_SERVICE` - Persistent background safeguards (Shake detection, Power button monitor)
* `RECEIVE_BOOT_COMPLETED` - Auto-restarting background monitoring services upon device reboot

---

## 🚀 Getting Started

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/vicky21-hub/SOS.git
   ```
2. **Open in Android Studio:**
   * Open Android Studio and select **Open**.
   * Navigate to the cloned `SOS` project directory.
3. **Build & Run:**
   * Connect an Android device or start an emulator.
   * Click **Run 'app'** or execute `./gradlew :app:assembleDebug`.

---

## 📄 License

This project is open source and available for personal safety development and customization.
