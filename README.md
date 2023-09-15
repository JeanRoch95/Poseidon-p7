# spring-boot
## Technical:

1. Spring Boot 3.1.0
2. Java 17
3. Thymeleaf
4. Bootstrap v.4.3.1


## Setup with Intellij IDE
1. Create project from Initializr: File > New > project > Spring Initializr
2. Add lib repository into pom.xml
3. Add folders
    - Source root: src/main/java
    - View: src/main/resources
    - Static: src/main/resource/static
4. Create database with name "demo" as configuration in application.properties
5. Run sql script to create table doc/data.sql

## Implement a Feature
1. Create mapping domain class and place in package com.nnk.springboot.domain
2. Create repository class and place in package com.nnk.springboot.repositories
3. Create controller class and place in package com.nnk.springboot.controllers

## Security
1. Create user service to load user from  database and place in package com.nnk.springboot.services
2. Add configuration class and place in package com.nnk.springboot.config


## Installation et configuration

1. Cloner le dépôt :  
   ``git clone https://github.com/JeanRoch95/Poseidon-p7.git``

2. Naviguer vers le dossier du projet  
   ``` cd poseidon-skeleton ```

3. Configurer la base de données
* Assurez-vous que MySQL ou MariaDB est installé et en cours d'éxécution
* Mettez à jour 'src/main/resources/application.properties' avec vos propres informations de connexion a la base de donnée

``spring.datasource.url=jdbc:mysql://localhost:3307/poseidon``   
``spring.datasource.username=root``  
``spring.datasource.password=votre_mot_de_passe``

4. Initialisation de la Base de Données

Pour initialiser la base de données avec des données par défault, un fichier ``data.sql`` est fournis dans le répertoire ``src/main/resources`

5. Compilez et packagez le projet avec Maven

   ```mvn clean install```

## Exécution

1. Lancer l'application

   ```mvn spring-boot:run```

2. Ouvrez un navigateur et naviguer vers

   ``http://localhost:9001``



Vous pouvez utiliser jusqu'à 3 user avant de vous inscrire :

* Username : user --- Password: pass
* Username : admin --- Password: pass