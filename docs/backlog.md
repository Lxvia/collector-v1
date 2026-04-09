# Backlog Collector.shop — V1

## Fonctionnalité implémentée : Gestion des articles

---

### US-01 — Consulter le catalogue d'articles

**En tant que** visiteur (non authentifié)  
**Je veux** consulter la liste des articles disponibles  
**Afin de** découvrir les objets de collection proposés sur la plateforme

**Critères d'acceptation :**
- [ ] La liste des articles est accessible sans authentification
- [ ] Seuls les articles au statut APPROVED sont visibles
- [ ] Chaque article affiche : titre, description, prix, statut
- [ ] L'endpoint GET /api/items retourne HTTP 200
- [ ] La réponse est au format JSON

---

### US-02 — Publier un article en tant que vendeur

**En tant que** vendeur authentifié  
**Je veux** publier un nouvel article sur la plateforme  
**Afin de** proposer mon objet de collection à la vente

**Critères d'acceptation :**
- [ ] L'utilisateur doit être authentifié avec le rôle ROLE_SELLER
- [ ] L'article nécessite un titre et un prix (champs obligatoires)
- [ ] L'article est créé au statut DRAFT par défaut
- [ ] Un utilisateur non authentifié reçoit HTTP 401
- [ ] Un utilisateur sans rôle SELLER reçoit HTTP 403
- [ ] L'endpoint POST /api/items retourne HTTP 200 avec l'article créé

---

### US-03 — Valider ou rejeter un article en tant qu'admin

**En tant que** administrateur  
**Je veux** valider ou rejeter un article soumis par un vendeur  
**Afin de** garantir la qualité des articles publiés sur la plateforme

**Critères d'acceptation :**
- [ ] L'utilisateur doit être authentifié avec le rôle ROLE_ADMIN
- [ ] L'admin peut changer le statut vers APPROVED ou REJECTED
- [ ] Un article APPROVED devient visible dans le catalogue public
- [ ] Un article REJECTED n'apparaît pas dans le catalogue
- [ ] L'endpoint PATCH /api/items/{id}/status retourne HTTP 200
- [ ] Un utilisateur sans rôle ADMIN reçoit HTTP 403

---

## Définition of Done (DoD)

- Code revu et mergé sur la branche main
- Tests unitaires écrits et passants
- Pipeline CI/CD vert (build + tests + scan Trivy)
- Fonctionnalité démontrée via appel API ou interface utilisateur