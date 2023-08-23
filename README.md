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



