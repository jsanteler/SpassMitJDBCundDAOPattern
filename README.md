## Videokursreihe von meine Professor zur Verfügung gestellt

In diesem Projekt wird JDBC (Java Database Connectivity) und das DAO Pattern itegriert. Dies ist eine effiziente und stukturierte Art der Interaktion mit
einer Datenbank in Java-Anwendungen. JDBC bietet eine einheitliche Schnittstelle, die es Java-basierten Anwendungen ermöglicht, Aktionen wie das Abfragen, 
Aktualisieren und Bearbeiten von Daten in relationalen Datenbanken durchzuführen. Es fungiert als Vermittler zwischen der Anwendung und der Datenbank und 
nutzt SQL-Anweisungen für die Kommunikation. Dies ermöglicht eine direkte Interaktion mit der Datenbank innerhalb des Java-Codes.

Das DAO Pattern ergänzt JDBC, indem es eine Abstraktionsschicht zwischen der Anwendung und der Datenbank einführt. 
Das Ziel des DAO Patterns ist es, die Datenzugriffslogik von der Geschäftslogik der Anwendung zu trennen. 
Durch diese Trennung wird der Code wartbarer, testbarer und modularer.

In einem solchen Projekt werden Klassen und Interfaces definiert, die CRUD-Operationen (Create, Read, Update, Delete) auf Entitätsebene durchführen.
Diese Operationen werden durch die DAO-Implementierungen ausgeführt, die JDBC verwenden, um die Verbindung zur Datenbank herzustellen 
und die erforderlichen SQL-Operationen auszuführen.
