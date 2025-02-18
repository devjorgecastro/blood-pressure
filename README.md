# Alternova Android Test 🌟
This is a technical test for the selection process for an Android Engineer in [Alternova](https://alternova.com/)

<br />

## Setup

* Clone Repository from `develop` branch

If the Android SDK is not detected when compiling the project, then you need to add the path to the local.properties file (create it if it doesn't exist)
Example:
```
sdk.dir=/Users/user/Library/Android/sdk
```

<br /><br />

## Features

-   **Jetpack Compose**: The user interface has been developed using Jetpack Compose, allowing for modern and flexible UI development in Kotlin.

-   **Clean Architecture**: The project follows Clean Architecture principles to maintain a modular and scalable structure.

-   **MVVM**: MVVM pattern is used to efficiently manage data flow and user interaction.

-   **Version Catalog**: A version catalog is used to centrally manage project dependencies.

-   **Dark Mode**: Dark Mode has been implement to enhance your visual experience. Switch between light and dark modes according to your preference.

-   **100% Kotlin**: All project code is written in Kotlin, leveraging the benefits of this modern language.

-   **Unit Tests (Junit5, MockK)**: Unit tests are included to ensure code quality and project functionality.

<br />

## Project Architecture
Following the guidelines proposed by the Android team to date, I have decided to carry out the test applying an architecture that allows us to have a separation to the point of having high cohesion and low coupling. Below you can see a general representation of how the application layers and the main components of the communication between them are separated. Note⚠️: For this test a remote repository was not used.
<img src="https://github.com/devjorgecastro/yape-android-test/blob/develop/repo-assets/clean-architecture.png" width="900" />

#### Some Patterns
* MVVM
* Unidirectional Data Flow
* Repository
* Strategy

## Some used libraries
- Room
- Kotlin Coroutines
- Navigation Compose
- JUnit 5
- MocKk
