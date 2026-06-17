# TixHive 🐝

A ticket-management application built with a **Spring Boot** REST API backend and a **JavaFX** desktop frontend. Users can register, log in, and manage support tickets with priority and status tracking. A dedicated developer role can advance ticket statuses through the workflow.

---

## Table of Contents

1. [Demo](#demo)
2. [Requirements](#requirements)
3. [Installation](#installation)
4. [How to Use](#how-to-use)
5. [User Roles & Permissions](#user-roles--permissions)
6. [File Structure](#file-structure)
7. [Technologies](#technologies)
8. [License](#license)

---

## Demo

The backend is continuously deployed to **capstone-project.de** via Docker on every push to `main`.  
To open the desktop frontend locally, start the backend first, then run the JavaFX application (see [Installation](#installation)).

A screenshot of the application:

![TixHive screenshot](frontend/src/main/resources/de/iav/frontend/pictures/Bildschirmfoto2023-08-17um13.16.45.png)

---

## Requirements

| Requirement | Version |
|---|---|
| Java (JDK) | 17 or later |
| Apache Maven | 3.8 or later |
| MongoDB | 6.x (or use a hosted URI, e.g. MongoDB Atlas) |
| Docker *(optional)* | any recent version |

No build of the frontend is needed beyond `mvn clean javafx:run`.

---

## Installation

### 1 — Clone the repository

```bash
git clone https://github.com/gloedige/TixHive.git
cd TixHive
```

### 2 — Start the backend

```bash
cd backend
# Provide your MongoDB connection string as an environment variable
export MONGO_DB_URI="mongodb://localhost:27017/tixhive"
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`.

> **Tip:** If you prefer Docker, build and run the image directly:
> ```bash
> cd backend
> ./mvnw -B package
> docker build -t tixhive-backend .
> docker run -e MONGO_DB_URI="<your-uri>" -p 8080:8080 tixhive-backend
> ```

### 3 — Start the frontend

Open a new terminal from the repository root:

```bash
cd frontend
./mvnw clean javafx:run
```

The JavaFX desktop window will launch and connect to the backend at `http://localhost:8080` by default.

---

## How to Use

1. **Register** — create a new account on the registration screen.
2. **Log in** — enter your credentials to access your dashboard.
3. **Create a ticket** — fill in the subject, description, and priority (`LOW` / `MEDIUM` / `HIGH`).
4. **View tickets** — browse all open tickets assigned to you or your team.
5. **Update a ticket** — edit the subject, description, or priority of an existing ticket.
6. **Delete a ticket** — remove a ticket you own (authentication required).
7. **Developer workflow** — users with the `DEVELOPER` role can advance a ticket's status through `OPEN → IN_PROGRESS → DONE`.
8. **Log out** — end your session securely.

---

## User Roles & Permissions

| Role | Register / Login | Create Ticket | Update Ticket | Delete Ticket | Change Status |
|---|:---:|:---:|:---:|:---:|:---:|
| `USER` | ✅ | ✅ | ✅ | ✅ | ❌ |
| `DEVELOPER` | ✅ | ✅ | ✅ | ✅ | ✅ |
| `ADMIN` | ✅ | ✅ | ✅ | ✅ | ❌ |

---

## File Structure

```
TixHive/
├── .github/
│   └── workflows/
│       ├── analyze-backend.yml        # SonarCloud analysis for the backend
│       ├── analyze-frontend.yml       # SonarCloud analysis for the frontend
│       ├── deployToAWS.yml            # Build → Docker Hub → deploy to capstone-project.de
│       └── maven.yml                  # CI: build & test on every push/PR
│
├── backend/                           # Spring Boot REST API
│   ├── Dockerfile                     # Container image definition (OpenJDK 17)
│   ├── pom.xml                        # Maven build descriptor
│   └── src/
│       ├── main/
│       │   ├── java/de/iav/backend/
│       │   │   ├── BackendApplication.java          # Spring Boot entry point
│       │   │   ├── controller/
│       │   │   │   ├── TicketController.java         # CRUD endpoints for tickets
│       │   │   │   └── UserController.java           # User management endpoints
│       │   │   ├── dto/
│       │   │   │   └── TicketRequestDTO.java         # Incoming ticket payload
│       │   │   ├── exceptions/                       # Custom exception classes
│       │   │   ├── model/
│       │   │   │   ├── Ticket.java                   # Ticket document (record)
│       │   │   │   ├── TicketPriority.java            # Enum: LOW / MEDIUM / HIGH
│       │   │   │   └── TicketStatus.java             # Enum: OPEN / IN_PROGRESS / DONE
│       │   │   ├── repository/
│       │   │   │   └── TicketRepository.java         # MongoDB repository
│       │   │   ├── security/
│       │   │   │   ├── AppUser.java                  # User document
│       │   │   │   ├── AppUserRole.java              # Enum: USER / ADMIN / DEVELOPER
│       │   │   │   ├── AppUserService.java           # User details service
│       │   │   │   ├── AuthController.java           # /api/auth/* endpoints
│       │   │   │   └── SecurityConfig.java           # Spring Security filter chain
│       │   │   └── service/
│       │   │       ├── DateTimeService.java          # Timestamp helper
│       │   │       ├── IdService.java                # UUID generator
│       │   │       ├── TicketService.java            # Business logic for tickets
│       │   │       └── UserService.java              # Business logic for users
│       │   └── resources/
│       │       └── application.properties            # App configuration
│       └── test/                                     # JUnit 5 integration & unit tests
│
└── frontend/                          # JavaFX desktop application
    ├── pom.xml                        # Maven build descriptor (JavaFX plugin)
    └── src/main/
        ├── java/de/iav/frontend/
        │   ├── TixHiveApplication.java              # JavaFX entry point
        │   ├── controller/                           # FXML screen controllers
        │   │   ├── AddTicketController.java
        │   │   ├── DeleteTicketController.java
        │   │   ├── HandleTicketController.java
        │   │   ├── ListTicketController.java
        │   │   ├── LoginController.java
        │   │   ├── LogoutController.java
        │   │   ├── RegisterController.java
        │   │   └── UpdateTicketController.java
        │   ├── exception/                            # Custom exception wrappers
        │   ├── model/                                # Mirror of backend model classes
        │   ├── security/                             # Auth request/response models
        │   └── service/
        │       ├── ChoiceBoxService.java             # Populates drop-down controls
        │       ├── SceneSwitchService.java           # Handles screen navigation
        │       ├── TicketService.java                # HTTP calls to ticket API
        │       └── UserService.java                  # HTTP calls to user API
        └── resources/de/iav/frontend/
            ├── css/                                  # Stylesheets for JavaFX controls
            │   ├── ChoiceBox.css
            │   ├── GlobalStyles.css
            │   ├── Label.css
            │   ├── ListStyles.css
            │   └── smallButtonStyle.css
            ├── fxml/                                 # Screen layout definitions
            │   ├── addTicket-scene.fxml
            │   ├── confirmDeleteTicket-scene.fxml
            │   ├── handleTicket-scene.fxml
            │   ├── listAllTicketDev-scene.fxml
            │   ├── listAllTickets-scene.fxml
            │   ├── login-scene.fxml
            │   ├── logout-scene.fxml
            │   ├── register-scene.fxml
            │   └── updateTicket-scene.fxml
            └── pictures/                             # Application screenshots / assets
```

---

## Technologies

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Backend framework | Spring Boot 3.1 |
| Persistence | MongoDB (via Spring Data MongoDB) |
| Security | Spring Security 6 (HTTP Basic, Argon2 password hashing) |
| Frontend framework | JavaFX 20 with FXML |
| HTTP client | Java built-in `java.net.http.HttpClient` |
| JSON serialisation | Jackson 2 |
| Build tool | Apache Maven (Maven Wrapper included) |
| Containerisation | Docker (OpenJDK 17 base image) |
| CI / CD | GitHub Actions → Docker Hub → capstone-project.de |
| Code quality | SonarCloud, JaCoCo |
| Testing | JUnit 5, Spring Boot Test, Flapdoodle Embedded MongoDB |

---

## License

This project is provided for educational and portfolio purposes.  
No explicit open-source license has been applied. All rights reserved by the author.
