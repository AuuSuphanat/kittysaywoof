# Kittysaywoof

`Kittysaywoof` is a simulated e-commerce website built with Spring Boot. It includes:

- Home, About, Products, Cart, Contact, and Checkout pages
- Thymeleaf server-rendered templates
- Contact message persistence with Spring Data JPA
- Optional email sending for contact and checkout actions

## Tech Stack

- Java 17
- Spring Boot 3.4.4
- Spring Web
- Spring Data JPA
- Thymeleaf
- H2 database
- Maven Wrapper

## Run Locally

1. Install Java 17.
2. Open a terminal in the project root.
3. Start the app:

```powershell
.\mvnw.cmd spring-boot:run
```

4. Open:

- `http://localhost:8080`

You can also import and run the project in Eclipse as a Spring Boot application.

## Database

The project is configured to use a local H2 database by default.

- H2 console: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:file:./data/kittysaywoof`
- Username: `sa`
- Password: blank

## Optional Mail Configuration

Email sending is disabled by default for local development.

To enable it, set these environment variables before starting the app:

```powershell
$env:APP_MAIL_ENABLED="true"
$env:APP_MAIL_TO="you@example.com"
$env:MAIL_HOST="smtp.gmail.com"
$env:MAIL_PORT="587"
$env:MAIL_USERNAME="you@example.com"
$env:MAIL_PASSWORD="your-app-password"
```

## Project Structure

```text
src/main/java/com/srojsir/kittysaywoof
src/main/resources/templates
src/main/resources/static
```

## Notes

- The local database files are stored under `data/` and are ignored by git.
- Checkout submission is handled from the cart page.
- This project uses the Maven wrapper, so a separate Maven install is not required.
