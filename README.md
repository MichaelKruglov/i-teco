## Cервис накопления скидочных баллов клиентов (backend)
#### Назначение: 
Cервис накопления и списания скидочных баллов клиентов.
#### Подготовка для запуска: 
Требуется предварительное подключение к базе данных, не содержащей посторонних таблиц.

#### Сборка: 
```
gradle clean build
```
#### Запуск:
```
gradle bootRun
```
#### Сборка в docker:
```
gradle clean build
docker build -t i-teco .
docker run -d -p 8080:8080 -t i-teco
```
#### Локальный порт для запуска: 
8080 указываются в файле **application.yml**.
так же подключение к Базе Данных: DB_CONNECT, DB_NAME, DB_USER, DB_PASSWORD
#### Используемые технологии:
* Java 11
* Spring Boot
* Тип сервиса: REST
* PostgreSQL
* Flyway
#### Методы в Rest контроллере http://localhost:8080/swagger-ui.html#/:
* withdrawPoints - накопление и снятие баллов.
* getBonusPoints - получение баллов по номеру карты.