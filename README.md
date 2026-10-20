# This is Moodify, a vibes based playlist creator (Throwing 40k down the drain if you will)

This application will require you to login to your spotify account however your credentials will be
safe as it is going to use their oauth protocol to just grab the information from their systems.

## How this application works
It will take a look at your listening history and then based on the songs you've been jamming to
it will use that information to generate a playlist. Once the playlist is generated it will look through
all the songs that you already have saved, that is related to your mood, and then it will start to prompt
you with new songs that you may potentially have interest in.

## Pages
- Home - This page will display the launching page as well as some statistics that will attempt to
guess your current mood. It also will have the buttons to generate the playlists for you as well.
- Playlists - This is where the generated playlists will display. If I find a way I may also allow
the user to play music from this page as well
- Profile - This is where all the statistics will display for the current logged in user.


## These are just notes for me since this is my first ever kotlin application

This is a Kotlin Multiplatform project targeting Android, iOS, Desktop (JVM).

* [/iosApp](./iosApp/iosApp) contains an iOS application. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

* [/shared](./shared/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - [commonMain](./shared/src/commonMain/kotlin) is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    the [iosMain](./shared/src/iosMain/kotlin) folder would be the right place for such calls.
    Similarly, if you want to edit the Desktop (JVM) specific part, the [jvmMain](./shared/src/jvmMain/kotlin)
    folder is the appropriate location.

### Running the apps

Use the run configurations provided by the run widget in your IDE's toolbar. You can also use these commands and options:

- Android app: `./gradlew :androidApp:assembleDebug`
- Desktop app:
  - Hot reload: `./gradlew :desktopApp:hotRun --auto`
  - Standard run: `./gradlew :desktopApp:run`
- iOS app: open the [/iosApp](./iosApp) directory in Xcode and run it from there.

### Running tests

Use the run button in your IDE's editor gutter, or run tests using Gradle tasks:

- Android tests: `./gradlew :shared:testAndroidHostTest`
- Desktop tests: `./gradlew :shared:jvmTest`
- iOS tests: `./gradlew :shared:iosSimulatorArm64Test`

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…