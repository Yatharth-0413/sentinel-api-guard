# 🚦 Intelligent API Rate Limiting & Abuse Prevention Platform

An **enterprise-grade API Rate Limiting, Abuse Detection, and Observability platform** built from scratch using **Spring Boot, Redis, and React**.

This project simulates how **real API Gateways / Platform teams** design, monitor, and control traffic in production systems.


---

## 🔥 Key Features

### 🚦 Rate Limiting (Token Bucket)
- IP-based and User-based rate limiting
- Endpoint-aware limits
- Tier support (FREE / PREMIUM)
- Redis-backed token bucket algorithm

### 🛡 Abuse Detection & Protection
- Request burst tracking
- Automatic temporary bans
- Redis-backed ban persistence
- Graceful degradation when Redis is down

### ⚙️ Enterprise Failure Handling
- **FAIL_OPEN / FAIL_CLOSED** modes
- Global rate-limiting enable / disable switch
- Redis outage safe (no cascading failures)

### 📊 Observability & Metrics
- Real-time request counters
- Time-series metrics (requests, rate-limited, bans)
- Endpoint-level traffic stats
- Live dashboard with graphs

### 🧠 Admin Control Plane
- Runtime configuration (no restart)
- Change rate limits dynamically
- Toggle failure modes
- Enable / disable rate limiting live
- REST admin APIs + UI controls

### 🖥 Modern Dashboard (React)
- Dark / Light mode
- Live charts (Grafana-style)
- Metrics cards
- Endpoint tables
- Admin control panel

---

## 🏗 Architecture Overview

          ┌──────────────┐
          │   React UI   │
          │  (Dashboard) │
          └──────┬───────┘
                 │
                 ▼
     ┌─────────────────────────┐
     │   Spring Boot Backend    │
     │─────────────────────────│
     │  RateLimitFilter         │
     │  Abuse Detection         │
     │  Admin Config APIs       │
     │  Metrics & Time Series   │
     └──────────┬──────────────┘
                │
                ▼
          ┌──────────┐
          │  Redis   │
          │──────────│
          │ Tokens   │
          │ Bans     │
          │ Counters │
          └──────────┘

---

## 🧩 Tech Stack

### Backend
- Java 17
- Spring Boot
- Spring Web
- Spring Data Redis (Lettuce)
- Redis (Docker)
- Token Bucket Algorithm

### Frontend
- React (Vite)
- Recharts (Graphs)
- Modern UI (Dark Mode)

### Infrastructure
- Docker (Redis)
- REST APIs
- In-memory + Redis hybrid model

---

## 🚀 Getting Started

### 1️⃣ Prerequisites
- Java 17+
- Node.js 18+
- Docker

---

### 2️⃣ Start Redis

bash
docker run -d --name redis-rate-limit -p 6379:6379 redis

### 3️⃣ Start Backend

cd backend
./mvnw spring-boot:run

- Backend runs on:
http://localhost:8080

### 4️⃣ Start Frontend
cd frontend
npm install
npm run dev

UI runs on:

http://localhost:5173

## 🧪 Testing the System

### Test API Traffic
curl http://localhost:8080/test

### Simulate Burst Traffic
for i in {1..20}; do curl http://localhost:8080/test; done



## ⚙️ Admin APIs
### 🔍 View Runtime Config
curl http://localhost:8080/admin/config

### 🔄 Toggle Rate Limiting
curl -X POST "http://localhost:8080/admin/config/enabled?enabled=false"

### 🚨 Change Failure Mode
curl -X POST "http://localhost:8080/admin/config/failure-mode?mode=FAIL_CLOSED"

## 📈 Metrics APIs
curl http://localhost:8080/metrics
curl http://localhost:8080/metrics/timeseries


📌 Have a Look Here 

## 🖼 Dashboard Preview

### Traffic & Metrics
<img width="1864" height="616" alt="image" src="https://github.com/user-attachments/assets/622d27a1-2e39-420d-9c71-d7aa24afc008" />

### Admin Controls
<img width="1071" height="379" alt="image" src="https://github.com/user-attachments/assets/04839ab8-1576-422c-ad6d-5194c1d019b7" />


### Rate Limiting Graphs
<img width="1849" height="530" alt="image" src="https://github.com/user-attachments/assets/acb941b8-6b62-4829-82f2-9d50e6ace61a" />

### Full Dashboard Preview 
<img width="1881" height="870" alt="image" src="https://github.com/user-attachments/assets/5adb1c89-6c04-46a6-a9bc-43370cf0fde1" />

## 🖼 Docker Desktop ( Redis Container )
<img width="1881" height="990" alt="image" src="https://github.com/user-attachments/assets/4102ca87-a97d-44a7-bbaf-f5206033989e" />



