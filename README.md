# Journal
A journal to keep track of thourths and feelings 

## Getting Started
## Installation

* Download:

    $ git clone https://github.com/ThePlatinum/Jornal

* Import Project by Android Studio Menu > File > Import Project...
* Sync grade and build 

## How to import Journal to your project

Copy Journal to Your Android Studio Project directry

Add codes into YourApp/build.gradle

    YourApp/build.gradle

        dependencies {
            ...
            compile 'com.google.firebase:firebase-core:10.0.1'
            compile 'com.google.firebase:firebase-crash:10.0.1'
            compile 'com.google.firebase:firebase-messaging:10.0.1'
            compile 'com.google.android.gms:play-services-auth:10.0.1'
            ...
        }
        
Edit settings.gradle

   settings.gradle

        include ':app'
        
> Create a Firebase Project at:
     console.firebase.com
    
 and setUp for Authentication

Click "Sync Project with Gradle Files" and "Rebuild Project" at Android Studio Menu.

> Run YourApp by Android Studio Menu > Run > Run YourApp.



## System Minimum Requirements
* 4 gig RAM
* 64 bit
* Java Development Kit (JFK) 8.1
    

## Built With
* Android Studio 2.3.3 - IDE
* Maven - Dependency Managment

<h3> Author</h3>
* Adesina Emmanuel

<h3>Support</h3>

Please take a look at [guide](https://developers.google.com). Most setup issues can be solved by following this guide.

If your question is not answered by the troubleshooting guide, post your question to [stackoverflow.com](https://stackoverflow.com/questions/tagged/gradle-builds).
