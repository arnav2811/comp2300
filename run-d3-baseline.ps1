# Footprint Lens (D3 Architectural Baseline Mode)
$ErrorActionPreference = 'Stop'
Set-Location $PSScriptRoot

Write-Host "============================================================" -ForegroundColor DarkYellow
Write-Host "Starting Footprint Lens (D3 Architectural Baseline Mode)..." -ForegroundColor DarkYellow
Write-Host "============================================================" -ForegroundColor DarkYellow

$fxPath = "$env:USERPROFILE\.m2\repository\org\openjfx"
$fxJars = "$fxPath\javafx-base\23.0.1\javafx-base-23.0.1-win.jar;$fxPath\javafx-controls\23.0.1\javafx-controls-23.0.1-win.jar;$fxPath\javafx-graphics\23.0.1\javafx-graphics-23.0.1-win.jar;$fxPath\javafx-fxml\23.0.1\javafx-fxml-23.0.1-win.jar"

if (-not (Test-Path "$fxPath\javafx-base\23.0.1\javafx-base-23.0.1-win.jar")) {
    Write-Error "JavaFX libraries not found at: $fxPath`nPlease ensure OpenJFX jars exist in ~/.m2/repository/org/openjfx"
    exit 1
}

$classesDir = Join-Path $PSScriptRoot "New\application\build\classes"
if (-not (Test-Path $classesDir)) { New-Item -ItemType Directory -Path $classesDir -Force | Out-Null }

Copy-Item "New\application\src\main\resources\style.css" "$classesDir\style.css" -Force

$sources = (Get-ChildItem -Path "New\application\src\main\java\in\carbon\core\*.java", "New\application\src\main\java\in\carbon\ui\*.java").FullName
& javac --module-path "$fxJars" --add-modules javafx.controls,javafx.fxml -d "$classesDir" $sources

& java --module-path "$fxJars" --add-modules javafx.controls,javafx.fxml -cp "$classesDir" in.carbon.ui.FootprintLensApp --d3 $args
