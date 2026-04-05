# Stationnement
Développement d’un simulateur et un optimiseur simples de stationnement à l'aide de java.
C'est un projet réalisé en 2 parties, tel qu'on retrouve leur codes respectif dans Parking1 et Parking2.

Parking1 pose les fondations d'un simulateur de stationnement en Java en mettant l'accent sur la gestion de données structurées et la lecture de fichiers. L'objectif est de modéliser la configuration et l'état d'occupation d'un parking à partir d'un fichier externe (type .inf) en utilisant un tableau bidimensionnel d'objets. Il faut implémenter les fonctionnalités nécessaires pour identifier les types de places disponibles ( dépendamment du type de voiture entrant au parking) et gérer le stationnement précis des véhicules, tout en appliquant les principes fondamentaux de la programmation orientée objet pour garantir une représentation en mémoire fidèle à la conception logique du système.

Parking2 fait évoluer le système vers une simulation dynamique pilotée par une horloge ( 24h simulées), introduisant la gestion de flux de véhicules via des structures de données en files d'attente (queues). L'implémentation doit simuler les barrières d'entrée et de sortie d'un parking sur une période complète de 24 heures (soit 86 400 itérations logiques), où chaque cycle de l'horloge système traite les demandes de passage. Le travail exige une coordination rigoureuse entre le moteur de simulation et l'état du stationnement, transformant un simple outil de stockage de données en un environnement réactif et temporel.

_______________________________
(english version):
# Parking
Development of a simple parking simulator and optimizer using Java.

This project is completed in two parts, as their respective code can be found in Parking1 and Parking2.

Parking1 lays the foundation for a Java parking simulator, focusing on structured data management and file reading. The goal is to model the configuration and occupancy status of a parking lot from an external file (.inf type) using a two-dimensional array of objects. It is necessary to implement the features required to identify the types of available spaces (depending on the type of car entering the parking lot) and manage the precise parking of vehicles, while applying the fundamental principles of object-oriented programming to ensure a memory representation faithful to the system's logical design.

Parking2 evolves the system into a dynamic simulation driven by a clock (simulated 24 hours), introducing vehicle flow management via queued data structures. The implementation must simulate the entry and exit barriers of a parking lot over a full 24-hour period (86,400 logical iterations), where each cycle of the system clock processes access requests. This requires rigorous coordination between the simulation engine and the parking lot's state, transforming a simple data storage tool into a reactive, time-dependent environment.
