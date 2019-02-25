# SoftwareDesign

[![Build Status](https://travis-ci.org/vladimirrim/SoftwareDesign.svg?branch=hw_1)](https://travis-ci.org/vladimirrim/SoftwareDesign)

# Архитектура

![architecture](https://user-images.githubusercontent.com/22059171/53352048-2b0e8d00-3933-11e9-936e-2ffe43081b83.png)

Основное приложение в бесконечном цикле считывает команды пользователя, вначале строка парсится с помощью CliParser, который в свою очередь вызывает парсер кавычек, а затем парсер команд. Полученный список команд передаётся на вход интерпретатору, который выполняет
их, создавая нужную команду с помощью CommandFactory. Полученный результат выводится в основном приложении. Все переменные окружения хранятся в Environment, который так же создаётся в Main App.
