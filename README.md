# Rick and Morty Location and Character App

## Description

This is an app that displays Rick and Morty locations and characters. Users can view a list of locations and characters, and click on a location to view the characters associated with that location. They can also click on a character to view more details about that character.

## Tasks

### Task 1: Basic Application Setup

- [x] A functional basic application will be prepared.

### Task 2: Splash Screen Development

- [x] This page should only be displayed at startup.
- [x] It should contain an animation or an image that introduces the application.
- [x] There should be a welcome message on the page. When the user opens the application for the first time, it should say "Welcome!". In subsequent launches, it should say "Hello!".

### Task 3: Home Page Creation

- [x] The page should consist of a title/logo, a scrolling horizontal list, and a vertical list.

### Task 4: Populating the Scrolling List

- [x] The horizontal list should include Rick and Morty locations.
- [x] The content should be pulled from here: https://rickandmortyapi.com/documentation/#locationschema
- [x] Only the first page should be listed (20 locations).
- [x] The design of selected and unselected locations should be different.

### Task 5: Populating the Vertical List

- [x] Characters associated with the location retrieved from the horizontal list should be listed.
- [x] The location response contains urls for characters (residents). The id's from these urls will be parsed to retrieve character details from this source: https://rickandmortyapi.com/documentation/#get-multiple-characters

### Task 6: Differentiating Items in the Vertical List

- [x] Item designs should vary based on the gender of the listed characters: male, female, genderless, or unknown (as seen in the mockup).

### Task 7: Adding Detail Pages

- [x] The page with the provided design should be developed.
- [x] Clicking on an item in the vertical list should open this page.

### Task 8: Adding Lazy Load to the Scrolling List

- [x] When the horizontal list on the main page is scrolled to the end (right), the next page should be loaded.
- [x] A loading item should be added to the end of the list during loading.

## App videos Portrait/Landscape (PIXEL XL API 33)

<img width="100" height="100" src="https://user-images.githubusercontent.com/38860392/227779990-9b87c7f6-42b5-465c-b105-a8fce79fcdb8.mp4" />

## Tech stack & Open-source libraries
- 100% [Kotlin](https://kotlinlang.org/) based + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) and [Flow](https://developer.android.com/kotlin/flow) & [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - A single-activity architecture, using the [Jetpack Navigation](https://developer.android.com/guide/navigation) to manage transactions.
  - [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform an action when lifecycle state changes
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
  - [Repository](https://developer.android.com/topic/architecture/data-layer) - Located in data layer that contains application data and business logic.
  - ViewBinding & DataBinding - allows you to more easily write code that interacts with views.



- [Android Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Dependency Injection Library
- [Retrofit](https://square.github.io/retrofit/) A type-safe HTTP client for Android and Java
- [Moshi](https://github.com/square/moshi) - A modern JSON library for Android and Java.
- [Glide](https://bumptech.github.io/glide/doc/download-setup.html) Glide is a fast and efficient open source media management and image loading framework for Android that wraps media decoding, memory and disk caching, and resource pooling into a simple and easy to use interface.

## Architecture
This app uses [***MVVM (Model View View-Model)***](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) architecture
![](https://miro.medium.com/v2/resize:fit:1000/format:webp/1*itYWsxQTfq7xTuvIMrVhYg.png)

## API
This app uses the [Rick and Morty API](https://rickandmortyapi.com/documentation/#introduction) to get the data.
