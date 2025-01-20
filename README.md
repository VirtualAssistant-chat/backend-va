# Virtual Assistant
## Initializing Docker Compose, PostgreSQL, Flyway, MongoDB and Gradle

This project uses Docker Compose to initialize PostgreSQL services, pgAdmin4 (a web user interface for PostgreSQL), Flyway (for database migrations) and MongoDB. Gradle is used as the build system.

## 1.  Prerequisites
- Have  [Docker](https://docs.docker.com/desktop/install/ubuntu/) and [Docker Compose](https://docs.docker.com/get-started/08_using_compose/) installed.
- Have [Gradle](https://gradle.org/install/) installed.
- [Java 11](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html) installed.

- **Environment variables**.
  Make sure you have the following files with the necessary environment variables:
    - .env-postgres
    - .env-postgres-admin
    - .env-flyway
    - .env-mongo
## 2.  Build and Run the program
- **Initialize services with Docker Compose**.
  To initialize all services defined in the `docker-compose.yml` file, run:
   ````bash
   docker-compose up -d

- **Compiling and running with Gradle**.
  To compile and run the project, use:
  `./gradlew bootRun`.

- **Virtual assistant**
  To run the project execute the main class VirtualAssistantApplication on the project

- **Access to the tools**

    - **pgAdmin**: Access [http://localhost:9000](http://localhost:9000/) to use pgAdmin.
    - **Application**: Once your Spring Boot application is up and running it can be accessed using [http://localhost:8080](http://localhost:8080/)

### 3. Common problems and solutions
- **Port conflict**: If you encounter an error indicating that a port is already in use check that you don't have another service running on that port.
- **Missing dependencies in Gradle**: If you encounter errors related to missing dependencies, check your `build.gradle` file and verify the dependencies.
  Whisper is a general-purpose speech recognition model. It is trained on a large dataset of diverse audio and is also a multitasking model that can perform multilingual speech recognition, speech translation, and language identification.

## ASR
![image](https://raw.githubusercontent.com/openai/whisper/main/approach.png)
### Requirements
- Python 3.0+
- Whisper
- ffmpeg
- fastapi
- python-multipart


### Required installations for ASRClient
The correct implementation of ASRClient from this repository requires the prior installation of some essential tools for its smooth operation.

The following are the commands needed to perform the installation. You can use any of the following: pip, pip3 or apt.

If you choose to use the apt package manager, run the following commands in your terminal:

```bash
sudo apt install python3-pip
```
or 
```bash
sudo apt install python-pip
```
Now we will install whisper

```bash
pip3 install -U openai-whisper
```
or
```bash
pip install -U openai-whisper
```
Now we will install ffmpeg which is a fundamental library for whisper to work. 

```bash
sudo apt update && sudo apt install ffmpeg
```
Now we will install fastapi which will help us to set up the whisper service with python.

```bash
pip3 install fastapi
```
or
```bash
pip install fastapi
```
Now we will install uvicorn which will run the python ASR server. 

```bash
sudo apt install uvicorn
```
Now we will install python-multipart which is a support library for uvicorn.
```bash
pip3 install python-multipart
```
or
```bash
pip install python-multipart
```
### Run ASR Whisper Client
```bash
cd src/main/java/org/fundacionjala/virtualassistant/whisper/service
```
```bash
uvicorn ASRClient:app --reload
```
These steps will ensure that all necessary tools are available and ready to facilitate the successful installation of ASRClient.

Note: If you want to run the gradle build command and have it compile without errors you will have to run the server with ASRClient so that it can run and pass its tests.

## References
- whisper: https://github.com/openai/whisper
- ffmpeg: https://pypi.org/project/ffmpeg-python/
- fastapi: https://fastapi.tiangolo.com/
- python-multipart: https://pypi.org/project/python-multipart/