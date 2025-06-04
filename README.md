# GeoQuiz: Android Pet Project

**Summary:** This repository hosts GeoQuiz, a pet project Android application developed as a learning exercise for Android app development, UI implementation, and Espresso testing. The project is based on an example from the book "Android Programming: The Big Nerd Ranch Guide" and has been extended with additional features and tests.

![image](https://github.com/user-attachments/assets/38099dab-243a-4f18-b84d-1306cc1152eb)
![image](https://github.com/user-attachments/assets/a3d4bed4-fdef-45c2-bb1c-ba50345548b9)
![image](https://github.com/user-attachments/assets/600c17e5-85a6-4541-a5ba-80dee8634861)
![image](https://github.com/user-attachments/assets/19492d71-3d59-4d15-abd6-483370150954)

## About This Project

GeoQuiz is a pet project quiz app written in Java.

The application was initially created as a sandbox for learning instrumented tests in Java. The base project was taken from the first chapter of the book "Android Programming: The Big Nerd Ranch Guide (3rd Edition)" by Bill Phillips, Chris Stewart, and Kristin Marsicano.

![image](https://github.com/user-attachments/assets/b00cdc0c-14f1-4d6b-8673-aeaf93d34320)

Gradually, as I learn new concepts, I add new functionality and tests to this base application. This app serves as a way to track my skill development in Android application development and testing. It's also partly just for fun, although it sometimes causes more frustration than amusement!

## Features

If the user grants internet access, the application fetches questions from the Open Trivia Database API: https://opentdb.com/api_config.php.
Support for multiple-choice question types is planned for future development.

### API Request Example:
`https://opentdb.com/api.php?amount=2&type=boolean`

### API Response Example:

```json
{
  "response_code": 0,
  "results": [
    {
      "type": "boolean",
      "difficulty": "medium",
      "category": "Science: Computers",
      "question": "AMD created the first consumer 64-bit processor.",
      "correct_answer": "True",
      "incorrect_answers": [
        "False"
      ]
    },
    {
      "type": "boolean",
      "difficulty": "medium",
      "category": "History",
      "question": "The Korean War ended in 1953 without any ceasefire.",
      "correct_answer": "False",
      "incorrect_answers": [
        "True"
      ]
    }
  ]
}
```

## Building and Running the Project

1.  Clone this repository to your local machine.
2.  Open Android Studio.
3.  Select "Open an existing Android Studio project".
4.  Navigate to the cloned repository's root directory and select it.
5.  Allow Android Studio to sync and build the project.
6.  Run the application on an Android emulator or a physical device.
