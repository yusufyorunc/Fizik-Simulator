# Fizik Simulator

FizikSimulator is an Android application under active development. This README provides a short overview, current development status, and basic instructions to build and run the project locally.

## Project status

- Status: In active development (alpha).
- Many features are still incomplete and APIs, UI and internal structure may change frequently.
- This repository is suitable for contributors who want to help implement features, fix bugs, or improve tests and documentation.

## Overview

FizikSimulator aims to provide physics simulation examples and interactive visualizations on Android devices. The project is implemented as a native Android app (Kotlin + optional C++ native components) and uses the standard Gradle build system.

## Key (planned) features

- Interactive simulations of basic physics concepts
- Simple UI for changing simulation parameters
- High-performance native code paths for calculations (optional)
- Export or save simulation results (planned)

> Note: Feature set is evolving. Check issues and project board for up-to-date tasks.

## Getting started (development)

Prerequisites:

- Java JDK 11 or later
- Android SDK and build tools installed (via Android Studio)
- Gradle wrapper is included in the repo (no global Gradle required)
- Windows (cmd.exe) instructions shown below; similar steps work on macOS/Linux using the included `gradlew` script

Quick build and run (Windows, from project root):

```cmd
:: Build debug APK
\.\gradlew.bat assembleDebug

:: Install and run on a connected device or emulator
\.\gradlew.bat installDebug
```

From Android Studio:

1. Open the project by selecting the root folder.
2. Let Android Studio sync Gradle and download dependencies.
3. Use the Run/Debug configuration to install on a device or emulator.

## Running tests

The project includes basic unit and instrumentation test stubs. Run tests with Gradle:

```cmd
:: Run unit tests
\.\gradlew.bat test

:: Run connected instrumentation tests (device/emulator required)
\.\gradlew.bat connectedAndroidTest
```

## Contributing

Contributions are welcome. Typical ways to help:

- Open issues for bugs or feature requests
- Implement issues and submit pull requests
- Add or improve unit and instrumentation tests
- Improve documentation and examples

Please follow these conventions when contributing:

- Create feature branches from `main` (or the designated development branch)
- Write clear commit messages
- Include tests for new functionality when possible

