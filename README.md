
# 🎖️ awards-service

A Spring Boot backend for generating military ribbon sets based on user-selected awards. Accepts award selections and assembles a properly ordered composite ribbon image.

---

## 📌 Features

- REST API for retrieving available military awards
- Accepts a list of selected awards
- Returns a generated composite image in the correct display order
- Designed for use with a front-end Angular application
- CORS support for local dev and deployed front-ends

---

## 🚀 Technologies Used

- Java 17
- Spring Boot 3.5
- Maven
- Docker
- Google Cloud Run (deployed)
- Artifact Registry
- ImageIO for ribbon image processing



---

## ⚙️ Local Development

### Prerequisites

- Java 17+
- Maven

### Run locally:

```bash
./mvnw spring-boot:run
````

The service will be available at `http://localhost:8080`.

---

## 🌐 API Endpoints

| Method | Endpoint            | Description                                                 |
| ------ | ------------------- | ----------------------------------------------------------- |
| GET    | `/getAwardsList`    | Returns list of available awards                            |
| POST   | `/getCombinedAward` | Accepts selected awards and returns a composite image (PNG) |

---

## 🐳 Build and Deploy with Docker & GCP

### Build JAR

```bash
./mvnw clean package -DskipTests
```

### Build Docker Image

```bash
docker build -t us-east5-docker.pkg.dev/awards-service/awards-service/awards-service .
```

### Push to Artifact Registry

```bash
docker push us-east5-docker.pkg.dev/awards-service/awards-service/awards-service
```

### Deploy to Cloud Run

```bash
gcloud run deploy awards-service \
  --image=us-east5-docker.pkg.dev/awards-service/awards-service/awards-service \
  --platform=managed \
  --region=us-east5 \
  --allow-unauthenticated
```

---

## 🔐 CORS Configuration

To allow calls from your front-end:

```java
@CrossOrigin(origins = {
  "http://localhost:4200",
  "https://awards-service.web.app",
  "https://your-custom-domain.com"
})
```

---

## 📄 License

This project is released under the [MIT License](LICENSE) (or add your preferred license here).

---

## 🙋 Author

Developed by [Fran Deible](https://github.com/fwdeible)

