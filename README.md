🧠 Astor Butler — Telegram AI Booking & Loyalty Assistant

Astor Butler is an intelligent assistant designed for HoReCa businesses (restaurants, cafes, clubs) to manage bookings, orders, payments, and customer loyalty — entirely through Telegram and AI.

✨ Features
•	🤖 AI-powered natural language processing for user interaction
•	📆 Table bookings with slot management and time control
•	🛍️ Merch and service ordering
•	❤️ Charity donations as a part of the ordering system
•	💰 Telegram Stars loyalty system (internal virtual currency)
•	💳 Real-time payments (Tinkoff, Sber, YooKassa integrations)
•	📊 Monitoring & observability: Redis, Kafka, Grafana ready
•	🔒 Secure role-based access: Guest / Manager / Admin
•	🛡️ Phone-based Telegram Login & phone number verification

🔧 Tech Stack
•	Java 21, Spring Boot
•	PostgreSQL, Redis
•	Docker, docker-compose
•	Kafka, Grafana, Prometheus
•	Telegram Bot API (via TelegramLongPollingBot)
•	Gradle & Maven compatibility

📄 License

This project is licensed under the Apache License 2.0 — see LICENSE for details.
By using this bot, you also accept the Privacy Policy.

🧱 Architecture

Currently monolithic with modular packaging by features (table, merch, charity, etc.).
Designed to evolve into a microservice architecture, with event-driven communication via Kafka.

🌍 Future Plans & Integrations
•	⚙️ Split into scalable microservices (booking, user, finance, feedback, etc.)
•	🌐 Integration with:
•	Amazon (e.g. for smart menu devices or AWS hosting)
•	Facebook / Meta (OAuth bridge & Messenger support)
•	LinkedIn (B2B partner management)
•	Netflix (event bookings, cross-promo, brand collabs)
•	Yandex / VK / Spotify (local content support & voice bots)
•	☁️ Cloud-native deployment: Kubernetes, AWS, GCP

👥 Want to Collaborate?

We’re looking to connect with engineers, designers, and businesses to scale this project.
Got a cool idea or partnership in mind? Let’s talk.