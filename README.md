# 🚦 Intelligent API Rate Limiting & Abuse Prevention Platform

An **enterprise-grade API Rate Limiting, Abuse Detection, and Observability platform** built from scratch using **Spring Boot, Redis, and React**.

This project simulates how **real API Gateways / Platform teams** design, monitor, and control traffic in production systems.

> 🎯 Built with an SDE-2 mindset: control-plane + data-plane separation, runtime configuration, fail-safe design, and real-time observability.

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

## 🖼 Dashboard Screenshots

📌 Have a Look Here 

## 🖼 Dashboard Preview

### Traffic & Metrics
![Traffic Dashboard](screenshots/dashboard-traffic.png)
<img width="1871" height="651" alt="image" src="https://github.com/user-attachments/assets/14f8818f-a217-47b9-8e51-062ddd684c26" />


### Admin Controls
![Admin Controls](screenshots/admin-controls.png)

### Rate Limiting Graphs
![Graphs](screenshots/graphs.png)
