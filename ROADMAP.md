# 🗺 Astor Butler Project Roadmap / Дорожная карта проекта

A step-by-step plan to evolve from MVP to production-ready assistant bot.

Пошаговый план развития проекта от MVP до стабильного продакшен-бота.

---

## ✅ v0.1 — MVP
**Basic working functionality / Базовый рабочий функционал**

- [x] User registration via Telegram / Регистрация пользователей по Telegram
- [x] Role and status system / Система ролей и статусов
- [x] Table, merch, and charity orders / Заказы: столы, мерч, благотворительность
- [x] Liquibase DB migrations / Миграции Liquibase
- [x] Docker & Compose setup / Docker и docker-compose
- [x] MVC structure with Spring Boot / Архитектура MVC на Spring Boot

---

## 🛠 v0.2 — Alpha
**Improving stability and developer experience / Повышение стабильности и удобства разработки**

- [ ] Unit tests: UserService, OrderHandler, AuthService
- [ ] Telegram error handling / Обработка ошибок Telegram
- [ ] Phone and command validation / Валидация номера телефона и команд
- [ ] CI/CD with GitHub Actions or Drone
- [ ] Enhanced README with architecture / Расширенный README с архитектурой

---

## 🚦 v0.3 — Beta
**Staging-level stability / Надёжность для тестовой среды**

- [ ] Integration tests in Docker
- [ ] Full audit logging / Полное логирование (аудит)
- [ ] Monitoring via Grafana/Prometheus / Мониторинг
- [ ] Improved Telegram UI: inline, menus / Улучшенный Telegram-интерфейс
- [ ] Deploy to staging with webhooks / Выкатка в стейджинг с вебхуками

---

## 🛡 v1.0 — Stable Beta
**Production-ready release candidate / Подготовка к продакшену**

- [ ] Admin UI panel / Админ-панель
- [ ] Secure `.env` for production / Продакшен-конфигурации и безопасность
- [ ] Build and release with `.jar` and Docker / Сборка `.jar` и `.docker`
- [ ] Auto-migrations and schema check / Автоматические миграции и проверка схемы