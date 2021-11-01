1. Go to project folder using below cmd
cd test
2. Build the project using below cmd
./gradlew clean build
3. Run the jar file which got build and stored in .\build\libs\test-0.0.1-SNAPSHOT.jar using below cmd
java -jar .\build\libs\test-0.0.1-SNAPSHOT.jar
4. Build a Docker images using the below cmd.
docker build --tag=test:latest .
5. Run the docker images in a container using the below cmd.
docker run --name ElectricVehicle-shalini -p8080:8080 test:latest