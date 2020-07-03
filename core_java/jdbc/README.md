# JDBC 
## Introduction
JBCD app is a java application that allows users to connect to PosatgreSQL database and create, update, read and delete data from its tables.
## ER Diagram 
## Design patterns
JDBC uses a two design patterns, DAO(data access object) design and repository design pattern. The difference between the two is that DAO is an abstraction of the data persistence and repository is an abstraction of a collection of objects. Repository could be implemented with DAO but DAO can not be implemented with repository. In DAO design pattern, DAO classes can access multiple databases but in Repository design pattern, each DAO class can only access one table. In the implementation of `orderDAO` accesses all 5 tables, thus using DAO design pattern. In the implementation of `customerDAO` only one table is accessed, this is an example of an repository design pattern.