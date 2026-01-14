# BurpSS: for Burp Suite

BurpSS is a Burp Suite extension that automates high-quality screenshots of your Burp Suite window. This version is streamlined to auto-detect the Burp window and captures clean screenshots without mouse cursors.

## Prerequisites

Before you begin, ensure you have the following installed:

1.  **Java Development Kit (JDK) 11** or higher.
2.  **Maven** (for building the project).
3.  **Burp Suite** (Community or Professional).

## Step 1: Project Setup

1.  Create a new folder for your project (e.g., `burpss`).
2.  Inside that folder, create a file named `pom.xml` and paste the Maven configuration provided previously.
3.  Create the following directory structure for your Java source code:
    ```
    src/main/java/burp/
    ```
4.  Inside `src/main/java/burp/`, create a file named `BurpExtender.java` and paste the updated Java code provided previously.

Your structure should look like this:
```text
burpss/
├── pom.xml
└── src
    └── main
        └── java
            └── burp
                └── BurpExtender.java
