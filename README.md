# <!--

LABORATOIRE 2 - 420-930-MA - Ete 2026 - gr. 25604
Template README a remplir et deposer sur Teams avant la deadline
====================================================================

Instructions :

1. Copiez ce fichier dans votre depot GitHub sous le nom README.md
2. Remplissez toutes les sections marquees [A COMPLETER]
3. Supprimez tous les commentaires HTML (<!-- ... -->) avant la remise
4. Deposez ce fichier (rempli) sur Teams, canal du groupe
   avec le titre : "Lab2 - Sujet X - Nom1 Nom2 [Nom3]"
   ====================================================================
   -->

# Mudic Catalog - Lab2

**Cours** : 420-930-MA — Algorithmes et modèles de programmation
**Session** : Été 2026, groupe 25604
**Laboratoire** : 2 (Application JavaFX v1)
**Date de remise** : 11 septembre 2026, 23h59

---

## Équipe

| Nom complet    | Adresse courriel   | Contribution principale           |
| -------------- |--------------------| --------------------------------- |
| Émile Valade   | e.valade@proton.me | Modèle + Service + Tris (backend) |
| Jean-Simon Cyr | jsimcyr@gmail.com  | XML + Controller + CSS (frontend) |

---

## Sujet choisi

**Numéro du sujet** : 3
**Nom du sujet** : Spotify Playlist Manager

---

## 🔗 Lien du dépôt GitHub PUBLIC

**URL** : https://github.com/Kkiriya/Music-Playlist-Manager

> ⚠️ Vérifier que le dépôt est **PUBLIC** et accessible sans authentification.
> Tester le lien dans un navigateur privé avant la remise.

---

## Fonctionnalités implémentées

### ✅ Obligatoires (cocher ce qui est fait)

- [ ] Architecture MVC avec packages séparés (model / service / algorithmes / controller / util)
- [X] Chargement des données depuis fichier CSV (nombre de lignes : 420 chansons)
- [X] Interface JavaFX principale avec liste/tableau
- [X] Panneau détail affichant l'élément sélectionné
- [X] Pagination fonctionnelle (taille de page : 25 par defaut, options 10 / 25 / 50 / 100)
- [X] Filtres multi-critères combinables (nombre implémentés : 5 / 4 demandés)
- [X] Recherche par texte en temps réel
- [X] Interface Algorithme définie
- [X] Tri #1 implémenté : Bubble sort
- [X] Tri #2 implémenté : Selection sort
- [X] Tri #3 implémenté : Insertion sort
- [X] Comparateur/benchmark des tris avec mesure du temps
- [ ] Wishlist / Favoris (ajout, retrait, pas de doublons)
- [X] CSS appliqué (thème visuel du projet)

### 🎁 Bonus (cocher ce qui est fait)

- [ ] [Bonus 1 : ex. Mode sombre/clair]
- [ ] [Bonus 2 : ex. Statistiques]
- [ ] [Bonus 3 : ...]

### ❌ Non implémenté (assumer honnêtement)

- Tout ce qui est playlist
- Recherche insensible aux accents

---

## Structure du projet

```
Music-Playlist-Manager/
|-- pom.xml
|-- src/main/java/
|   |-- module-info.java
|   `-- com/maisonneuve/music_playlist_manager/
|       |-- MainFx.java
|       |-- model/
|       |-- algorithm/
|       |-- controller/
|       `-- util/
`-- src/main/resources/com/maisonneuve/music_playlist_manager/
    |-- fxml/principal.fxml
    |-- views/lab.fxml
    |-- styles/theme.css
    |-- assets/
    `-- data/songs.csv
```

---

## Instructions pour lancer le projet

### Prérequis

- JDK 17 ou plus
- Maven 3.x
- (optionnel) IntelliJ IDEA / Eclipse

### Étapes

```bash
# 1. Cloner le dépôt
git clone https://github.com/Kkiriya/Music-Playlist-Manager
cd Music-Playlist-Manager

# 2. Compiler
mvn clean compile

# 3. Lancer l'application
mvn javafx:run
```

### Alternative dans IntelliJ

1. Ouvrir le projet dans IntelliJ (File > Open > dossier du projet)
2. Attendre que Maven télécharge les dépendances
3. Ouvrir `MainFx.java`
4. Cliquer sur le bouton Run

---

## Choix techniques

### Version Java utilisée

Java 17 avec JavaFX 21.

### Format des données

CSV avec virgule comme separateur, lu avec OpenCSV en UTF-8. Le fichier contient 420 chansons.

### Algorithmes de tri implémentés

- Bubble sort : O(n^2)
- Selection sort : O(n^2)
- Insertion sort : O(n^2)
- Merge sort : O(n log n)
- Quick sort : O(n log n) en moyenne

### Bibliothèques externes utilisées

- OpenCSV pour lire le fichier CSV
- JUnit pour les tests

---

## Difficultés rencontrées

- Lecture du CSV avec des champs contenant des virgules : utilisation de OpenCSV.
- Mise à jour du tableau JavaFX apres les filtres, tris et pagination.
- Organisation du contrôleur principal en petites classes pour garder le code plus lisible.

---

## Répartition du travail (auto-évaluation)

| Membre  | % contribution estimée | Ce sur quoi j'ai travaillé                                                       |
| ------- | ---------------------- |----------------------------------------------------------------------------------|
| Emile Valade | 25% | Base du modele, diagramme de classes,transformation des données |
| Jean-Simon Cyr | 75% | FXML, controleurs, chargement CSV, filtres, pagination, tri, benchmark, CSS      |

---

## Notes pour le correcteur

Le benchmark est accessible avec le bouton `Benchmark` dans la barre du haut.

---

## Captures d'écran (fortement recommandé)

Aucune capture d'ecran ajoutee pour le moment.

Exemple :

```markdown
### Écran principal

![Écran principal](screenshots/principal.png)

### Écran de benchmark

![Benchmark](screenshots/benchmark.png)
```

---

## Historique Git

**Nombre total de commits** : 30
**Date du premier commit** : 2026-09-08
**Date du dernier commit** : 2026-09-13

Voir l'onglet **Insights > Contributors** de GitHub pour voir la contribution de chacun.

---

# <!--

# CHECKLIST FINALE AVANT LA REMISE (a supprimer avant remise)

[ ] Tous les [A COMPLETER] ont ete remplaces par de vrais contenus
[ ] Tous les commentaires HTML <!-- ... --> ont ete supprimes
[ ] Le lien GitHub est valide (teste dans un navigateur prive)
[ ] Le depot est PUBLIC (pas Prive)
[ ] Le README.md est bien present a la RACINE du depot
[ ] Le projet compile avec "mvn clean compile" sans erreur
[ ] Le projet lance avec "mvn javafx:run" sans erreur
[ ] Les donnees (CSV) sont dans src/main/resources/data/
[ ] Le .gitignore exclut target/, .idea/, out/
[ ] Chaque membre de l'equipe a des commits a son nom
[ ] Ce fichier README rempli a ete deposé sur Teams

# DATE LIMITE : 11 septembre 2026, 23h59

-->
