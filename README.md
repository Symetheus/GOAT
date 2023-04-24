# GOAT
[4MOC][Android] Quizz sur les citations des personnages de la série Game of Thrones

API link: https://gameofthronesquotes.xyz/

Jeu où l'on doit deviner à qui appartient une citation dans la série Game of Thrones.

Une citation est donnée, on propose 4 personnages différents de la série Game of Thrones, et
l'utilisateur doit choisir le bon personnage.

- Parcours Quizz de 10 questions (pour se challenger et obtenir un score)
- Parcours Daily avec une citation par jour (entrainement, permet d'avoir son avancée sur toutes les
  potentielles questions)

## API Homemade

- Création d'une API pour la gestion
    - des comptes
    - des sauvegardes de scores
    - de l'historique des parties

## Fonctionnalités

- Inscription / Connexion
- Débuter le parcours Quizz
    - Pouvoir obtenir une liste de citations aléatoires via l'API GoT
    - Pouvoir obtenir des personnages aléatoires via l'API GoT
- Débuter le parcours Daily
    - Pouvoir obtenir une citation aléatoire via l'API GoT
    - Pouvoir obtenir des personnages aléatoires via l'API GoT
- Enregistrer les données reçues de l'API pour ne pas s'y pas connecter à chaque fois (où dans le
  cas où l'API crash) — Système de cache
- Accéder à son profil
- Éditer son profil
- Accéder à son historique de parties avec le score obtenu
- Accéder à son classement en comparaison avec les autres joueurs
- Accéder à la complétion du parcours Daily sur la totalité des citations possibles (avec un
  pourcentage de complétion)
