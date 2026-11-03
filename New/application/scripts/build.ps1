$ErrorActionPreference = 'Stop'
Set-Location (Join-Path $PSScriptRoot '..')
if (-not (Get-Command mvn -ErrorAction SilentlyContinue)) {
    throw 'Maven is required. Install Maven 3.9+ and run: mvn clean verify'
}
mvn clean verify
