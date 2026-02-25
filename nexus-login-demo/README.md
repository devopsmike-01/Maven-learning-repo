# nexus-login-demo

A minimal Spring Boot app that serves a small frontend (Thymeleaf) with form login (Spring Security).

## Run locally

```bash
mvn spring-boot:run
```

Open:
- http://localhost:8080/public (no login)
- http://localhost:8080/ (requires login)

Demo credentials: `user / password`

## Unit tests

```bash
mvn test
```

## Static code analysis with SonarQube

```bash
mvn -DskipTests sonar:sonar \
  -Dsonar.host.url=https://YOUR_SONAR_HOST \
  -Dsonar.token=YOUR_SONAR_TOKEN
```

## "Dry build with Nexus" (resolve dependencies via Nexus mirror)

This uses `.mvn/settings.xml` which mirrors all Maven downloads through Nexus.

```bash
mvn -s .mvn/settings.xml -Pdry-nexus clean verify
```

## Deploy artifacts to Nexus

1) Set environment variables used by `.mvn/settings.xml`:

```bash
export NEXUS_USERNAME=...
export NEXUS_PASSWORD=...
```

2) Update `pom.xml`:
- `distributionManagement` URLs
- `nexusUrl` and `repository` inside the `nexus-deploy` profile

3) Deploy:

```bash
mvn -s .mvn/settings.xml -Pnexus-deploy clean deploy
```

