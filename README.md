# SoftwareDesign

[![Build Status](https://travis-ci.org/vladimirrim/SoftwareDesign.svg?branch=hw_1)](https://travis-ci.org/vladimirrim/SoftwareDesign)

# Архитектура

![architecture](https://user-images.githubusercontent.com/22059171/52305624-a15c4700-29a6-11e9-9c4a-84a9cd10c8bf.png)

Основное приложение в бесконечном цикле считывает команды пользователя, вначале строка парсится с помощью CliParser, который в свою очередь вызывает парсер кавычек, а затем парсер команд. Полученный список команд передаётся на вход интерпретатору, который выполняет
их, создавая нужную команду с помощью CommandFactory. Полученный результат выводится в основном приложении. Все переменные окружения хранятся в Environment, который так же создаётся в Main App.
