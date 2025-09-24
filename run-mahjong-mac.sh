#!/bin/bash

# 麻将游戏 - Mac优化运行脚本
# 适用于Apple Silicon和Intel Mac

echo "🀄️  启动麻将游戏 (Mac优化版)"
echo "=================================="

# 检测Mac架构
ARCH=$(uname -m)
if [[ "$ARCH" == "arm64" ]]; then
    echo "🍎 检测到Apple Silicon Mac (ARM64)"
    JAVAFX_SUFFIX="mac-aarch64"
else
    echo "💻 检测到Intel Mac (x86_64)"
    JAVAFX_SUFFIX="mac"
fi

# 设置Java环境
export JAVA_HOME=$(/usr/libexec/java_home -v 17 2>/dev/null || /usr/libexec/java_home -v 11 2>/dev/null || /usr/libexec/java_home)
echo "☕ 使用Java版本: $(java -version 2>&1 | head -1)"

# JavaFX模块路径
JAVAFX_VERSION="21.0.1"
JAVAFX_PATH="$HOME/.m2/repository/org/openjfx"

echo "📦 检查JavaFX依赖..."

# 检查JavaFX依赖是否存在
JAVAFX_BASE="$JAVAFX_PATH/javafx-base/$JAVAFX_VERSION/javafx-base-$JAVAFX_VERSION-$JAVAFX_SUFFIX.jar"
if [ ! -f "$JAVAFX_BASE" ]; then
    echo "⬇️  下载JavaFX依赖..."
    mvn dependency:resolve -q
fi

echo "🔨 编译项目..."
mvn clean compile -q

if [ $? -ne 0 ]; then
    echo "❌ 编译失败！"
    echo "💡 请确保："
    echo "   1. 已安装Java 17或更高版本"
    echo "   2. Maven已正确配置"
    echo "   3. 网络连接正常"
    exit 1
fi

echo "🎮 启动JavaFX应用程序..."

# 构建JavaFX模块路径
MODULE_PATH="$JAVAFX_PATH/javafx-base/$JAVAFX_VERSION/javafx-base-$JAVAFX_VERSION-$JAVAFX_SUFFIX.jar:$JAVAFX_PATH/javafx-controls/$JAVAFX_VERSION/javafx-controls-$JAVAFX_VERSION-$JAVAFX_SUFFIX.jar:$JAVAFX_PATH/javafx-fxml/$JAVAFX_VERSION/javafx-fxml-$JAVAFX_VERSION-$JAVAFX_SUFFIX.jar:$JAVAFX_PATH/javafx-graphics/$JAVAFX_VERSION/javafx-graphics-$JAVAFX_VERSION-$JAVAFX_SUFFIX.jar"

# 运行JavaFX应用程序
java \
    --module-path "$MODULE_PATH" \
    --add-modules javafx.controls,javafx.fxml \
    --add-opens javafx.graphics/com.sun.javafx.application=ALL-UNNAMED \
    --add-opens javafx.graphics/com.sun.javafx.scene=ALL-UNNAMED \
    --add-opens javafx.controls/com.sun.javafx.scene.control.behavior=ALL-UNNAMED \
    --add-opens javafx.controls/com.sun.javafx.scene.control=ALL-UNNAMED \
    --add-exports javafx.graphics/com.sun.glass.ui=ALL-UNNAMED \
    -cp "target/classes:target/dependency/*" \
    Application

echo "✅ 游戏已退出"
