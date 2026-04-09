# Architecture technique Collector.shop V1

## Stack technique

- **Frontend** : Angular 19, Nginx (HTTPS/TLS auto-signé)
- **Sécurité** : Keycloak 24 (OAuth2/OIDC), JWT Bearer Token
- **Backend** : Spring Boot 4, Spring Security, Spring Data JPA
- **Base de données** : PostgreSQL 15
- **Observabilité** : Spring Actuator, Micrometer, Prometheus, Grafana
- **CI/CD** : GitHub Actions (build, tests, Trivy scan, Docker build)
- **Conteneurisation** : Docker Compose

## Principes de sécurité

- Communication HTTPS via certificat auto-signé Nginx
- Authentification OAuth2/OIDC via Keycloak
- Autorisation par rôles JWT (ROLE_BUYER, ROLE_SELLER, ROLE_ADMIN)
- Scan de vulnérabilités Trivy intégré au pipeline CI/CD
- Backend stateless (JWT) — scalable horizontalement

## Scalabilité

Le backend stateless permet un scaling horizontal sans modification
du code. En production, Docker Swarm ou Kubernetes permettrait
d'ajouter des replicas derrière un load balancer.