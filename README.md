# BurpSS: for Burp Suite

BurpSS is a Burp Suite extension that automates high-quality screenshots of your Burp Suite window. This version is streamlined to auto-detect the Burp window and captures clean screenshots without mouse cursors.

## Prerequisites

Before you begin, ensure you have the following installed:

1.  **Java Development Kit (JDK) 11** or higher.
2.  **Maven** (for building the project).
3.  **Burp Suite** (Community or Professional).

# BurpSS Build & Install Guide

This guide explains how to install the necessary tools, build the extension using Maven, and load it into Burp Suite.

## 1. Install Prerequisites

Before running any commands, you need Java and Maven installed.

### Windows
1.  **Install Java (JDK 11+):**
    *   Download the **JDK 17 Installer** (x64 Installer) from [Oracle](https://www.oracle.com/java/technologies/downloads/).
    *   Run the installer and click "Next" until finished.
2.  **Install Maven:**
    *   Download the "Binary zip archive" from the [Maven Download Page](https://maven.apache.org/download.cgi).
    *   Extract the zip folder to a location like `C:\Program Files\Maven`.
    *   Add the `bin` folder path (e.g., `C:\Program Files\Maven\apache-maven-3.9.6\bin`) to your System Environment Variables **Path**.
3.  **Verify Installation:**
    *   Open a new Command Prompt (cmd) and type:
        ```cmd
        java -version
        mvn -version
        ```
    *   If you see version numbers for both, you are ready.

### macOS / Linux
1.  **Install via Homebrew (easiest):**
    ```bash
    brew install openjdk maven
    ```
2.  **Verify Installation:**
    ```bash
    java -version
    mvn -version
    ```

---

## 2. Prepare the Project Files

1.  Create a folder named `burpss`.
2.  Inside `burpss`, create a file named `pom.xml`.
3.  Inside `burpss`, create the folder path: `src/main/java/burp/`.
4.  Inside `src/main/java/burp/`, create a file named `BurpExtender.java`.

*(Copy and paste the code provided in the previous steps into these files)*.

---

## 3. Build the Project (The "mvn clean package" step)

This step compiles the Java code into a `.jar` file that Burp Suite can read.

1.  Open your terminal (Command Prompt or PowerShell).
2.  Navigate to your project folder:
    ```cmd
    cd path\to\your\burpss
    ```
3.  Run the build command:
    ```cmd
    mvn clean package
    ```

**What this does:**
*   `clean`: Deletes any old build files to ensure a fresh start.
*   `package`: Compiles your code and bundles it into a JAR file.

**Success:**
You will see a large `BUILD SUCCESS` message.
Maven creates a new folder called `target` inside your project directory.
The file you need is located at: `target/burpss-4.0-ULTIMATE-jar-with-dependencies.jar`.

---

## 4. Install into Burp Suite

1.  Open **Burp Suite**.
2.  Go to the **Extensions** tab -> **Installed** sub-tab.
3.  Click the **Add** button.
4.  Configure the settings:
    *   **Extension Type:** `Java`
    *   **Extension File:** Click "Select file..." and navigate to your `burpss/target/` folder. Select the file ending in `...jar-with-dependencies.jar`.
5.  Click **Next**.
    *   You should see a message saying "The extension loaded successfully."

---

## 5. How to Use

1.  In Burp Suite, look for the new tab labeled **burpss**.
2.  Click the ** Screenshot** button.
3.  A 10-second countdown will start on the button text.
4.  **Important:** Bring the Burp window (or the specific tab you want to capture) to the front of your screen.
5.  Wait for the beep sound.
6.  Check the text log in the extension tab to see where the image was saved (usually in a `burpss` folder in your User Home directory).
