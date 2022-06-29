# geek-eshop

<details open=""><summary><h2>Описание</h2></summary>
  <div>
    geek-eshop &ndash; проект интернет магазина.
  </div>
  <p></p>
  <div align="center">
    <table border="0">
      <tr>
        <td width="50%"></td>
        <td width="50%"></td>
      </tr>
    </table>
  </div>
</details>
<details><summary><h2>Стэк технологий</h2></summary>
   <ul>
      <li>JDK &ndash; v.11.0.12;</li>
      <li>Maven &ndash; v.3.8.1;</li>
      <li>Spring Boot &ndash; v.2.5.5</li>
      <li>Spring Data JPA</li>
      <li>Spring Web</li>
      <li>Spring REST</li>
      <li>Spring Сloud Config</li>
      <li>Spring Сloud Eureka</li>
      <li>Spring Сloud Gateway</li>
      <li>RabbitMQ</li>
      <li>Redis</li>
      <li>Hibernate</li>
      <li>Liquibase</li>
      <li>MySQL</li>
      <li>NGINX</li>
      <li>Node.js  &ndash; v.16.13.2 (включая npm &ndash; v.8.6.0);</li>
      <li>Angular CLI &ndash; v.13.2.6;</li>
      <li>Docker &ndash; v.20.10.12;</li>
      <li>Docker-compose &ndash; v.20.10.12;</li>
      <li>Thymeleaf</li>
   </ul>
</details>
<details><summary><h2>Функциональность</h2></summary>
В приложении админ:
<ul>
<li>
создание, редактирование, удаление:
  <ul>
    <li>категорий продуктов</li>
    <li>брэндов продуктов</li>
    <li>продуктов</li>
    <li>пользователей</li>
  </ul>
</li>
<li>пагинация &ndash; отображаются по 10 товаров;</li>
<li>
  сортировка
  <ul>
    <li>продуктов - по id, имени, цене;</li>
    <li>пользователей - по id, имени, возрасту;</li>
  </ul>
</li>
<li>
  фильтрация
  <ul>
    <li>продуктов - по категориям, имени, минимальной и максимальной цене, брэндам;</li>
    <li>пользователей - по имени, минимальному и максимальному возрасту;</li>
  </ul>
</li>
</ul>
В приложении backend:
<ul>
<li>фильтрация продуктов - по категориям, имени, минимальной и максимальной цене, брэндам;</li>
<li>добавление продуктов в корзину;</li>
<li>регистрация пользователей;</li>
<li>для зарегистрированных пользователей:
<ul>
<li>создание заказа из добавленных в корзину товаров;</li>
<li>просмотр заказов с онлайн уведомлением об изменении статуса заказа;</li>
</ul>
</li>
</ul>
</details>
<details><summary><h2>Сборка и запуск приложения</h2></summary>
<div>
Для локального запуска приложения необходимо иметь следующие установленные приложения:
</div>
<ul>
<li><a href="https://docs.oracle.com/en/java/javase/11/install/index.html">JDK</a> &ndash; v.11.0.12;</li>
<li><a href="https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html">Maven</a> &ndash; v.3.8.1;</li>
<li><a href="https://nodejs.org/ru/">Node.js</a> &ndash; v.16.13.2 (включая npm &ndash; v.8.6.0);</li>
<li><a href="https://angular.io/cli">Angular CLI</a> &ndash; v.13.2.6;</li>
<li><a href="https://docs.docker.com/engine/install">Docker</a> &ndash; v.20.10.12;</li>
<li><a href="https://docs.docker.com/engine/install">Docker-compose</a> &ndash; v.20.10.12;</li>
</ul>
<p>В проекте использованы дополнительные образы из docker hub
(при запуске через docker-compose скачивать или устанавливать не надо):
</p>
<ul>
<li><a href="https://hub.docker.com/_/mysql">mysql</a> &ndash; в качестве базы данных;</li>
<li><a href="https://hub.docker.com/r/bitnami/rabbitmq">bitnami:rabbitmq</a> &ndash; в качестве Message Broker;</li>
<li><a href="https://hub.docker.com/r/bitnami/redis">redis</a> &ndash; для кеширования результатов запросов пользователей в telegram
  bot;</li>
<li><a href="https://hub.docker.com/_/nginx">nginx</a>  &ndash; для раздачи статического контента.</li>
</ul>
&nbsp;&nbsp;&nbsp;&nbsp;После установки вышеуказанных программ необходимо:
  <ul>
    <li><a href="#git_clone">Склонировать репозиторий на локальный компьютер</a></li>
    <li><a href="#front-image">Построить фронтэнд</a></li>
    <li><a href="#mvn-build">Запустить сборку проекта через Maven</a></li>
    <li><a href="#run-app">Запустить приложение</a></li>
  </ul>

<a name="git_clone"><h3>Склонировать репозиторий на локальный компьютер:</h3></a>
```
git clone https://github.com/Geek-Team-Development/market-analyzer
```
<a name="front-image"><h3>Построить фронтэнд:</h3></a>
```
cd shop-frontend-app
npm i
ng build
```
<a name="mvn-build"><h3>Запустить сборку проекта через Maven</h3></a>
```
cd ..
mvn clean install
```
<a name="run-app"><h3>Запустить приложение</h3></a>
```
docker-compose up -d
```
</details>
<details><summary><h2>Структура проекта</h2></summary>
<table>
<tr>
<th>Директория</th>
<th>Описание</th>
</tr>
<tr>
<td>log4jdbc-boot</td>
<td>Модуль логирования запросов в базу данных</td>
</tr>
<tr>
<td>picture-service</td>
<td>Библиотека контроллера и сервиса взаимодействия с картинками</td>
</tr>
<tr>
<td>picture-service-app</td>
<td>Сервис взаимодействия с картинками</td>
</tr>
<tr>
<td>shop-admin-app</td>
<td>Сервис для администрирования</td>
</tr>
<tr>
<td>shop-backend-api-app</td>
<td>Сервис взаимодействия пользователей с магазином</td>
</tr>
<tr>
<td>shop-database</td>
<td>Модуль, содержащий структуру базы даных, а также файлы миграции для Liquibase</td>
</tr>
<tr>
<td>shop-delivery-service</td>
<td>Сервис обработки заказов (имитация)</td>
</tr>
<tr>
<td>shop-frontend-app</td>
<td>Фронтэнд - модуль, содержащий логику для взаимодействия с сервисом из модуля shop-backend-api-app</td>
</tr>
<tr>
<td>spring-cloud-config</td>
<td>Сервис раздачи конфигурации</td>
</tr>
<tr>
<td>spring-cloud-gateway</td>
<td>Балансировщик нагрузки запросов на shop-backend-api-app и picture-service-app</td>
</tr>
<tr>
<td>spring-eureka</td>
<td>Сервер регистрации запущенных сервисов</td>
</tr>
</table>
</details>
