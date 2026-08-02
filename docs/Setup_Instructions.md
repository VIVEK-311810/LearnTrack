# Setup Instructions

## Java Environment Setup

### JDK Version
- **Java Development Kit (JDK):** Java 11 or higher (JDK 17 recommended)

### Installation Steps

#### Windows
1. Download JDK from [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or use [OpenJDK](https://jdk.java.net/)
2. Run the installer and follow the installation wizard
3. Set JAVA_HOME environment variable:
   - Right-click "This PC" → Properties → Advanced system settings
   - Click "Environment Variables"
   - Add new system variable: `JAVA_HOME = C:\Program Files\Java\jdk-17` (adjust path as per installation)
4. Add JDK bin to PATH:
   - Edit PATH and add `%JAVA_HOME%\bin`
5. Verify installation:
   ```bash
   java -version
   javac -version
   ```

#### macOS
1. Download JDK from Oracle or use Homebrew:
   ```bash
   brew install openjdk@17
   ```
2. Set JAVA_HOME in `~/.zshrc` or `~/.bash_profile`:
   ```bash
   export JAVA_HOME=$(/usr/libexec/java_home -v 17)
   export PATH=$JAVA_HOME/bin:$PATH
   ```
3. Verify installation:
   ```bash
   java -version
   javac -version
   ```

#### Linux (Ubuntu/Debian)
1. Install OpenJDK:
   ```bash
   sudo apt update
   sudo apt install openjdk-17-jdk
   ```
2. Verify installation:
   ```bash
   java -version
   javac -version
   ```

### Verification

Create and run a "Hello World" program to verify the setup:

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, Java World!");
    }
}
```

**Steps:**
1. Save the above code as `HelloWorld.java`
2. Compile: `javac HelloWorld.java`
3. Run: `java HelloWorld`
4. Expected output: `Hello, Java World!`

### Compilation and Running LearnTrack

**Compile the project:**
```bash
javac -d out $(find . -name "*.java")
```

**Run the application:**
```bash
java -cp out com.airtribe.learntrack.ui.Main
```

### IDE Setup (Optional)

For a better development experience, use an IDE like:
- **IntelliJ IDEA Community Edition** (free)
- **Eclipse IDE** (free)
- **Visual Studio Code** with Java extensions (free)

All of these IDEs handle compilation and execution automatically once JDK is properly configured.
