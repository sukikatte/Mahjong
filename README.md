# 🀄️ Mahjong Game - Cross-Platform Desktop Application

A comprehensive Mahjong desktop game built with Java 17 and JavaFX 21, featuring complete game rules, multi-player support, and cross-platform compatibility.

## 🚀 Features

- **Complete Mahjong Rules**: Full implementation of traditional Mahjong gameplay
- **Multi-Player Support**: 4-player local game
- **Advanced AI**: Sophisticated Hu detection algorithms
- **Cross-Platform**: Supports Windows, Mac (Intel/Apple Silicon), and Linux
- **Modern GUI**: Built with JavaFX for smooth user experience
- **Comprehensive Testing**: Unit tests with JUnit and TestNG

## 🛠️ Technology Stack

- **Java 17** - Modern Java with latest features
- **JavaFX 21.0.1** - Cross-platform GUI framework
- **Maven 3.11.0** - Build and dependency management
- **JUnit 4.12 & 5.8.1** - Unit testing frameworks
- **TestNG 7.9.0** - Advanced testing framework

## 📋 Prerequisites

- **Java 17** or higher
- **Maven 3.6+**
- **Operating System**: Windows 10/11, macOS 10.15+, or Linux

## 🚀 Quick Start

### Option 1: Using Maven (Recommended)
```bash
# Clone the repository
git clone https://github.com/sukikatte/Mahjong.git
cd Mahjong

# Run the game
mvn javafx:run
```

### Option 2: Using Platform-Specific Scripts

#### Windows
```cmd
run-mahjong-windows.bat
```

#### Mac/Linux
```bash
./run-mahjong-cross-platform.sh
```

#### Universal Cross-Platform Script
```bash
./run-mahjong-cross-platform.sh
```

## 🏗️ Project Structure

```
src/
├── Application.java          # Application entry point
├── Game/                     # Core game logic
│   ├── Game.java            # Main game controller
│   ├── DetermineHu.java     # Hu detection algorithm
│   ├── CountScore.java      # Scoring system
│   └── LoginInterface.java # Login interface
├── GameInterface/           # User interface layer
├── Player/                  # Player management
├── Item/                    # Mahjong tile entities
├── Table/                   # Game table management
├── Dice/                    # Dice logic
└── Initialization/          # Initialization modules
```

## 🎮 Game Features

- **Complete Tile Set**: All traditional Mahjong tiles (筒、条、万、风牌、箭牌)
- **Game Rules**: Chi, Peng, Gang, and Hu detection
- **Scoring System**: Multiple Hu types with different scores
- **Multi-Directional Display**: Support for East, South, West, North orientations
- **Interactive GUI**: Mouse-based tile interaction

## 🔧 Development

### Building from Source
```bash
# Compile the project
mvn clean compile

# Run tests
mvn test

# Package the application
mvn clean package
```

### Running Tests
```bash
# Run JUnit tests
mvn test

# Run TestNG tests
mvn test -Dtest=TestNG
```

## 🌍 Platform Support

| Platform | Architecture | Status |
|----------|-------------|--------|
| Windows  | x64, x86    | ✅ Supported |
| macOS    | Intel, Apple Silicon | ✅ Supported |
| Linux    | x64, ARM64  | ✅ Supported |

## 📊 Project Statistics

- **Java Classes**: 21 files
- **Lines of Code**: ~2,664 lines
- **Resource Files**: 154 image files
- **Dependencies**: 7 major dependencies
- **Test Coverage**: 3 testing frameworks

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Traditional Mahjong rules and gameplay
- JavaFX community for excellent documentation
- Open source Java ecosystem

---

**Enjoy playing Mahjong! 🀄️**
