# Techinical stack

SpringBoot : 2.3.1.RELEASE
Junit - Jupiter
Lambok
RestTemplate
ApacheHTTPClient
SLF4J [logging]
IntelliJ [IDE]

# Log info

Logs are being written to country-capital-service.log file, at project base location.

## Steps
```
Two ways to execute the Application
1. Run as Junit test with @SpringBootTest in IntelliJ
    a. Select Gradle task test
    b. Provide -Dname=COL or -Dcodes=col;no in VM options
    c. Run the test task

2. Run as Java application with user input
    a. Run CountryServiceCapitalRetrieverApplication class (included logic in SpringBoot starter/main class)
    b. Provide input as prompted and verify the execution flow 
    NOTE: Spring beans are not being used for this flow, since reading the user input from JUnit test did not work.   
          As a workaround, called this functionality from main method, which needed to create the object 
          with new opeartor and hence lost Spring context.  
```
...
@ Junit configurations
```
    Runtime configurations are configured in build.gradle
    JUnit configuration
    test {
        useJUnitPlatform()
        doFirst {
            systemProperty "name", System.properties['name']
            systemProperty "codes", System.properties['codes']
        }
    }
```