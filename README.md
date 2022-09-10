# Cities basic application

## Table of Contents

- [Introduction](#Introduction)
- [How to build](#how-to-build)
- [How to run locally](#how-to-run-locally)
- [API](#api)

## Introduction

### This application was developed to perform simple operations on given cities

## How to build

```shell script
$ ./gradlew build
```

## How to run locally

```shell script
$ java -jar city-list.jar
```

## API

<details>
<summary>ENDPOINTS</summary>

- ### Get cities

<details>
    <summary>/cities - HTTP GET:</summary>

Request param:

"size": 10   (record size in the page)

"page": 2    (record page)

"sort": "id" (Element sorting by type)

Status Code: 200 // Success case

Response data example:

```json
  [
  {
    "id": 1,
    "name": "Tokyo",
    "photo": "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Skyscrapers_of_Shinjuku_2009_January.jpg/500px-Skyscrapers_of_Shinjuku_2009_January.jpg"
  },
  {
    "id": 2,
    "name": "Jakarta",
    "photo": "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f6/Jakarta_Pictures-1.jpg/327px-Jakarta_Pictures-1.jpg"
  },
  {
    "id": 3,
    "name": "Delhi",
    "photo": "https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/IN-DL.svg/439px-IN-DL.svg.png"
  }
]
```

Status Code: 500 // Failed cases: Client Exception

```json
{
  "code": "unexpected error"
}
```

</details>

- ### Get city by name

<details>
    <summary>/cities/name/{name} - HTTP GET:</summary>

Path variable:

"name": "Tokyo"

Status Code: 200 // Success case

Response data example:

```json
  {
  "id": 1,
  "name": "Tokyo",
  "photo": "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Skyscrapers_of_Shinjuku_2009_January.jpg/500px-Skyscrapers_of_Shinjuku_2009_January.jpg"
}
```

Status Code: 404 // Failed cases: Not found exception

```json
{
  "code": "NAME_NOT_FOUNDED"
}
```

Status Code: 500 // Failed cases: Client Exception

```json
{
  "code": "unexpected error"
}
```

</details>

- ### Update city

<details>
    <summary>/cities - HTTP PUT:</summary>

Request body:

```json
  {
  "id": 1,
  "name": "Updated Tokyo",
  "photo": "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Skyscrapers_of_Shinjuku_2009_January.jpg/500px-Skyscrapers_of_Shinjuku_2009_January.jpg"
}
```

Status Code: 200 // Success case

Status Code: 404 // Failed cases: Not found exception

```json
{
  "code": "CITY_ID_NOT_FOUNDED"
}
```

Status Code: 500 // Failed cases: Client Exception

```json
{
  "code": "unexpected error"
}
```

</details>
</details>
