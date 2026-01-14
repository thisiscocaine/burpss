# BurpSS: for Burp Suite

BurpSS is a Burp Suite extension that automates high-quality screenshots of your Burp Suite window. This version is streamlined to auto-detect the Burp window and captures clean screenshots without mouse cursors.

## Prerequisites

Before you begin, ensure you have the following installed:

1.  **Java Development Kit (JDK) 11** or higher.
2.  **Maven** (for building the project).
3.  **Burp Suite** (Community or Professional).

Prerequisites
Java JDK 11 (or higher) installed.
Maven installed (run mvn -v in your terminal to check).
Burp Suite (Community or Professional).
Step 1: Build the Project (mvn clean package)
Prepare the Folder Structure: Ensure your files are arranged exactly like this on your computer:

Text
MyBurpProject/
├── pom.xml
└── src/
    └── main/
        └── java/
            └── burp/
                └── BurpExtender.java
Open your Terminal / Command Prompt: Navigate to the MyBurpProject folder (where pom.xml is located).

bash
cd /path/to/MyBurpProject
Run the Build Command: Type the following command and hit Enter:

bash
mvn clean package
Wait for "BUILD SUCCESS": Maven will download dependencies and compile the code.

Success: You will see a target folder created inside your project directory.
The File you need: Look inside target/ for a file named: burpss-4.0-ULTIMATE-jar-with-dependencies.jar
Step 2: Install into Burp Suite
Open Burp Suite.
Go to the Extensions tab (top row).
Click the Add button.
Extension Settings:
Extension type: Select Java.
Extension file: Click "Select file..." and navigate to your target folder. Select burpss-4.0-ULTIMATE-jar-with-dependencies.jar.
Click Next.
You should see output in the window saying "BurpSS: 4K Photo Studio" loaded successfully.
Click Close.
Step 3: Run and Use
Locate the Tab: Look at the main tabs in Burp Suite (Dashboard, Target, Proxy, etc.). You will see a new tab named burpss. Click it.

Prepare the View: Since you removed the "Select Area" tool, this extension now automatically captures the Burp Window.

Make sure Burp Suite is open and not minimized.
Arrange your tabs (e.g., Repeater or Proxy) to show exactly what you want to screenshot.
Start the Process:

Go to the burpss tab.
Click the orange button: 📸 Take 4K Screenshot (10s Delay).
The 10-Second Countdown:

The button text will start counting down: "Switch Tab! 10s", "Switch Tab! 9s"...
IMMEDIATELY switch to the tab you want to photograph (e.g., go to the Repeater tab).
Do not move the mouse over the data (since you removed the cursor drawer, the mouse won't be drawn anyway, but it's good practice).
Capture:

After 10 seconds, you will hear a system Beep.
Go back to the burpss tab.
Check the "Log" text area. It will say: ✅ Screenshot Saved: C:\Users\YourName\burpss\burpss_2026-01-14_....png
Step 4: View Your Screenshot
Open your file explorer.
Navigate to your user home directory.
Windows: C:\Users\YourName\burpss\
Mac/Linux: /Users/YourName/burpss/ or /home/YourName/burpss/
Open the PNG image. It will have the rounded corners (CORNER_RADIUS = 30) applied automatically.
