# lso-grade-parsing-service
A Spring microservice with RESTful API for parsing and translating LSO landing grade.

This microservice can be used to convert a DCS Super Carrier style LSO landing grade message to a JSON, with options to make the message easier to read for new pilots.

It is intended to be called from other services or application.

Almost ready for Docker deployment.

For example,
```http request
GET http://localhost:8080/v1/lsograde/show/15

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sat, 20 Jun 2020 14:20:23 GMT
Keep-Alive: timeout=60
Connection: keep-alive

{
  "id": 15,
  "timestamp": "2020-06-20T19:35:43",
  "rawGrade": "LSO: GRADE:--- : _WX_  _DRX_ SLOX _DRIM_ (LURIM) LOIC EGTL BIW [BC] WIRE# 3",
  "carrierName": "CVN-74",
  "aircraftType": "Tomcat",
  "grade": "---",
  "wire": 3,
  "errorItems": [
    {
      "level": "badly",
      "position": "X",
      "error": "W"
    },
    {
      "level": "badly",
      "position": "X",
      "error": "DR"
    },
    {
      "level": null,
      "position": "X",
      "error": "SLO"
    },
    {
      "level": "badly",
      "position": "IM",
      "error": "DR"
    },
    {
      "level": "slightly",
      "position": "IM",
      "error": "LUR"
    },
    {
      "level": null,
      "position": "IC",
      "error": "LO"
    },
    {
      "level": null,
      "position": "TL",
      "error": "EG"
    },
    {
      "level": null,
      "position": "IW",
      "error": "W"
    },
    {
      "level": "bad comm call",
      "position": "BC",
      "error": null
    }
  ]
}

Response code: 200; Time: 15ms; Content length: 636 bytes
```

Localized Message (zh_CN)
```http request
GET http://localhost:8080/v1/lsograde/show/15/zh_CN

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sat, 20 Jun 2020 14:22:48 GMT
Keep-Alive: timeout=60
Connection: keep-alive

{
  "id": 15,
  "timestamp": "2020-06-20T19:35:43",
  "rawGrade": "LSO: GRADE:--- : _WX_  _DRX_ SLOX _DRIM_ (LURIM) LOIC EGTL BIW [BC] WIRE# 3",
  "carrierName": "CVN-74",
  "aircraftType": "雄猫",
  "grade": "不及格，总体上处于在安全范围",
  "wire": 3,
  "errorItems": [
    {
      "level": "严重",
      "position": "起点",
      "error": "坡度不稳"
    },
    {
      "level": "严重",
      "position": "起点",
      "error": "向右偏移"
    },
    {
      "level": null,
      "position": "起点",
      "error": "过慢"
    },
    {
      "level": "严重",
      "position": "中段",
      "error": "向右偏移"
    },
    {
      "level": "轻微",
      "position": "中段",
      "error": "中线偏右"
    },
    {
      "level": null,
      "position": "接近阶段",
      "error": "过低"
    },
    {
      "level": null,
      "position": "着舰阶段",
      "error": "着舰时缺乏推力"
    },
    {
      "level": null,
      "position": "着舰区",
      "error": "坡度不稳"
    },
    {
      "level": null,
      "position": "目视确认时",
      "error": null
    }
  ]
}

Response code: 200; Time: 12ms; Content length: 643 bytes

```


TODO:

* Docker config files, including docker compose

* Config server

* Eureka server