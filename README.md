ОПИСАНИЕ ЗАДАЧИ:

Необходимо реализовать консольное CRUD приложение, которое взаимодействует с БД и позволяет выполнять все CRUD операции над сущностями:
- Writer (id, firstName, lastName, List<Post> posts)
- Post (id, content, created, updated, List<Label> labels)
- Label (id, name)
- PostStatus (enum ACTIVE, UNDER_REVIEW, DELETED)

ТРЕБОВАНИЯ:

- Все CRUD операции для каждой из сущностей
- Придерживаться подхода MVC
- Для сборки проекта использовать Maven
- Для взаимодействия с БД - Hibernate
- Для конфигурирования Hibernate - аннотации
- Инициализация БД должна быть реализована с помощью flyway
- Сервисный слой приложения должен быть покрыт юнит тестами (junit + mockito)
- Результатом выполнения задания должен быть репозиторий на github, с использованием Travis (https://travis-ci.org/) и отображением статуса сборки проекта.

ТЕХНОЛОГИИ:

- Java
- PostgreSQL
- Maven
- Flyway
- Hibernate

ШАБЛОНЫ ПРОЕКТИРОВАНИЯ:

- Singleton
- Factory method
- MVC

ИНСТРУКЦИЯ ПО ЗАПУСКУ:
1) Импортировать проект на локальный репозиторий;
2) Изменить файлы конфигурации (flyway.properties, hibernate.cfg.xml) для подключения к БД;
3) В консоли прописать команду mvn flyway:migrate для инициализации БД;
4) Запустить класс Main.
