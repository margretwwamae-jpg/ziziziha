# Wealth Discipline Kenya — Android project

This is a native Android starter app with local expense storage, salary editing, an expense history, and basic wealth-discipline guidance. Default salary is KSh 35,000; rent and Wi-Fi are shown as starting bill guidance. Data stays on the device.

## Build an APK

**This ZIP is Android source code, not a compiled APK.** This environment does not have the Android SDK/build tools needed to compile the APK directly.

### Easiest cloud build route (requires GitHub)
1. Create a new GitHub repository.
2. Upload the contents of this folder to the repository (not the outer folder itself).
3. Open the repository's **Actions** tab.
4. Select **Build Android APK** and click **Run workflow**, or push the files to the `main` branch.
5. When the workflow finishes, open the completed run and download the artifact named `Wealth-Discipline-Kenya-APK`.
6. Extract the artifact; it contains `app-debug.apk`. Transfer it to your Android phone and install it. Android may ask you to allow installation from that source.

### Build locally
Open this folder in Android Studio with Android SDK Platform 35 installed, then build the `app` debug APK.

## Important
This is a simple starter implementation, not a regulated financial product. It does not connect to bank or M-Pesa accounts, execute investments, or provide live investment rates. Keep your phone protected and use the app's on-device data carefully.
