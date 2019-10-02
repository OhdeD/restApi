call runcrud.bat
if "%ERRORLEVEL%" == "0" goto runchrome
echo.
echo Something went wrong.

:runchrome
start chrome http://localhost:8080/crud/v1/task/getTasks

:end
echo.
echo Program's done.