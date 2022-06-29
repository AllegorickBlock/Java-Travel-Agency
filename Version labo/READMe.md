<h1>TODO</h1>

<h3>Done:</h3>
- Coté client toutes les principales features
- Coté server toutes les principales features


<h3>Not finish</h3>

- Réaliser le calculer des distance/prix quand la ville de départ n'est pas sélectionnée.
- La propagation de la suppression d'un trip sur le serveur (fonctionne coté client)
- Double dispatch manquant (communication client/serveur pas belle actuellement)
- Duplication de code entre la création normale d'un élément et ça regénération à partir d'un objet déjà existant
- Sauvegarde du fichier binaire se fait à la fermeture d'un client et non à la fermeture du serveur (la lecture se fait bien via le serveur)
- Le coté responsive des vues
- Le lock d'un trip en cour d'édition quand on fait venir un nouveau client (le trip est accesible alors qu'il est en modification ailleurs)