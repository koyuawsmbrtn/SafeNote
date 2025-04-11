# SafeNote

A minimal, secure note-taking application for Android with panic button support.

[![Build APK](https://github.com/koyuawsmbrtn/SafeNote/actions/workflows/build.yaml/badge.svg)](https://github.com/koyuawsmbrtn/SafeNote/actions/workflows/build.yaml)

## Features

- Simple, distraction-free note editor
- Dark theme for eye comfort
- Monospace font for better readability
- Automatic saving
- Panic button integration with Guardian Project apps
- No permissions required
- No data collection or tracking

## Security Features

- Notes are stored in app-private SharedPreferences
- Panic button support to quickly erase all notes
- No cloud backup or sync
- No screenshots or recent apps preview

## Download

You can download the latest APK from:
- [Latest Release](https://github.com/koyuawsmbrtn/SafeNote/releases/latest)
- [Build Artifacts](https://github.com/koyuawsmbrtn/SafeNote/actions) (requires GitHub login)

## Building

Requirements:
- Android Studio or IntelliJ IDEA
- JDK 11 or newer
- Android SDK 35
- Gradle 8.11.1 or newer

To build:
```bash
./gradlew assembleDebug
```

## Usage

1. Launch the app
2. Start typing your notes
3. Notes are automatically saved
4. To clear all data, trigger panic action via Guardian Project apps

## Compatibility

- Minimum Android version: 6.0 (API 24)
- Target Android version: Android 14 (API 35)
- Dark theme only

## License

This project is open source and available under the MIT License.

## Privacy

SafeNote does not:
- Request any permissions
- Connect to the internet
- Store data outside app storage
- Create backups
- Share data with other apps

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## Acknowledgments

- Guardian Project for panic kit integration
- Android Jetpack libraries