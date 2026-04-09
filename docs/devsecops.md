# Cycle de vie DevSecOps — Collector.shop

## Plan
- Backlog avec User Stories et critères d'acceptation
- Exigences de sécurité identifiées dès la conception (auth, transactions financières)

## Code
- Secrets via variables d'environnement (jamais hardcodés)
- Sécurité by design : HTTPS/TLS, JWT, RBAC par rôles Keycloak

## Build
- GitHub Actions : mvn clean verify + Docker build
- Images Docker immuables et versionnées

## Test
- Tests unitaires JUnit (ItemService, ItemServiceGetAll)
- Scan de vulnérabilités Trivy (CRITICAL/HIGH) sur image Docker

## Release
- Pipeline vert = artefact approuvé
- Images Docker taggées prêtes au déploiement

## Deploy
- Docker Compose : stack complète (Postgres, Keycloak, Backend, Frontend)
- Monitoring activé dès le déploiement (Prometheus + Grafana)

## Operate
- Métriques HTTP collectées via Actuator/Micrometer
- Tests de charge Siege : 534 req/sec, 100% disponibilité, 20ms avg