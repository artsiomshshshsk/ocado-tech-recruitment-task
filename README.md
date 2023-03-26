# In Store Fulfillment (ISF)


## Run Locally

```bash
  java -jar ocado-tech-1.0-SNAPSHOT.jar <store_filename> <orders_filename> [task]
```
 task is optional and can be one of the following:
- `FIRST` - (set by default), task 1 (maximal number of orders)
- `SECOND` - task 2 (maximal orders value)

also you can build jar on your own using maven wrapper:

### Linux / Mac
```bash
  ./mvnw clean package
```

### Windows
```bash
  mvnw.cmd clean package
```

## Dependencies used

- `Jackson` - working with JSON files
- `Lombok` - avoid boilerplate code(getters, setters, constructors, etc.)
- `JUnit` - unit testing
- `Mockito` - mocking objects for unit testing