# Comprobamos los procesos en ejecución
$chromedriver_processes = Get-Process | Where-Object {$_.ProcessName -eq "chromedriver.exe"}

# En caso de que encontremos alguno, lo eliminamos
if($chromedriver_processes -ne $null){
    foreach ($process in $chromedriver_processes) {
        Stop-Process -Id $process.Id -Force
        Remove-Item $process.Path
    }
}