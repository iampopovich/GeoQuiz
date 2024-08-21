# GeoQuiz
GeoQuiz pet-app written in java

Приложение написано для песочницы инструментальных тестов на Java  
Проект взят из первой главы [книги](https://www.piter.com/collection/programmirovanie-pod-android-i-ios/product/android-programmirovanie-dlya-professionalov-3-e-izdanie) 
`Android. Программирование для профессионалов. 3-е издание Филлипс Б., Стюарт К., Марсикано К.`

![image](https://github.com/user-attachments/assets/b00cdc0c-14f1-4d6b-8673-aeaf93d34320)

постепенно я узнаю что-то новое и добавляю в базовое приложение новую функциональность и тесты  
можно назвать это приложение пет-проектом  
создаю я его, чтобы в будущем проследить за развитием скилов написания приложений и их тестирования  
ну и отчасти just for fun конечно 🤘  
хотя боли проект причиняет больше чем веселья  


если пользователь разрешает досутп в интернет - https://opentdb.com/api_config.php получаем вопросы отсюда
позже попробую поддержать типы вопросов с множественными ответами  

пример запроса  
`https://opentdb.com/api.php?amount=2&type=boolean`  
пример ответа  
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

# TODO
- добавить настройки для запроса квизов из тривии
- добавить экран или что0то более простое для настроек
- добавить кнопку настроек в тулбар экрна квиза тривии
- вернуть статистику
- вернуть ресет игры
