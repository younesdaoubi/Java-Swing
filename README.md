# Java-Swing
virtual aquarium (personnal project) Aquarium containing different fish with different behavior (sociability, reproduction) evolving in an environment (aquarium) adjustable with keyboard keys (water temperature, etc.)

## Fonctionnalités

L'application simule un écosystème d'aquarium avec différents types de poissons ayant des comportements uniques :

- **Poisson rouge** : Chasseur qui traque et mange les autres poissons non rouges. Sa vitesse varie selon la température de l'aquarium.
- **Poisson orange** : Se déplace de manière aléatoire jusqu'à recevoir une nouvelle direction.
- **Poisson mauve** : Fuit les poissons rouges et accélère en fonction du nombre de poissons oranges présents.
- **Poisson bleu** : Sociable, il suit le poisson rouge ou mauve le plus proche.
- **Poisson vert** : Curieux et insouciant, il se rapproche du poisson le plus proche, sans se soucier du danger.

### Autres éléments :
- **Insectes et pastilles** : Les insectes donnent un bonus de vitesse temporaire, tandis que les pastilles ralentissent les poissons selon les collisions avec les décors.
- **Température** : Modifiable entre froid, tiède et chaud, elle influence la vitesse des poissons rouges.
- **Reproduction** : Possible entre poissons de même espèce sous certaines conditions, avec une limite à 60 poissons pour éviter la surpopulation.
- **Collisions** : Obstacles mobiles verticaux générés aléatoirement, empêchant le passage des poissons.
- **Commandes** : Des interactions permettent de modifier l'environnement et les comportements des poissons.

L'application respecte l'héritage et le polymorphisme avec une gestion centralisée des comportements via la classe abstraite `Fish`, garantissant flexibilité et évolutivité du système.


## Installation et Exécution

L'application suit un processus automatisé de compilation et d'exécution grâce au script `run.sh`.

Utilisez les commandes suivantes pour exécuter le programme :


lancer la commande "bash run.sh" a partir du fichier run.sh  
