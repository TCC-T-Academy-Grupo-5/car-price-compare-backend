# Etapa de build
FROM maven:3.9.4-eclipse-temurin-21 AS build

# Definindo a codificação UTF-8 para o ambiente do Docker
ENV LANG=C.UTF-8
ENV LC_ALL=C.UTF-8

# Diretório de trabalho no container
WORKDIR /app

# Copiando o arquivo pom.xml e fazendo o download das dependências offline
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiando o código-fonte para dentro do container
COPY src ./src

# Limpeza manual da pasta target antes de rodar o Maven clean
RUN rm -rf /app/target && mvn clean package -DskipTests

# Etapa de runtime
FROM eclipse-temurin:21-jre

# Diretório de trabalho no container
WORKDIR /app

# Copiando o JAR gerado da etapa de build
COPY --from=build /app/target/*.jar app.jar

# Comando de inicialização do aplicativo
ENTRYPOINT ["java", "-jar", "app.jar"]
