# Synthèse d'expérimentation — Keycloak 24

## Contexte

Dans le cadre du développement de Collector.shop, l'authentification
et l'autorisation constituent une exigence de premier plan (transactions
financières, rôles acheteur/vendeur/admin). Keycloak a été sélectionné
comme serveur d'autorisation OAuth2/OIDC.

---

## Environnement de test

- Machine : MacBook Pro (Apple Silicon / aarch64)
- OS : macOS
- Docker Desktop : dernière version
- Keycloak : 24.0 (image quay.io/keycloak/keycloak:24.0)
- Mode : start-dev (développement local)
- Base de données : PostgreSQL 15 (container Docker dédié)
- Frontend : Angular 19 avec keycloak-js
- Backend : Spring Boot 4 avec spring-boot-starter-oauth2-resource-server

---

## Étapes clés de l'expérimentation

### 1. Déploiement de Keycloak en Docker
Lancement via Docker Compose avec une base PostgreSQL dédiée.
Configuration des variables d'environnement KC_DB, KC_HOSTNAME,
KC_HOSTNAME_PORT pour permettre la communication entre containers.

### 2. Configuration du realm collector
Création du realm, des 3 rôles (ROLE_BUYER, ROLE_SELLER, ROLE_ADMIN),
du client collector-backend et d'un utilisateur de test.

### 3. Intégration Angular (client public)
Installation de keycloak-js, création du KeycloakService, initialisation
via APP_INITIALIZER pour garantir l'init avant le bootstrap Angular.
Configuration du client en mode public (Client authentication OFF)
car les secrets ne peuvent pas être cachés dans une SPA navigateur.

### 4. Intégration Spring Boot (resource server)
Configuration de spring-security-oauth2-resource-server avec validation
JWT via JWK Set URI. Implémentation d'un JwtAuthenticationConverter
personnalisé pour extraire les rôles depuis le claim realm_access.

### 5. Résolution du conflit issuer/container
Problème identifié : le backend Docker tentait de valider les tokens
via http://localhost:8180 mais localhost dans un container Docker
pointe vers le container lui-même, pas la machine hôte.
Solution : issuer-uri reste http://localhost:8180 (pour matcher
les tokens émis) mais jwk-set-uri utilise http://keycloak:8080
(nom du service Docker pour la communication interne).

---

## Difficultés rencontrées

| Difficulté | Cause | Solution |
|---|---|---|
| Silent SSO échoue | Conflit HTTP/HTTPS iframe | Désactivation checkLoginIframe |
| 401 sur POST | Rôle ROLE_SELLER manquant | Assignation du rôle dans Keycloak |
| Token rejeté en Docker | Conflit issuer localhost vs container | URLs différentes pour issuer et JWK |
| Client authentication | SPA ne peut pas garder un secret | Passage en client public |

---

## Résultats obtenus

- Authentification OAuth2/OIDC fonctionnelle entre Angular et Keycloak
- Tokens JWT validés par Spring Boot via JWK Set
- Autorisation par rôles opérationnelle (ROLE_SELLER requis pour POST)
- Login/logout fonctionnel depuis le frontend Dockerisé

---

## Limites identifiées

- Mode start-dev non adapté à la production (pas de clustering,
  configuration simplifiée)
- Certificat auto-signé Nginx non reconnu par Keycloak en HTTPS strict
- La gestion du refresh token automatique n'est pas implémentée —
  l'utilisateur doit se reconnecter après expiration du token
- En production, Keycloak nécessiterait un déploiement dédié avec
  haute disponibilité et backup de la base de données

---

## Justification de l'adoption

Keycloak a été retenu car il fournit une solution complète OAuth2/OIDC
open source, évitant de développer un système d'authentification custom.
Il supporte nativement les rôles, les realms multi-tenant, et s'intègre
avec les standards Spring Security. Les difficultés rencontrées sont
documentées et résolues, validant sa faisabilité dans ce contexte.