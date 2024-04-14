# WebRTC Demo Android App

## Introduction
The WebRTC Demo app demonstrates the capabilities of real-time communication for Android devices. This project aims to showcase the implementation of the WebRTC protocol in a mobile application environment.

## Getting Started
Follow these instructions to get a copy of the project running on your local machine for development and testing purposes.

### Prerequisites
- Android Studio 4.0 or higher
- Minimum SDK version 24

### Setup
1. **Clone the Repository:**
   - Open your terminal or command prompt.
   - Navigate to the directory where you want to clone the repository.
   - Run the following command:
     ```bash
     git clone https://github.com/Hassaan69/WebRTCDemo.git
     ```
   - After cloning, open Android Studio.
   - From the Android Studio menu, select 'File' > 'Open'.
   - Navigate to the cloned repository directory and select it to open the project.
   - Click on 'Sync Now' in the notification bar at the top of Android Studio or navigate to 'File' > 'Sync Project with Gradle Files'.

2. **Run the Application:**
   - Install the app on two Android devices.
   - Ensure both devices have internet connectivity.

### Steps to Test the Video Calling Feature
1. **Sign In on Both Devices:**
   - Open the app on the first Android device.
   - Enter a username in the designated field and sign in.

2. **Repeat Sign In on the Second Device:**
   - Open the app on the second Android device.
   - Enter a different username from the first one and sign in.

3. **Initiate the Video Call:**
   - On either device, navigate to the main screen where online users are displayed.
   - You should see the username of the other device listed if both devices are signed in successfully.
   - Tap the 'Call' button next to the username of the device you want to call.

4. **Accept the Call:**
   - On the receiving device, you will receive an incoming call notification.
   - Accept the call to start the video conversation.

### Notes
- Ensure you have allowed the necessary permissions for camera and microphone on both devices.

