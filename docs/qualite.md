# Indicateurs qualité ISO 25010 — Collector.shop

## Indicateur 1 — Fiabilité (Reliability)

**Métrique : Taux de succès des tests automatisés**

- Outil : JUnit 5 + GitHub Actions
- Mesure : % de tests verts sur chaque push (actuellement 5/5 = 100%)
- Seuil d'alerte : pipeline bloqué si un test échoue
- Lien dette technique : un test qui échoue révèle une régression.
  Sans ce suivi, des bugs s'accumulent silencieusement en production.

---

## Indicateur 2 — Performance (Performance efficiency)

**Métrique : Temps de réponse moyen et débit (req/sec)**

- Outil : Siege + Prometheus/Grafana
- Mesure : temps de réponse moyen mesuré à 20ms, débit à 534 req/sec
  (test : 20 utilisateurs simultanés, 30 secondes)
- Seuil d'alerte : temps de réponse moyen > 200ms ou disponibilité < 99%
- Lien dette technique : une dégradation progressive des performances
  non détectée conduit à refactorer l'ensemble de la couche service
  en urgence. Le suivi continu permet d'intervenir tôt.

---

## Indicateur 3 — Sécurité (Security)

**Métrique : Nombre de vulnérabilités CVE CRITICAL/HIGH détectées**

- Outil : Trivy (intégré au pipeline CI/CD GitHub Actions)
- Mesure : scan à chaque build sur l'image Docker du backend
- Seuil d'alerte : toute CVE CRITICAL bloque le pipeline
- Lien dette technique : des dépendances non mises à jour accumulent
  des vulnérabilités connues. Le scan systématique force la mise à jour
  régulière des dépendances avant que la dette de sécurité ne devienne
  critique.

---

## Indicateur 4 — Maintenabilité (Maintainability)

**Métrique : Couverture de code par les tests unitaires**

- Outil : JaCoCo (intégrable dans le pipeline Maven)
- Mesure : % de lignes couvertes par les tests unitaires
- Seuil cible : > 70% de couverture sur la couche service
- Lien dette technique : un code non couvert par les tests est
  difficile à refactorer sans risque de régression. Maintenir
  une couverture élevée garantit que les évolutions futures
  (nouvelles fonctionnalités V2) n'introduisent pas de régressions
  sur le code existant.