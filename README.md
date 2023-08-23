# Weather Forecast

This is an application that displays the weather at a user's current location. It varies the background based on the current weather.

| Sunny                            | Cloudy                              | Rainy                        |
| ---------------------------------| ----------------------------------- | ---------------------------- |
| ![Sunny](https://github.com/wycliff/MyApplication/assets/20297562/393f67b3-a239-4cce-ad46-a52c4fac7a4a) | ![Cloudy](https://github.com/wycliff/MyApplication/assets/20297562/2076a203-ecfc-4c6d-977a-9afd274b3eb4)| ![Rainy](https://github.com/wycliff/MyApplication/assets/20297562/7ba75fe9-3b4f-4a5d-8f08-d812d3a352ec)

## Architecture and considerations

### Architecture: MVVM (Model-View-ViewModel)
That application makes use of the MVVM architecture which promotes separation of concerns and maintainability. The architecture consists of three main components:


![mvvm](https://github.com/wycliff/MyApplication/assets/20297562/6233e0c2-2d02-44b0-9aff-9efa66c27fca)

- **Model:** Represents the data and business logic of the application.
- **View:** Displays the user interface and observes ViewModel changes i.e. changes in weather based on location.
- **ViewModel:** Manages the presentation logic, interacts with the Model, and exposes observable data to the View.

### Dependency Injection: Hilt
Hilt is used for dependency injection, ensuring a modular and testable codebase. It simplifies the process of providing dependencies to various parts of the application, such as the ViewModels and repositories.

## Third-Party Dependencies

Below is an exhaustive list of third-party dependencies used in this project along with their respective purposes:

- **Hilt:** Dependency injection library that simplifies the management of dependencies in the application.
- **Retrofit:** A type-safe HTTP client for making network requests.
- **OkHttp:** An HTTP client for efficient network operations.
- **Gson:** A library for serializing and deserializing JSON data.
- **Coroutine:** Provides asynchronous programming using coroutines for managing background tasks.
- **LiveData:** A data holder class that provides observable data to UI components.
- **JUnit Jupiter:** A testing framework for writing and running unit tests in Java and Kotlin.
- **MockK:** A mocking library for Kotlin that helps with creating mock objects for testing.
- **NetworkResponseAdapter:** This library provides a Kotlin Coroutines-based Retrofit call adapter for wrapping your API responses in a NetworkResponse sealed type.
- **Timber:** A highly extensible and customizable logging library for Android.

  The project  also the library below provided by Android and Google Play Services to enhance functionality:
- **Google Play Services Location:** Library for accessing location-based services provided by Google Play Services.

## Building the Project

1. Clone the repository: `git@github.com:wycliff/MyApplication.git`
2. Open the project in Android Studio.
3. Create API keys:
   - OpenWeatherMap API Key: Sign up at https://openweathermap.org/ to obtain an API key.

4. Add the API keys to the project:
   - Open the `local.properties` file in the project root directory.
   - Add the following lines, replacing `<YOUR_API_KEY>` with your actual API keys:
     ```
     WEATHER_API_KEY = <YOUR_API_KEY>
     // 08ffdaa71cf3f2eb4178d607fb42d93a
     ```
5. Build and run the project on an emulator or physical device.

## Additional Notes
- Unit testing: Write unit tests using JUnit Jupiter and MockK for critical parts of the codebase to ensure functionality and catch bugs early.
- Location services: Leverage built-in Google Play Services Location APIs for efficient and accurate location-based services.
- Logging: Utilize Timber for efficient and customizable logging to aid in debugging and monitoring.

Thank you for taking the time to consider this project! If you have any questions or feedback, feel free to contact me.
