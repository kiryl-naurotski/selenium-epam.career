# selenium-epam.career

## Reqirements
**Before running tests from IDE make sure to add required browser drivers to your PATH**
> Note:
> If you run tests using gradle than you can skip it as gradle will take it for you

## Running Tests
> Note:
> By defailt tests will run on google chrome browser

You can run tests from IDE as well as from gradle.  
To run tests on different browser add `-PbrowserName=firefox` as JVM parameter. 
To run test from gradle use the following command:  
`./gradlew clean test` - for mac and linux  
`gradlew clean test` - for windows

## Tests results
All tests results is located in the project directory.  
Tests logs is located in folder **logs**  
Tests screenshots is located in folder **screenshots**

If you running tests from gradle than it will create html report located by **build/reports/tests/test** 


