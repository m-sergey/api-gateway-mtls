# Демо версия работы с JWKS

## Запуск nginx c JWKS

cd docker\nginx\
docker-compose up

## Запуск микросервиса

gradlew bootRun

## Проверка авторизации 

http://localhost:8080/untrust - не требует токена

http://localhost:8080/trust - требует токен

Тестовый токен

eyJraWQiOiI4ZTM1YmViNy1hOGZlLTQ1ZDgtODhjOC1iNzk4MjMzNzhiNT
ciLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJVcmFsc2l
iIiwicm9sZXMiOlsiYWRtaW4iLCJ1c2VyIl0sImlzcyI6InNhbXBsZSIsIm
V4cCI6MzI1MTQwNzQxNjB9.Q9zZJ_Zxjy3FVNUl_mogs0AraZHHD94EimACN
UFAeKLxGa9rVJVCniUogsoJ4ApVbLSGgRI85B8eXvS4_BV9v8rQ4mwVBJTOknC
-Xn_3T-snGZr7QrnmzIHMYfj3j_MOR7P4W6oyMIWvravtQY9DaWesf5RpQ0Caz
Wpr9Uf45ga3lbDWLg3mDizoqzj_7EY-ZVq_eLMzr_lOBRVIFllSC8Ev_H_NOiq
jMzivjuoafPMmcpcn2yFbqYBlHLn00MqWa61r4e23GLxX2AY1I5BtqmHjITy79h
KN8zGtJK-qOhGX3eV4u458Gh-RQvJNS949o_OXzfd_xLZcmiDIfuPXbg
