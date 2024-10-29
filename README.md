
# Cinema-Connect

A modern user-friendly movie ticket booking platform that allows users to explore movies, book seats, and manage as well as share their favorite films in one place.


![Logo](https://firebasestorage.googleapis.com/v0/b/cinema-connect.appspot.com/o/GitHub%20essentials%2FCinema-Connect_logo.webp?alt=media&token=48407a63-579f-4149-81c4-b65dc89bf8df)


## Features 

- **Explore Movies**: Browse top and upcoming movies with interactive, auto-sliding banners.
- **Movie Details**: View detailed information about each movie, including description, IMDb rating, and trailers.
- **Favorite Movies**: Bookmark movies for quick access, with a dedicated favorites section.
- **Seat Selection**: Pick and book your favorite seats easily.
- **Share Movie Details**: Share your favorite movies with friends directly from the app.
- **Search Functionality**: Find movies by title quickly using the built-in search bar.


## 📱 Screenshots

### App Overview

| **Intro Screen** | **Home Page** |
|-------------------|---------------|
| ![Intro Screen](https://firebasestorage.googleapis.com/v0/b/cinema-connect.appspot.com/o/GitHub%20essentials%2FCinema-Connect-Intro_page.png?alt=media&token=c7ac99ff-6c1a-4878-8835-ad262e36c456) | ![Home Page](https://i.imgur.com/SxoeWNk.png) |
| *The introductory screen showcasing the app's branding.* | *The home page displaying featured movies.* |

### Additional Features

| **Movie Details** | **Seat Selection** |
|--------------------|---------------------|
| ![Movie Details](https://i.imgur.com/3wMSnvY.png) | ![Seat Selection](https://firebasestorage.googleapis.com/v0/b/cinema-connect.appspot.com/o/GitHub%20essentials%2FCinema-Connect_Seat-Selection.png?alt=media&token=d6bc6db8-9b6e-4e11-8c6f-10f6c76e78f7) |
| *Detailed view of a selected movie.* | *Selecting seats for a movie.* |

### More Features

| **Favorites Page** | **Share Sheet** |
|---------------------|------------------|
| ![Favorites Page](https://firebasestorage.googleapis.com/v0/b/cinema-connect.appspot.com/o/GitHub%20essentials%2FCinema-Connect_Favorites-Page.png?alt=media&token=ea5f4c5c-771f-4805-9e64-2fc2e3dfa0f2) | ![Share Sheet](https://firebasestorage.googleapis.com/v0/b/cinema-connect.appspot.com/o/GitHub%20essentials%2FCinema-Connect-Share_Sheet.jpg?alt=media&token=23ef2780-dee4-4c91-bb36-06dc37fb7448) |
| *The page where users can view their favorite films.* | *Sharing options for movies.* |

## 🚀 Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/Amlan101/Cinema-Connect.git

2. **Open in Android Studio:**
- Launch Android Studio and open the cloned project.
3. **Set up Firebase:**
- Add your google-services.json file from Firebase to the /app directory.
4. **Sync Project:**
- Run a Gradle sync in Android Studio to download dependencies.
5. **Download APK** *(optional)*:
 - If you want to quickly test the app without setup, download the APK from [here](https://drive.google.com/file/d/1jUPdtKY1115D8FsmkuKs_5vNzEu-fIA4/view?usp=drive_link).
## 🛠️ Tech Stack

- **Kotlin** - Main programming language for Android development
- **XML** - UI layout design
- **Firebase** - Real-time database for movie and user data
- **Room Database** - Local storage for offline data management
- **Glide** - Image loading and caching
- **Jetpack Libraries** - ViewModel, LiveData, Navigation


## 📘 Lessons Learned

Building Cinema Connect was a rewarding experience in mobile development, providing hands-on practice with both core and advanced Android concepts. Here are some key takeaways:

- **Room Database Management**: Implemented Room for efficient local data storage, enabling offline access to user favorites and improving app responsiveness.
- **Firebase Integration**: Integrated Firebase’s real-time database for seamless data syncing, helping to manage movie listings and user information dynamically.
- **Asynchronous Data Handling**: Used Kotlin Coroutines to manage background tasks like database and network calls, ensuring a smooth, lag-free user experience.
- **MVVM Architecture**: Structured the app using the Model-View-ViewModel pattern, promoting separation of concerns, testability, and modularity in the codebase.
- **Custom Glide Targets**: Leveraged `CustomTarget` in Glide to load images as `Bitmap` objects, allowing precise control for sharing images via intents.
- **Share Button Feature**: Implemented a customizable share button, allowing users to share movie details (text and images) across various apps, enhancing the app's social engagement.
- **Complex UI Handling**: Created a modern, user-friendly UI with features like auto-sliding banners, grid layouts, and interactive buttons, enhancing usability and visual appeal.
