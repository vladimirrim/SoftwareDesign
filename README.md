# SoftwareDesign

[![Build Status](https://travis-ci.org/vladimirrim/SoftwareDesign.svg?branch=hw_1)](https://travis-ci.org/vladimirrim/SoftwareDesign)


# Выбор библиотеки для парсинга аргументов

Хотелось либу написанную на котлине, а не на джаве, поэтому рассматривались 3 варианта --

1. https://github.com/xenomachina/kotlin-argparser
2. https://github.com/Kotlin/kotlinx.cli
3. https://github.com/ajalt/clikt/

Третий не подошёл, так как предлагает, чтобы все команды реализовывали его интерфейс, но уже реализованные команды не хотелось переписывать. Был выбран первый вариант, так как его легче установить и он популярнее второго.
