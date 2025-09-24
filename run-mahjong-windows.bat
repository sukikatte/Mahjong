@echo off
chcp 65001 >nul

REM 麻将游戏 - Windows运行脚本
REM 支持Windows 10/11

echo 🀄️  启动麻将游戏 (Windows版)
echo ==================================

REM 检测架构
if "%PROCESSOR_ARCHITECTURE%"=="AMD64" (
    set JAVAFX_SUFFIX=win
    echo 🪟 检测到Windows 64位系统
) else (
    set JAVAFX_SUFFIX=win32
    echo 🪟 检测到Windows 32位系统
)

REM 检查Java
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ 未找到Java，请确保Java 17已安装并添加到PATH
    pause
    exit /b 1
)

echo ☕ 使用Java版本:
java -version

REM JavaFX模块路径
set JAVAFX_VERSION=21.0.1
set JAVAFX_PATH=%USERPROFILE%\.m2\repository\org\openjfx

echo 📦 检查JavaFX依赖...

REM 检查JavaFX依赖是否存在
set JAVAFX_BASE=%JAVAFX_PATH%\javafx-base\%JAVAFX_VERSION%\javafx-base-%JAVAFX_VERSION%-%JAVAFX_SUFFIX%.jar
if not exist "%JAVAFX_BASE%" (
    echo ⬇️  下载JavaFX依赖...
    mvn dependency:resolve -q
)

echo 🔨 编译项目...
mvn clean compile -q

if %errorlevel% neq 0 (
    echo ❌ 编译失败！
    echo 💡 请确保：
    echo    1. 已安装Java 17或更高版本
    echo    2. Maven已正确配置
    echo    3. 网络连接正常
    pause
    exit /b 1
)

echo 🎮 启动JavaFX应用程序...

REM 构建JavaFX模块路径
set MODULE_PATH=%JAVAFX_PATH%\javafx-base\%JAVAFX_VERSION%\javafx-base-%JAVAFX_VERSION%-%JAVAFX_SUFFIX%.jar;%JAVAFX_PATH%\javafx-controls\%JAVAFX_VERSION%\javafx-controls-%JAVAFX_VERSION%-%JAVAFX_SUFFIX%.jar;%JAVAFX_PATH%\javafx-fxml\%JAVAFX_VERSION%\javafx-fxml-%JAVAFX_VERSION%-%JAVAFX_SUFFIX%.jar;%JAVAFX_PATH%\javafx-graphics\%JAVAFX_VERSION%\javafx-graphics-%JAVAFX_VERSION%-%JAVAFX_SUFFIX%.jar

REM 运行JavaFX应用程序
java --module-path "%MODULE_PATH%" --add-modules javafx.controls,javafx.fxml --add-opens javafx.graphics/com.sun.javafx.application=ALL-UNNAMED --add-opens javafx.graphics/com.sun.javafx.scene=ALL-UNNAMED --add-opens javafx.controls/com.sun.javafx.scene.control.behavior=ALL-UNNAMED --add-opens javafx.controls/com.sun.javafx.scene.control=ALL-UNNAMED --add-exports javafx.graphics/com.sun.glass.ui=ALL-UNNAMED -cp "target/classes;target/dependency/*" Application

echo ✅ 游戏已退出
pause
