# Java Projet 1 - The Big Adventure
Nelson De Oliveira - Rémy Kies
___

Rapport pour le rendu bêta du projet **The Big Adventure**.

- [Java Projet 1 - The Big Adventure](#java-projet-1---the-big-adventure)
- [Travail effectué](#travail-effectué)
- [Architecture de l'archive](#architecture-de-larchive)
- [Compilation](#compilation)
- [Prochaines étapes](#prochaines-étapes)
- [Difficultées rencontrées](#difficultées-rencontrées)


# Travail effectué 

Pour la phase 0, voici les fonctionnalitées qui ont été implémentées :
* Parser du format `.map` avec tous les attributs
  * Les remontés d'erreurs se font pour l'instant via les `throw`
  * Les erreurs indiquent le fichier source et la ligne qui a trigger l'erreur.
  * Pour l'instant, le parser s'arrête à la première erreur rencontrée
* Affichage d'une map
  * Pas encore de scrolling
  * Affichage de tous les éléments précédemment parsés
  * Système de frame pour un rafraichissement à 60 FPS.
    On précise que pour le système de frame, le code est donné dans le projet de C. Cette précision est nécessaire pour éviter tous problèmes de plagiat.
* Interactions
  * Les mobs ont une bare de vie affichée au dessus de leur tête
  * Les mobs se déplacent de manière aléatoire dans la zone qui leur est défini
  * Le joueur peut se déplacer avec les flèches directionnelles ou ZQSD. On peut quitter le programme en appuyant sur n'importe quelle touche non reconnue par Zen5, c'est à dire `Echape`, `Entrée`, ...
  * Lors des déplacements, il y a des collisions avec les obstacles et les mobs 

# Architecture de l'archive

Voici l'architecture de l'archive avec les différents modules

```
.
├── images                  // Contient les images
├── lib                     // Bibliothèques utilisées
├── map                     // Contient les différentes maps
└── src     
    ├── fr.umlv.zen5        // Démo de Zen5
    ├── game                // Gestion globale des structures du jeu
    │   ├── entity          // Les entitées pouvant se déplacer (avoir des positions)
    │   │   ├── item        // Objets
    │   │   │   └── container
    │   │   └── mob         // Mobs
    │   └── environnement   // Obstacles, Décorations, Biome
    ├── graph               // Structures graphiques
    ├── parser              // Structures pour parser une map
    ├── util                // Zone, Directions, Position
    └── zen5                // Sources de Zen5
```

# Compilation

Pour compiler et exécuter le projet, on peut :
* Utiliser Eclipse (attention au fichier .classpath)
* Compiler en ligne de commande avec :
  ```bash
  javac -cp "src;lib/zen5.jar" -d classes src/main.java
  java -cp "classes;resources;lib/zen5.jar" .\src\Main.java
  ```

# Prochaines étapes

* Afficher le nom des mobs
* Pouvoir récupérer les items au sol (dans la main, inventaire)
* Permettre de faire des attaques, pouvoir perdere de la vie, en gagner en mangeant
* Scroll la caméra

# Difficultées rencontrées

* La première grosse difficulté était de trouver une structure adéquate au jeu et à tous les éléments.
* La second est de faire un parser avec un format de carte extrèmement modulable, et de pouvoir créer les éléments en conséquence. Faire un parser sans avoir un code ignoble est un défi intéressant
* La dernière étape est de lier le tout avec l'affichage, en séparant bien le moteur du jeu avec les structures.

___
Nelson De Oliveira - Rémy Kies
