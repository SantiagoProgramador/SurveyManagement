# Survey Management ApiRest
## 1. Generalities:

### Description

- Your job is to compare pairs of poker hands and indicate which one is the winner, with what type of hand he won and which cards made him win.

Examples:

He won the first hand, with the highest card, the card was the Ace. He won the second hand, with a pair, the card was the King.

Note: the text is only explanatory, you must deliver the result in an object that is evaluated when running the tests.

You must keep in mind that you must only implement 5 of the 10 types of hands but in the normal order, for example: Royal Flush, Straight Flush, Poker, Full House and Flush

---

## 2. Functionality Proposals:

1. Registration of tho hands of poker, each hand contain 5 cards.
2. Compare the two hands and determine which is the winner 
3. Validate that the cards and the hand are valid
4. Send a json containing the winner and the characteristics: hand type, hand composition

---

---
## 3. Architecture - layered
```
project/
└── src/
    └── main/
        ├── java/
        │   ├ controller/
        │   │
        │   ├dto/
        │   │   └── Errors/
        │   │   └── Request
        │   │   └── Response
        │   │   
        │   ├ config/
        │   │   
        │   ├entities
        │   │   
        │   ├service/
        │   │   
        │   ├helpers/
        │   │   
        │   └── utils/
        │   │   └── exceptions/
        └── resource/


```

---
## Link to deployed code
https://texas-hold-em.onrender.com/api/v1/swagger-ui/index.html
---
## Architecture diagram
```plaintext
+-------------------------------------+
|             Client                  |
|  (Browser, Mobile App, Postman, etc.) |
+-----------------+-------------------+
                  |
                  v
+-------------------------------------+
|        Controller Layer             |
| (Handles HTTP requests/responses)   |
|  +-------------------------------+  |
|  | @RestController               |  |
|  | - UserController         |  |
|  +-------------------------------+  |
+-----------------+-------------------+
                  |
                  v
+-------------------------------------+
|          Service Layer              |
| (Business logic and rules)          |
|  +-------------------------------+  |
|  | @Service                      |  |
|  | - PokerHandService            |  |
|  +-------------------------------+  |
+-----------------+-------------------+

```

## Requirements to run the program in your local machine

1. The program uses Java version 17 in the code editor.
2. Use your IDE or code editor of preference.
3. You can use PostMan to try out the end points

## Program execution

1. Run the project (SpringBoot dashboard recommended)
2. Open Swagger using the following link: http://localhost:8080/api/v1/swagger-ui/index.html
3. Select the query you wish to perform and enter data if necessary to test the program's functionality.
