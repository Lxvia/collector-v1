# Plan de remédiation sécurité — Collector.shop V1

## Contexte

Après audit de la V1 et analyse des résultats du test de charge Siege
(534 req/sec, longest transaction 6730ms) et du scan Trivy, voici
les vulnérabilités identifiées et les actions de remédiation proposées.

---

## Vulnérabilités identifiées

### V1 — Certificat auto-signé (CRITIQUE)

**Constat** : Le certificat TLS utilisé est auto-signé. Les navigateurs
affichent un avertissement de sécurité et le certificat n'est pas
reconnu par les autorités de certification.

**Risque** : Attaques man-in-the-middle, perte de confiance utilisateur,
non-conformité PCI-DSS (obligatoire pour les transactions financières).

**Remédiation** :
- En production : utiliser Let's Encrypt via cert-manager (Kubernetes)
  ou Certbot (standalone) pour un certificat signé par une CA reconnue
- Renouvellement automatique du certificat tous les 90 jours
- Priorité : HAUTE — à traiter avant toute mise en production

---

### V2 — Absence de rate limiting (HAUTE)

**Constat** : Aucune limitation du nombre de requêtes par IP ou par
utilisateur n'est en place. Le test Siege montre que 20 clients
simultanés génèrent 534 req/sec sans aucune restriction.

**Risque** : Attaques DDoS, brute force sur l'API d'authentification,
épuisement des ressources serveur.

**Remédiation** :
- Intégrer Spring Cloud Gateway ou Nginx rate limiting
- Limiter à 100 req/min par IP sur les endpoints publics
- Limiter à 10 tentatives de login par minute via Keycloak brute force
  protection (déjà disponible dans Keycloak, à activer)
- Priorité : HAUTE

---

### V3 — Keycloak en mode start-dev (HAUTE)

**Constat** : Keycloak est déployé en mode start-dev, non adapté
à la production.

**Risque** : Configuration non optimisée, pas de clustering,
performances dégradées sous forte charge, absence de cache distribué.

**Remédiation** :
- Passer en mode start (production) avec configuration explicite
- Activer le clustering Infinispan pour la haute disponibilité
- Séparer la base de données Keycloak sur un serveur dédié
- Priorité : HAUTE avant mise en production

---

### V4 — Absence de refresh token automatique (MOYENNE)

**Constat** : Le token JWT expire sans renouvellement automatique.
L'utilisateur est déconnecté sans avertissement.

**Risque** : Mauvaise expérience utilisateur, sessions interrompues
pendant une transaction en cours.

**Remédiation** :
- Implémenter le refresh token silencieux dans KeycloakService Angular
- Configurer updateMinValidity dans keycloak-js
- Priorité : MOYENNE

---

### V5 — Pic de latence détecté (MOYENNE)

**Constat** : Le test Siege révèle une transaction à 6730ms alors que
la moyenne est de 20ms. Ce pic correspond probablement à un cold start
du pool de connexions HikariCP.

**Risque** : Dégradation de l'expérience utilisateur en cas de charge
soudaine, timeout potentiel sur les transactions financières.

**Remédiation** :
- Configurer le pool HikariCP avec un minimum de connexions actives :
  spring.datasource.hikari.minimum-idle=5
- Activer le connection warmup au démarrage
- Ajouter un health check sur le pool de connexions dans Actuator
- Priorité : MOYENNE

---

### V6 — Logs non centralisés (BASSE)

**Constat** : Les logs sont dispersés dans chaque container Docker
sans agrégation centralisée.

**Risque** : Difficulté de détection des incidents en production,
impossibilité de corréler les événements entre services.

**Remédiation** :
- Intégrer une stack ELK (Elasticsearch, Logstash, Kibana)
  ou Loki + Grafana (déjà en place)
- Configurer Logback pour envoyer les logs vers Loki
- Priorité : BASSE

---

## Tableau de synthèse

| ID | Vulnérabilité | Priorité | Effort | Impact |
|---|---|---|---|---|
| V1 | Certificat auto-signé | CRITIQUE | Faible | Très élevé |
| V2 | Absence rate limiting | HAUTE | Moyen | Élevé |
| V3 | Keycloak start-dev | HAUTE | Moyen | Élevé |
| V4 | Refresh token | MOYENNE | Faible | Moyen |
| V5 | Pic latence HikariCP | MOYENNE | Faible | Moyen |
| V6 | Logs non centralisés | BASSE | Élevé | Faible |

---

## Priorisation des actions

**Court terme (avant mise en production) :**
V1 → V2 → V3

**Moyen terme (V2 de l'application) :**
V4 → V5

**Long terme :**
V6