# Restaurant API - Mini Projet WS REST

## MBDS 2025-2026 - Université Côte d'Azur

### Liens rapides
- **API en ligne** : https://restaurant-api-d862.onrender.com
- **Swagger UI** : https://restaurant-api-d862.onrender.com/swagger-ui.html
- **Collection Postman** : `postman_collection.json` (dans le repo)

> Premier accès : ~50 secondes (réveil du serveur gratuit)

---

### Membres de l'équipe

| Membre | Rôle | Travail réalisé |
|--------|------|-----------------|
| **Fitia ETU001776** (ainanyfitiagershom) | Backend / Logique métier | Entités, Repositories, Services, JWT, Security, Endpoints agrégation |
| **Kanto ETU001756** (kanto28) | API / Documentation | Controllers REST, HATEOAS, DTOs, Swagger, Postman, README |

---

## Description du projet

API REST de gestion de restaurant développée avec Spring Boot. L'API permet de gérer des restaurants, des menus, des catégories, des commandes et des utilisateurs avec authentification JWT.

---

## Technologies utilisées

- **Java 21**
- **Spring Boot 3.4.2**
- **Spring Data JPA** - accès aux données
- **Spring Security** - sécurisation des endpoints
- **JWT (jjwt 0.11.5)** - authentification par token
- **HATEOAS** - liens hypermedia dans les réponses
- **H2 Database** - base de données en mémoire
- **Swagger (springdoc-openapi)** - documentation API
- **Maven** - build

---

## Entités (5)

### Relations 1 à N
- **Restaurant** → **Menu** (un restaurant a plusieurs menus)
- **Restaurant** → **Order** (un restaurant a plusieurs commandes)
- **User** → **Order** (un utilisateur a plusieurs commandes)

### Relations N à N
- **Menu** ↔ **Category** (un menu peut avoir plusieurs catégories et inversement)
- **Order** ↔ **Menu** (une commande contient plusieurs menus)

---

## Architecture du projet

```
src/main/java/mg/restaurant/restapi/
├── RestapiApplication.java
├── model/          → Entités JPA (User, Restaurant, Category, Menu, Order)
├── dto/            → Data Transfer Objects (LoginDTO, RegisterDTO, MenuDTO, OrderDTO)
├── repository/     → Interfaces JPA Repository
├── service/        → Logique métier (AuthService, JwtService, etc.)
├── controller/     → API REST (AuthController, RestaurantController, etc.)
├── config/         → Sécurité (SecurityConfig, JwtAuthenticationFilter, DataLoader)
└── exception/      → Gestion des erreurs (GlobalExceptionHandler)
```

---

## Endpoints

### Authentification
| Méthode | URL | Description | Auth |
|---------|-----|-------------|------|
| POST | `/api/auth/register` | Inscription | Non |
| POST | `/api/auth/login` | Connexion (retourne un JWT) | Non |

### Restaurants
| Méthode | URL | Description | Auth |
|---------|-----|-------------|------|
| GET | `/api/restaurants` | Liste des restaurants | Non |
| GET | `/api/restaurants/{id}` | Détail d'un restaurant | Non |
| GET | `/api/restaurants?name=xxx` | Recherche par nom | Non |
| POST | `/api/restaurants` | Créer un restaurant | Oui (JWT) |
| PUT | `/api/restaurants/{id}` | Modifier un restaurant | Oui (JWT) |
| DELETE | `/api/restaurants/{id}` | Supprimer un restaurant | Oui (JWT) |

### Catégories
| Méthode | URL | Description | Auth |
|---------|-----|-------------|------|
| GET | `/api/categories` | Liste des catégories | Non |
| GET | `/api/categories/{id}` | Détail d'une catégorie | Non |
| POST | `/api/categories` | Créer une catégorie | Oui (JWT) |
| PUT | `/api/categories/{id}` | Modifier une catégorie | Oui (JWT) |
| DELETE | `/api/categories/{id}` | Supprimer une catégorie | Oui (JWT) |

