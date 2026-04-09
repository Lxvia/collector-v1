# Cartographie des compétences — Équipe Collector.shop

## Composition de l'équipe

| Rôle | Profil | Nombre |
|---|---|---|
| Lead Developer | Senior fullstack + DevSecOps | 1 |
| Développeur confirmé Backend | Java/Spring, 5 ans exp. | 1 |
| Développeur confirmé Frontend | Angular/TypeScript, 5 ans exp. | 1 |
| DevOps Engineer | CI/CD, Docker, cloud | 1 |
| QA Engineer | Tests automatisés, sécurité | 1 |

---

## Matrice des compétences nécessaires

| Compétence | Lead Dev | Dev Backend | Dev Frontend | DevOps | QA |
|---|---|---|---|---|---|
| Spring Boot / Java | ★★★ | ★★★ | ★ | ★ | ★ |
| Angular / TypeScript | ★★★ | ★ | ★★★ | ★ | ★ |
| OAuth2 / Keycloak | ★★★ | ★★ | ★★ | ★★ | ★ |
| Docker / Compose | ★★★ | ★★ | ★★ | ★★★ | ★★ |
| CI/CD GitHub Actions | ★★★ | ★★ | ★★ | ★★★ | ★★ |
| Tests JUnit / Mockito | ★★★ | ★★★ | ★ | ★ | ★★★ |
| Prometheus / Grafana | ★★ | ★ | ★ | ★★★ | ★★ |
| Sécurité applicative | ★★★ | ★★ | ★★ | ★★ | ★★★ |
| PostgreSQL | ★★ | ★★★ | ★ | ★★ | ★ |

*★★★ = expert, ★★ = confirmé, ★ = notions*

---

## Gaps identifiés et action de formation

### Gap principal : Sécurité applicative (DevSecOps)

**Constat** : Les développeurs backend et frontend ont des notions
de sécurité mais ne maîtrisent pas les pratiques DevSecOps avancées
(OWASP Top 10, analyse statique de code, gestion des secrets).

**Action de formation proposée : Formation OWASP DevSecOps**

- Contenu : OWASP Top 10, intégration SAST/DAST dans le pipeline CI/CD,
  gestion des secrets avec HashiCorp Vault, threat modeling
- Format : 3 jours en présentiel + 1 mois de mise en pratique
- Cible : Dev Backend + Dev Frontend + QA Engineer
- Objectif : autonomie sur l'identification et la correction des
  vulnérabilités sans intervention du Lead Developer
- Résultat attendu : réduction du nombre de CVE détectées par Trivy
  et amélioration du score de sécurité OWASP ZAP

### Gap secondaire : Observabilité avancée

**Constat** : L'équipe DevOps maîtrise Prometheus/Grafana mais
les développeurs ne savent pas instrumenter leur code avec
des métriques métier personnalisées (Micrometer custom metrics).

**Action de formation proposée : Workshop interne Micrometer**

- Contenu : création de métriques custom, alerting Grafana,
  corrélation logs/métriques/traces (OpenTelemetry)
- Format : 1 journée workshop animée par le Lead Developer
- Cible : Dev Backend + DevOps