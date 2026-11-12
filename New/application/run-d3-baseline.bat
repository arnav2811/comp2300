@echo off
setlocal
cd /d "%~dp0"

echo ============================================================
echo Starting Footprint Lens (D3 Architectural Baseline Mode)...
echo ============================================================

set FX_PATH=%USERPROFILE%\.m2\repository\org\openjfx
set FX_JARS=%FX_PATH%\javafx-base\23.0.1\javafx-base-23.0.1-win.jar;%FX_PATH%\javafx-controls\23.0.1\javafx-controls-23.0.1-win.jar;%FX_PATH%\javafx-graphics\23.0.1\javafx-graphics-23.0.1-win.jar;%FX_PATH%\javafx-fxml\23.0.1\javafx-fxml-23.0.1-win.jar

if not exist "%FX_PATH%\javafx-base\23.0.1\javafx-base-23.0.1-win.jar" (
    echo [ERROR] JavaFX libraries not found at: %FX_PATH%
    echo Please make sure OpenJFX jars exist in ~/.m2/repository/org/openjfx
    pause
    exit /b 1
)

set CP=build\classes
java --module-path "%FX_JARS%" --add-modules javafx.controls,javafx.fxml -cp "%CP%" in.carbon.ui.FootprintLensApp --d3 %*

if %ERRORLEVEL% neq 0 (
    echo [ERROR] Application exited with error code %ERRORLEVEL%.
    pause
)

