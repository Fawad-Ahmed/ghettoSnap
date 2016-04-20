# ghettoSnap
Mobile Application that uses the mobile's camera, takes a picture, and allows the user to draw on top of the picutre.

First Activity has a button to start the 2nd activity.
2nd Activity onCreate initializes a camera intent and buttons.
3 color buttons, 1 "Undo" button, 1 "Clear" button, and one "Done" button
onLongPress() application will add an icon
onDoubleTap() application will add a different icon
Paths and icons are stored in a HashMap and ArrayList, respectively.\n
Supports MultiTouch\n
*Undo button will undo only the lines\n
*Done Button will go back to the first activity, but not save the edited picture
