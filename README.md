# electric-car-charge-info

- 전기차 충전소 조회 어플

## 요구사항

- 내 현재 위치 지역의 전기차 충전소 조회 지도를 이동시 해당 위치의 충전소 조회
- 충전기 타입별로 필터링기능 (급속, 완속, Combo, Demo, AC)
- 원하는 위치 검색

## 아키텍쳐

### Clean Architecture

![clean-architecture](https://user-images.githubusercontent.com/57650484/206324237-e5771a1d-1d0e-4114-a6fa-83bbc8770f8a.png)

### MVVM Archtecture

![MVVM](https://user-images.githubusercontent.com/57650484/206324400-19102333-84be-4486-8d89-e524bb6fc6dd.png)

## 기술스택

### Presenter

- Android JetPack - android framework

### Data

- Kotlin Serialization - json Parsing
- Retrofit - network
- 한국전력공사 전기차 충전소 운영정보
- [https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15100485]

### DI

- Hilt

### ETC 

- Coroutine - asynchronous
- Flow

### Domain

- UseCase
- Model
- Repository Interface

### Data

- Retrofit Service
- Repository Implement
- DTO
- DAO
