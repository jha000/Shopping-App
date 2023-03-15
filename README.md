# Fresh Yard - Online E-commerce Delivery Application

Fresh Yard is a mobile application built to enable users to purchase products online, get them delivered to their location and track the order. The app also includes features like product authentication, payment gateway, image picker, call support, referral program, and more. The app is built using Kotlin, XML, Material Design, Firebase, Room DB, SharedPreferences, and Geocoder.

## Technologies Used

* Kotlin: The primary programming language used to develop the application.
* XML: Used for designing user interface components.
* Material Design: Used for implementing visually appealing and consistent UI components.
* Firebase: Used for authentication, database management, and push notifications.
* Room DB: Used for data persistence and local data management.
* SharedPreferences: Used for storing and retrieving application data.
* ImagePicker: Used for selecting and uploading product images.
* Geocoder: Used for accessing the device's location to enable delivery tracking.

## Dependencies

Here's a list of dependencies used in the app:

```python
dependencies {
   implementation 'androidx.appcompat:appcompat:1.7.0-alpha01'
    implementation 'com.google.android.material:material:1.6.1'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    implementation 'com.google.firebase:firebase-auth:19.3.2'
    implementation platform('com.google.firebase:firebase-bom:31.0.2')
    implementation 'com.google.firebase:firebase-database'
    implementation 'com.google.android.gms:play-services-location:17.0.0'
    implementation 'com.squareup.picasso:picasso:2.71828'
    implementation'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation'com.squareup.retrofit2:retrofit:2.9.0'
    implementation("com.squareup.okhttp3:okhttp:4.4.0")
    implementation 'com.razorpay:checkout:1.6.4'
    implementation("androidx.multidex:multidex:2.0.1")
    implementation 'androidx.room:room-runtime:2.2.5'
    implementation 'com.facebook.shimmer:shimmer:0.5.0'
    implementation 'com.google.firebase:firebase-storage-ktx:20.1.0'
    annotationProcessor 'androidx.room:room-compiler:2.2.5'
    implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
    annotationProcessor 'androidx.lifecycle:lifecycle-compiler:2.2.0'
    implementation 'com.hbb20:ccp:2.6.1'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    implementation 'de.hdodenhof:circleimageview:3.1.0'
    implementation 'com.airbnb.android:lottie:3.7.0'
    implementation 'com.google.firebase:firebase-config:20.0.3'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
}

```

## Features

* Authentication: Users can create an account, sign in, and manage their profile.
* Product Purchase: Users can browse and purchase products from the app.
* Payment Gateway: Users can make secure online payments using popular payment gateways.
* Order Tracking: Users can track the status of their orders and receive real-time updates on delivery.
* ImagePicker: Users can select and upload product images for display.
* Call Support: Users can contact customer support using the app.
* Feedback: Users can provide feedback on their experience with the app and the products.
* Referral Program: Users can refer friends and earn rewards.

## Installation

To install the app, follow these steps:

1. Download the app from the Google Play Store.
2. Install the app on your Android device.
3. Launch the app and follow the on-screen instructions to sign up or sign in to your account.
