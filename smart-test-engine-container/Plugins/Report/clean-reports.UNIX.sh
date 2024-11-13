#!/bin/bash
# Número de días que deseas mantener
echo "Se mantendrán los reportes a partir de 7 dias previos."
# Formato de los archivos de reporte: report_DD_MM_AAAA.*
# Cambia al directorio donde se encuentran los reportes
cd ../../resume-report
pwd
ls
find * -mtime +7 -exec rm -R {} \;
ls
echo "Limpieza de reportes completada."
