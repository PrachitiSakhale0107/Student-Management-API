# Student Management REST API

A REST API built with Java 21, Spring Boot, Spring Security, JWT, Spring Data JPA/Hibernate and MySQL. Users can register and log in; authenticated users can perform CRUD operations on student records.

## Requirements
- JDK 21
- MySQL Server and MySQL Workbench (or MySQL CLI)
- Maven 3.9+ (or the IDE's Maven integration)
- Postman

## Setup
1. Create/open the `student_management` database by running `database.sql` in MySQL Workbench.
2. Set your MySQL credentials. The defaults are `DB_USERNAME=root` and `DB_PASSWORD=change_me`. Update `src/main/resources/application.properties`, or define environment variables `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD`.
3. Keep `JWT_SECRET` private. For local coursework the provided example key is configured as a default; for any shared/deployed use, generate a strong Base64 secret and set the `JWT_SECRET` environment variable.
4. Open this folder in your IDE and reload Maven dependencies.
5. Run `StudentmanagementApplication.java`, or from this folder run `mvn spring-boot:run`.
6. The API starts at `http://localhost:8080`. Hibernate creates/updates the `app_users` and `students` tables.

## Endpoints
| Method | Path | Authentication | Description |
|---|---|---|---|
| POST | `/api/auth/register` | Public | Register user |
| POST | `/api/auth/login` | Public | Login and receive JWT |
| POST | `/api/students` | Bearer JWT | Add student |
| GET | `/api/students` | Bearer JWT | List students |
| GET | `/api/students/{id}` | Bearer JWT | Get student by ID |
| PUT | `/api/students/{id}` | Bearer JWT | Update student |
| DELETE | `/api/students/{id}` | Bearer JWT | Delete student |

## Postman quick test
1. Register: `POST http://localhost:8080/api/auth/register`, JSON body `{"email":"student@example.com","password":"Student@123"}`.
2. Login: `POST http://localhost:8080/api/auth/login` with the same body. Copy `token` from the response.
3. For student endpoints, select **Authorization > Bearer Token** and paste the token.
4. Add student with `POST /api/students` and body `{"name":"Rahul Sharma","email":"rahul@example.com","branch":"Information Technology","year":3}`.
5. Test GET `/api/students`, GET `/api/students/1` (use actual ID), PUT `/api/students/1`, and DELETE `/api/students/1`.
6. Verify that `GET /api/students` without a token returns 401.

## Example request body
```json
{
  "name": "Rahul Sharma",
  "email": "rahul@example.com",
  "branch": "Information Technology",
  "year": 3
}
```

## Security and limitations
- Passwords are hashed with BCrypt; raw passwords are not stored.
- Student APIs require a valid, unexpired signed JWT.
- This is an educational starter project. Before production use, improve secret management, add rate limiting, stricter CORS policy if needed, and more tests.
- Do not commit real passwords or production secrets.

## Submission
Add your real Postman screenshots under `screenshots/` and update this README with your GitHub repository URL after pushing.
