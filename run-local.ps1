param(
    [string]$DbUser = "root",
    [string]$DbPassword = ""
)

if ([string]::IsNullOrWhiteSpace($DbPassword)) {
    $DbPassword = Read-Host "Contraseña de MySQL (déjala vacía si tu usuario no tiene contraseña)"
}

$env:DB_USER = $DbUser
$env:DB_PASSWORD = $DbPassword

& "$PSScriptRoot\mvnw.cmd" spring-boot:run
