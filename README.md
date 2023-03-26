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


### Run .jar with self-test-data from project root folder

complete-by
```bash
  java -jar target/ocado-tech-1.0-SNAPSHOT.jar src/main/resources/self-test-data/complete-by/store.json src/main/resources/self-test-data/complete-by/orders.json
```

isf-end-time
```bash
  java -jar target/ocado-tech-1.0-SNAPSHOT.jar src/main/resources/self-test-data/isf-end-time/store.json src/main/resources/self-test-data/isf-end-time/orders.json
```

optimize-order-count
```bash
  java -jar target/ocado-tech-1.0-SNAPSHOT.jar src/main/resources/self-test-data/optimize-order-count/store.json src/main/resources/self-test-data/optimize-order-count/orders.json
```

logic-bomb
```bash
  java -jar target/ocado-tech-1.0-SNAPSHOT.jar src/main/resources/self-test-data/logic-bomb/store.json src/main/resources/self-test-data/logic-bomb/orders.json
```

any-order-length-is-ok
```bash
  java -jar target/ocado-tech-1.0-SNAPSHOT.jar src/main/resources/self-test-data/any-order-length-is-ok/store.json src/main/resources/self-test-data/any-order-length-is-ok/orders.json
```

advanced-optimize-order-count
```bash
  java -jar target/ocado-tech-1.0-SNAPSHOT.jar src/main/resources/self-test-data/advanced-optimize-order-count/store.json src/main/resources/self-test-data/advanced-optimize-order-count/orders.json
```

advanced-allocation
```bash
  java -jar target/ocado-tech-1.0-SNAPSHOT.jar src/main/resources/self-test-data/advanced-allocation/store.json src/main/resources/self-test-data/advanced-allocation/orders.json
```


optimize-order-value
```bash
  java -jar target/ocado-tech-1.0-SNAPSHOT.jar src/main/resources/self-test-data/advanced-allocation/store.json src/main/resources/self-test-data/advanced-allocation/orders.json SECOND
```

advanced-optimize-order-value
```bash
  java -jar target/ocado-tech-1.0-SNAPSHOT.jar src/main/resources/self-test-data/advanced-optimize-order-value/store.json src/main/resources/self-test-data/advanced-optimize-order-value/orders.json SECOND
```