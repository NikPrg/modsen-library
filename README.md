# Library application
#### Доступные функции:
    - получение списка всех книг
    - получение списка свободных книг
    - получение книги по её externalId
    - получение книги по её ISBN
    - добавление новой книги
    - изменение информации о существующей книге
    - удаление книги

    
#### Дополнительный функционал:
+ **Аутентификация реализована через bearer token.**

На основе этого имеются два эндпоинта:

    - /login
    - /logout

+ **Реализован дополнительный сервис, который хранит информацию о времени выдачи и времени, когда книгу нужно вернуть - *library-tracker***


#### Используемые технологии:

Были использованы:

    - JDK 17
    - для сборки проекта - Gradle
    - БД - PostgreSQL
    - Spring Boot
    - Docker
    - Flyway
    - Lombok
    - Иные библиотеки


# Database ERD
#### Для приложения была использована следующая схема БД


![image](https://github.com/NikPrg/modsen-library/assets/98475612/f775143e-7f32-435a-a317-0ec1ea08e7d2)

##### Для упрощения связь между сущностями Book и Author "**Один ко Многим**"

# Usage examples

#### Тестирование осуществляется через *Postman*

- **Аутентификация** 

**http://localhost:8080/public/api/v1/users/auth/login**

#### Если пользователь существует выдаётся следующая информация:
![image](https://github.com/NikPrg/modsen-library/assets/98475612/1d9be00b-8522-4763-954c-facc06911239)

#### При отсутствии пользователя(указали неверный email):
![image](https://github.com/NikPrg/modsen-library/assets/98475612/cc5c84d2-2b0d-4345-a433-d886b45766ac)


- **Logout**

**http://localhost:8080/public/api/v1/users/auth/logout**

#### При правильном переданном токене, токен становится невалидным для обращения к ресурсам

#### При неправильном токене выкидывается исключение:
![image](https://github.com/NikPrg/modsen-library/assets/98475612/29ffa386-efa3-4ce0-848f-fc8e37bfb800)


- **Получение списка всех книг**

**http://localhost:8080/public/api/v1/books**

#### При отсутствии bearer токена выкидывается исключение:
![image](https://github.com/NikPrg/modsen-library/assets/98475612/f77f807f-4e5b-44a0-8061-6c1ff0864315)

#### При наличии правильного bearer токена выдаёт view:
- **в случае наличия книг:**

![image](https://github.com/NikPrg/modsen-library/assets/98475612/0e6af8d5-5e6c-4e74-a0f2-6953b5aa833c)


- **в случае отсутствия книг:**

![image](https://github.com/NikPrg/modsen-library/assets/98475612/3c51f193-0881-4fe7-a809-7347beb404cb)


- **Получение книги по externalId**

**http://localhost:8080/public/api/v1/books/{externalId}**
    
#### При отсутствии bearer токена выкидывается исключение, как и в предыдущем кейсе

#### При наличии правильного bearer токена выдаёт view:
- **в случае наличия книги:**

![image](https://github.com/NikPrg/modsen-library/assets/98475612/e82b6f38-8651-4f12-96c3-45ae78112af4)


- **в случае отсутствия книги:**

![image](https://github.com/NikPrg/modsen-library/assets/98475612/f55dd217-57ee-44e8-9e50-4b0cae346976)


- **Получение списка не занятых книг**

**http://localhost:8080/public/api/v1/books/available**

#### При отсутствии bearer токена выкидывается исключение, как и в предыдущем кейсе

#### При наличии правильного bearer токена выдаёт view:
- **в случае наличия книг:**

![image](https://github.com/NikPrg/modsen-library/assets/98475612/d80d3da7-0cbd-427e-9224-681ef61912bd)


- **в случае отсутствия книг:**

![image](https://github.com/NikPrg/modsen-library/assets/98475612/3c51f193-0881-4fe7-a809-7347beb404cb)


- **Удаление книги**

**http://localhost:8080/public/api/v1/books/{externalId}**

#### При отсутствии bearer токена выкидывается исключение, как и в предыдущем кейсе

#### При успешном удалении выставляется флаг isDeleted на true, запись при этом не удаляется из БД


### Остальные эндпоинты рабоатают по идентичной логике 
