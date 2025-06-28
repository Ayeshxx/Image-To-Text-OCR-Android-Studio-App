This is project is made by combined efforts of 6 team members. We are focusing on building an Optical Character Recognition model that runs efficiently on CPUs and edge devices without requiring high computational resources. Here the edge device we will be using is an Android mobile phone.

The main steps include building UI, image capture, text recognition, draw rectangle boxes and numbers around text blocks, allow users to click on the text box and display result.
1. User Interface <br>
The application uses a minimal and clean interface with only two buttons. There is a place to display the image, buttons to take photos and copy text, and a text area to display results. The main screen has an image viewer at the top, a "Click an Image" button, a text display area at the bottom and "Copy Text" button. all of the logic is written in activity_main.xml file. Also there is an overlay layer that sits on top of the image to draw the boxes around text. The design is simple with no interruption in navigation and fast interaction, making the application perfect for both new and advanced users.
2. Image Capture and processing
The first step is to obtain an image by capturing a photo from the device's camera using the "Click an Image" button, this results in a permission pop up requesting access to the camera. Once permission is granted to the app, and an image has been captured, then the image can be processed into an bitmap image in order to work with the OCR.
3. Text Recognition Using OCR Engine
The majority of the implementations make use of Google ML Kit's Text Recognizer to identify and extract text from the image. These libraries read the bitmap image, detect text blocks, lines, and words and put them into machine-readable format, all of the logic is written in MainActivity.kt file.
4.Draw Rectangle Boxes and Numbers Around Text Blocks
Google ML Kit analyzes the image and finds all the text in it. For each piece of text it finds, it gives us the exact coordinates of where that text is located. The TextOverlayView.kt file then draws blue boxes around each text block and puts numbers above them. 
5.Allow Users to Click on the Text Box
When the user taps on any of the blue boxes, that box turns green to show it's selected, and the text inside that box gets extracted and shown in the text area below. 
6. Text Processing and Output Generation
The identified text is then processed and rendered to the user in the UI of the app. User can then copy this selected text to the phone's clipboard using the "Copy Text" button, just like copying text from any other app, the text then can be used further. 
