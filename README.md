# 📚 **Projet Bibliothèque BTS SIO - L'Application de Gestion de Revues Informatiques**

## 🎯 **Contexte**

La bibliothèque du **BTS SIO** possède un fonds de revues informatiques, mais ces dernières sont actuellement réservées aux **professeurs** et **non accessibles aux étudiants**. L'objectif est de développer une **application web** permettant :

- La gestion de l'alimentation des revues.
- Le suivi des **emprunts** et **retours** des étudiants.
  
Cette application doit **respecter les bonnes pratiques** de développement et garantir **sécurité** et **performance**, tout en offrant une expérience simple pour les utilisateurs.

---

## **Architecture de l'application**

1. **Back-End (Serveur)** :
   - Framework : **Spring Boot** (pour gérer les requêtes et la logique métier).
   - Base de données : **MySQL** (pour stocker toutes les données).
   - Accès aux données via **JPA** (pour une gestion fluide de la base de données).

2. **Front-End (Interface Utilisateur)** :
   - Technologie : **Angular** (pour l'interface moderne et responsive).
   - Communication avec le back-end via des **API REST**.

## **Principales Fonctionnalités**

- **Gestion des revues** : Ajouter, modifier et supprimer des revues dans la bibliothèque.
- **Emprunts et Retours** : Suivre les revues empruntées et leur retour à la date prévue.
- **Inscription des étudiants** : Permettre aux étudiants de s’inscrire et emprunter des revues.
- **Sécurité** : Sécuriser les accès selon le rôle (étudiant, admin, etc.).

---

## ⚙️ **Installation**

### **Prérequis**

Avant de commencer, tu dois t'assurer d'avoir les outils suivants installés sur ta machine :

- **JDK 11 ou +** pour le back-end.
- **MySQL** pour la base de données.
- **Gradle** pour gérer les dépendances back-end.
- **Node.js** & **npm** pour le front-end.
- **IDE** : IntelliJ, Eclipse, ou VS Code.

---

### **Récupérer le projet sur le dépot**

**Cloner le projet** à partir du repository. `git clone https://github.com/CorentinLartigue/BibliSIO_application.git`

---

### **Back-End (Spring Boot)**

1. **Configurer MySQL** : Créer la base de données `bibliosio` et configurer le fichier `application.properties` avec les informations de connexion.
2. **Installer les dépendances** et préparer le projet pour l'exécution.
3. **Lancer le serveur** Spring Boot pour que l'API soit prête à recevoir des requêtes.

Le back-end sera alors accessible sur `http://localhost:8080`.

---

### **Front-End (Angular)**

1. **Installer les dépendances** via npm.
2. **Lancer l'application** avec la commande `ng serve`.

Le front-end sera alors accessible sur `http://localhost:4200`.

---

### **Assurer la Communication entre Front-End et Back-End**

- Le back-end fonctionne sur le port `8080` et le front-end sur `4200`.
- Dans le code Angular, assure-toi que les appels API sont correctement pointés vers le back-end (`http://localhost:8080`).

---

