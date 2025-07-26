cd frontend
rmdir /s /q build
call npm run build

cd ..
call mvn clean install
call mvn spring-boot:run