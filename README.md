## News Service

This is a small service that consumes some APIs from NewsAPI

### How to set up

Make sure to have MSSQL dB, create a database (ex. `newsdb`)

Set up your `application.properties` with the corresponding values
```yaml
server.port=<YOUR_PORT_HERE>

# Database Configuration
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=newsdb // replace with your dB connection string and name
spring.datasource.username=<DB_USERNAME>
spring.datasource.password=<DB_PASSWORD>
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
spring.jpa.hibernate.ddl-auto=update

## Logging
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate=ERROR

## News API
news.api.key=<NEWS_API_KEY>
news.api.baseUrl=https://newsapi.org

## JWT Configuration
news.jwt.secret=342tkm4t445645yl45ylmgkl454543232342rerewpl432erw3kl21312khe2ea
news.jwt.expiration=300000 // in milliseconds
```

Then run the app using `mvn clean spring-boot:run`

> You can test the endpoints using the POSTMAN collection available in the project root directory.
Make sure to change the collection variable `baseUrl` to your suitable url

### Service Overview

To consume these APIs, the user needs to have a valid token.

To get a token, the user needs to be registered.

Once a user registers, his data will be stored to the `users` table in the `newsdb`

Once registered, the user needs to sign in, in the response of the sign in endpoint the user will receive a JWT token

Then the user can use this token to call the protected endpoints.

### API documentation:

Call the registration API:
```curl
POST /api/public/users/signup
```

Request Sample:
```json
{
    "fullName": "Marc Esso",
    "username": "marc.esso",
    "password": "Password@123"
}
```

Response Sample:
Status: `200 OK`

```json
{
    "fullName": "Marc Esso",
    "username": "marc.esso"
}
```

---

Sign in using
```curl
POST /api/public/users/signin
```
Request Sample:
```json
{
    "username": "marc.esso",
    "password": "Password@123"
}
```

Response Sample:
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXJjLmVzc28iLCJpYXQiOjE2OTAxMjI1ODgsImV4cCI6MTY5MDEyMjg4OH0.C4fGVW-HLBoozRhcEHguVm-HkOyZxpoZCcUhXF9gsuY"
}
```
---
For the rest of the endpoint you need to use a valid token to be able to call it:

Get top headlines

```curl
GET /api/news/top-headlines?pageNumber=1&pageSize=10&search=a
```

Response sample:
```json
{
  "status": "ok",
  "totalResults": 37,
  "articles": [
    {
      "source": {
        "id": null,
        "name": "Miami Herald"
      },
      "author": "Susan Miller Degnan",
      "title": "Inter Miami players ‘overwhelmed’ by Lionel Messi’s last-minute goal - Miami Herald",
      "description": "Inter Miami played its first match with Lionel Messi there at DRV PNK Stadium. The match opened the Leagues Cup, which includes Mexico’s Liga MX and MLS teams.",
      "url": "https://www.miamiherald.com/sports/mls/inter-miami/article277462648.html",
      "urlToImage": "https://www.miamiherald.com/latest-news/bhgx4q/picture277554378/alternates/LANDSCAPE_1140/MIA_Inter_Miami_Cruz_Azul_MJO_19.JPG",
      "publishedAt": "2023-07-22T14:39:48Z",
      "content": "The only thing more thrilling than playing for the same team on the same field in the same game as Lionel Messi for his Inter Miami debut was doing it in a last-minute victory on a free kick the game… [+3827 chars]"
    },
    // rest of the response...
  ]
}
```
---
Get top headlines

```curl
GET /api/news/sources
```

Response Sample:

```json
{
  "status": "ok",
  "sources": [
    {
      "id": "abc-news",
      "name": "ABC News",
      "description": "Your trusted source for breaking news, analysis, exclusive interviews, headlines, and videos at ABCNews.com.",
      "url": "https://abcnews.go.com",
      "category": "general",
      "language": "en",
      "country": "us"
    },
    ...
  ]
}
```
---

Downloading a file from URL

```curl
POST /api/file?url=https://www.africau.edu/images/default/sample.pdf
```
you can send any file url in the query param `url`

and you will get the actual file in the response


## Testing

For testing, I used Cucumber + Mockito with Gherkin feature files

These are the test coverage results

![Test results](https://github.com/e55o/news-api/assets/31523264/3b8f603b-9c99-40bb-98ed-7673f3c5c614)

You can run the tests using IntelliJ IDEA coverage runner.



