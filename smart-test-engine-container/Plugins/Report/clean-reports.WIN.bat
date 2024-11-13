REM forfiles /P "C:\github-mac\Android-Front-Automated-testing\Test-Automation\resume-report" /M "*" /D -7 /C "cmd /c if @isdir==TRUE echo rd /s /q @path"
forfiles /P "C:\github-mac\Android-Front-Automated-testing\Test-Automation\resume-report" /M "*" /D -7 /C "cmd /c if @isdir==TRUE rd /s /q @path"
REM PAUSE