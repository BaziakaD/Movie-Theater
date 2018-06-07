# Movie Theater

Developed with Spring MVC, Spring Security and Spring Data. Spring application for managing a movie theater, which allows for admins to create cinemas and events, fill user bill, and for users to register, view events with air dates and times, get ticket price, buy tickets. Also, admin can load cinemas and events from json format file. Spring aspects used for create lucky winner logic.

Techonologies:
  - Spring Framework( IoC, Data, MVC, Security)
  - Hibernate
  - H2 & MySQL
  - iText PDF
  - JodaTime
  - Log4j2
  - Jetty

# Users

  - Admin (admin@mail.com, pass)
  - User (user@mail.com, pass)

### Installation
Install the dependencies and devDependencies and start the server.

```sh
$ cd Movie-Theater
$ mvn clean package
$ java -jar target/Movie_Cinema_Spring-1.0-SNAPSHOT-jar-with-dependencies.jar

```

For production environments. (Use your MySQL or docker-compose from this project)

```sh
$ cd Movie-Theater
$ mvn clean package
$ java -jar -DSpring.profiles.active=prod target/Movie_Cinema_Spring-1.0-SNAPSHOT-jar-with-dependencies.jar
```

### Docker
You can use docker-compose for create mysql container.

```sh
cd Movie-Theater
docker-compose up
```