### Menus
| Méthode | URL | Description | Auth |
|---------|-----|-------------|------|
| GET | `/api/menus` | Liste des menus | Non |
| GET | `/api/menus/{id}` | Détail d'un menu | Non |
| GET | `/api/menus/restaurant/{id}` | Menus d'un restaurant | Non |
| GET | `/api/menus?minPrice=5&maxPrice=15` | Filtrer par prix | Non |
| POST | `/api/menus` | Créer un menu | Oui (JWT) |
| DELETE | `/api/menus/{id}` | Supprimer un menu | Oui (JWT) |

### Commandes
| Méthode | URL | Description | Auth |
|---------|-----|-------------|------|
| GET | `/api/orders` | Liste des commandes | Non |
| GET | `/api/orders/{id}` | Détail d'une commande | Non |
| GET | `/api/orders/user/{id}` | Commandes d'un utilisateur | Non |
| POST | `/api/orders` | Créer une commande | Oui (JWT) |
| DELETE | `/api/orders/{id}` | Supprimer une commande | Oui (JWT) |

### Agrégation
| Méthode | URL | Description | Auth |
|---------|-----|-------------|------|
| GET | `/api/stats/revenue` | Chiffre d'affaires par restaurant | Non |
| GET | `/api/stats/users` | Statistiques par utilisateur | Non |

---

## Lancer le projet

### Prérequis
- Java 21
- Maven

### Commandes

```bash
# Cloner le projet
git clone https://github.com/ainanyfitiagershom/WS_REST.git
cd WS_REST

# Lancer l'application
mvn spring-boot:run
```

L'API sera accessible sur `http://localhost:8080`

### Version en ligne

L'API est déployée sur Render :
- **URL** : https://restaurant-api-d862.onrender.com
- **Swagger UI** : https://restaurant-api-d862.onrender.com/swagger-ui.html

> Note : le premier accès peut prendre ~50 secondes (réveil du serveur gratuit)

### Liens utiles (local)
- **Swagger UI** : http://localhost:8080/swagger-ui.html
- **H2 Console** : http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:restaurantdb`, user: `sa`, password: vide)

---

## Données de test

Au démarrage, l'application charge automatiquement :
- 2 utilisateurs : `admin@test.com` / `1234` (ADMIN) et `user@test.com` / `1234` (USER)
- 4 catégories : Pizza, Pâtes, Dessert, Boisson
- 2 restaurants : Chez Luigi, La Dolce Vita
- 5 menus avec catégories
- 3 commandes

---

## Tester avec Postman

1. Importer le fichier `postman_collection.json` dans Postman
2. Lancer **Login Admin** pour obtenir un token JWT
3. Le token est automatiquement sauvegardé pour les autres requêtes
4. Tester les endpoints

---

## Sécurité

- **GET** : accessible sans authentification
- **POST / PUT** : nécessite un token JWT valide
- **DELETE** : nécessite un token JWT valide
- Le token est généré lors du login et expire après 30 minutes
- Le filtre `JwtAuthenticationFilter` intercepte chaque requête et vérifie le token

---

## Exemples de requêtes

### Login
```
POST /api/auth/login
Body: { "email": "admin@test.com", "password": "1234" }
Réponse: { "token": "eyJhbG..." }
```

### Créer un restaurant (avec token)
```
POST /api/restaurants
Header: Authorization: Bearer <token>
Body: { "name": "Pizzeria Bella", "address": "5 rue du Port", "phone": "0493112233" }
```

### Agrégation - Chiffre d'affaires
```
GET /api/stats/revenue
Réponse:
[
  { "restaurantName": "Chez Luigi", "totalOrders": 2, "totalRevenue": 37.0 },
  { "restaurantName": "La Dolce Vita", "totalOrders": 1, "totalRevenue": 13.0 }
]
```
