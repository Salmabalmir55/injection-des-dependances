# 🌱 Spring IoC & Dependency Injection — TP Partie 1

![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/Spring%20Framework-6.x-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.8%2B-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)

> Travaux Pratiques — Inversion de Contrôle (IoC) et Injection de Dépendances (DI) avec le Framework Spring.  
> Ce projet illustre le passage progressif d'un **couplage fort** vers un **couplage faible**, puis vers une gestion automatisée des dépendances via Spring.

---

## 📚 Table des matières

- [Contexte et objectifs](#-contexte-et-objectifs)
- [Architecture du projet](#-architecture-du-projet)
- [Structure des fichiers](#-structure-des-fichiers)
- [Concepts clés](#-concepts-clés)
- [Partie 1 — Étapes détaillées](#-partie-1--étapes-détaillées)
  - [1. Interface IDao](#1-interface-idao)
  - [2. Implémentation de IDao](#2-implémentation-de-idao)
  - [3. Interface IMetier](#3-interface-imetier)
  - [4. Implémentation de IMetier (couplage faible)](#4-implémentation-de-imetier-couplage-faible)
  - [5a. Injection statique](#5a-injection-de-dépendances--instanciation-statique)
  - [5b. Injection dynamique](#5b-injection-de-dépendances--instanciation-dynamique)
  - [5c. Spring XML](#5c-injection-via-spring--version-xml)
  - [5d. Spring Annotations](#5d-injection-via-spring--version-annotations)
- [Installation et exécution](#-installation-et-exécution)
- [Ressources](#-ressources)

---

## 🎯 Contexte et objectifs

Ce TP a pour but de comprendre et d'appliquer le principe d'**Inversion de Contrôle (IoC)** et les différentes techniques d'**Injection de Dépendances (DI)** en Java, en évoluant progressivement vers l'utilisation du framework **Spring**.

| Approche | Flexibilité | Couplage |
|---|---|---|
| Instanciation statique | ❌ Faible | Fort |
| Instanciation dynamique | ✅ Moyenne | Faible |
| Spring XML | ✅✅ Haute | Très faible |
| Spring Annotations | ✅✅✅ Maximale | Très faible |

---

## 🏗️ Architecture du projet

Le projet suit une architecture en couches classique respectant le **principe de séparation des responsabilités** :

```
Présentation (main)
      │
      ▼
  IMetier  ◄──── injection ────  IDao
      │                            │
MetierImpl                    DaoImpl
```

La couche **Métier** ne connaît que l'interface `IDao`, jamais son implémentation concrète → **couplage faible**.

---

## 📁 Structure des fichiers

```
spring-ioc-tp/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── ma/projet/
│       │       ├── dao/
│       │       │   ├── IDao.java                  # Interface couche DAO
│       │       │   └── DaoImpl.java               # Implémentation DAO
│       │       ├── metier/
│       │       │   ├── IMetier.java               # Interface couche Métier
│       │       │   └── MetierImpl.java            # Implémentation Métier (couplage faible)
│       │       └── presentation/
│       │           ├── PresentationStatique.java  # Injection statique
│       │           ├── PresentationDynamique.java # Injection dynamique
│       │           ├── PresentationSpringXML.java # Injection via Spring XML
│       │           └── PresentationAnnotations.java # Injection via Annotations
│       │
│       └── resources/
│           └── applicationContext.xml             # Configuration Spring XML
│
├── pom.xml
└── README.md
```

---

## 💡 Concepts clés

### Couplage fort vs. couplage faible

```java
// ❌ Couplage FORT — MetierImpl dépend directement de DaoImpl
public class MetierImpl {
    private DaoImpl dao = new DaoImpl(); // lié à l'implémentation concrète
}

// ✅ Couplage FAIBLE — MetierImpl dépend de l'interface IDao
public class MetierImpl implements IMetier {
    private IDao dao; // dépend de l'abstraction
    // dao sera injecté de l'extérieur
}
```

### Inversion de Contrôle (IoC)

> Au lieu que **la classe** crée ses propres dépendances, c'est **un conteneur externe** (Spring) qui les crée et les injecte.

---

## 🔬 Partie 1 — Étapes détaillées

### 1. Interface `IDao`

Définit le contrat de la couche d'accès aux données.

```java
package ma.projet.dao;

public interface IDao {
    double getData();
}
```

---

### 2. Implémentation de `IDao`

```java
package ma.projet.dao;

import org.springframework.stereotype.Repository;

@Repository("dao")
public class DaoImpl implements IDao {

    @Override
    public double getData() {
        System.out.println("Version Base de Données");
        return 23.5;
    }
}
```

---

### 3. Interface `IMetier`

Définit le contrat de la couche métier.

```java
package ma.projet.metier;

public interface IMetier {
    double calcul();
}
```

---

### 4. Implémentation de `IMetier` (couplage faible)

La classe `MetierImpl` dépend de **l'interface** `IDao` et non de son implémentation.  
La dépendance est exposée via un **setter** pour permettre l'injection.

```java
package ma.projet.metier;

import ma.projet.dao.IDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("metier")
public class MetierImpl implements IMetier {

    @Autowired
    private IDao dao;

    @Override
    public double calcul() {
        double data = dao.getData();
        return data * 2; // logique métier
    }

    // Setter pour l'injection manuelle (statique / dynamique)
    public void setDao(IDao dao) {
        this.dao = dao;
    }
}
```

---

### 5a. Injection de Dépendances — Instanciation Statique

Les objets sont instanciés **manuellement** dans le code. Peu flexible (couplage fort au niveau de la présentation).

```java
package ma.projet.presentation;

import ma.projet.dao.DaoImpl;
import ma.projet.metier.MetierImpl;

public class PresentationStatique {
    public static void main(String[] args) {
        // Instanciation manuelle
        DaoImpl dao = new DaoImpl();
        MetierImpl metier = new MetierImpl();

        // Injection manuelle via le setter
        metier.setDao(dao);

        System.out.println("Résultat = " + metier.calcul());
    }
}
```

---

### 5b. Injection de Dépendances — Instanciation Dynamique

Le nom de la classe à instancier est lu depuis un **fichier de configuration** (`config.txt`), ce qui permet de changer l'implémentation sans recompiler.

**`config.txt`** (placé à la racine du projet) :
```
ma.projet.dao.DaoImpl
ma.projet.metier.MetierImpl
```

```java
package ma.projet.presentation;

import ma.projet.dao.IDao;
import ma.projet.metier.IMetier;
import ma.projet.metier.MetierImpl;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Scanner;

public class PresentationDynamique {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("config.txt"));

        // Chargement dynamique de l'implémentation DAO
        String daoClassName = scanner.nextLine();
        Class<?> daoClass = Class.forName(daoClassName);
        IDao dao = (IDao) daoClass.getConstructor().newInstance();

        // Chargement dynamique de l'implémentation Métier
        String metierClassName = scanner.nextLine();
        Class<?> metierClass = Class.forName(metierClassName);
        IMetier metier = (IMetier) metierClass.getConstructor().newInstance();

        // Injection via la méthode setDao (reflection)
        Method setDao = metierClass.getMethod("setDao", IDao.class);
        setDao.invoke(metier, dao);

        System.out.println("Résultat = " + metier.calcul());
    }
}
```

---

### 5c. Injection via Spring — Version XML

Spring gère la création des objets et l'injection des dépendances via un fichier XML.

**`src/main/resources/applicationContext.xml`** :
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- Déclaration du bean DAO -->
    <bean id="dao" class="ma.projet.dao.DaoImpl"/>

    <!-- Déclaration du bean Métier avec injection par setter -->
    <bean id="metier" class="ma.projet.metier.MetierImpl">
        <property name="dao" ref="dao"/>
    </bean>

</beans>
```

**Classe de présentation :**
```java
package ma.projet.presentation;

import ma.projet.metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PresentationSpringXML {
    public static void main(String[] args) {
        // Spring lit le fichier XML et crée le contexte applicatif
        ApplicationContext context =
            new ClassPathXmlApplicationContext("applicationContext.xml");

        // Récupération du bean — Spring a déjà injecté les dépendances
        IMetier metier = context.getBean("metier", IMetier.class);

        System.out.println("Résultat = " + metier.calcul());
    }
}
```

---

### 5d. Injection via Spring — Version Annotations

Spring détecte automatiquement les beans grâce aux annotations (`@Component`, `@Service`, `@Repository`) et injecte les dépendances avec `@Autowired`.

```java
package ma.projet.presentation;

import ma.projet.metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "ma.projet")
public class PresentationAnnotations {
    public static void main(String[] args) {
        ApplicationContext context =
            new AnnotationConfigApplicationContext(PresentationAnnotations.class);

        IMetier metier = context.getBean(IMetier.class);
        System.out.println("Résultat = " + metier.calcul());
    }
}
```

> **Annotations utilisées :**
> - `@Repository` → sur `DaoImpl` (couche accès données)
> - `@Service` → sur `MetierImpl` (couche métier)
> - `@Autowired` → sur l'attribut `dao` dans `MetierImpl` pour l'injection automatique

---

## ⚙️ Installation et exécution

### Prérequis

- Java 17+
- Maven 3.8+
- IDE : IntelliJ IDEA / Eclipse

### Dépendances Maven (`pom.xml`)

```xml
<dependencies>
    <!-- Spring Context (IoC Container) -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>6.1.6</version>
    </dependency>
</dependencies>
```

### Lancer le projet

```bash
# Cloner le dépôt
git clone https://github.com/<votre-username>/spring-ioc-tp.git
cd spring-ioc-tp

# Compiler
mvn compile

# Exécuter une présentation (exemple : statique)
mvn exec:java -Dexec.mainClass="ma.projet.presentation.PresentationStatique"
```

---

## 📎 Ressources

| Ressource | Lien |
|---|---|
| 🎥 Vidéo du cours | [YouTube — TP Spring IoC](https://www.youtube.com/watch?v=vOLqabN-n2k) |
| 📖 Documentation Spring | [spring.io/docs](https://spring.io/docs) |
| ☕ Java Reflection API | [Oracle Docs](https://docs.oracle.com/javase/tutorial/reflect/) |

---

## 👨‍💻 Auteur

Projet réalisé dans le cadre d'un TP académique sur les Design Patterns et l'architecture logicielle en Java avec Spring Framework.

---
