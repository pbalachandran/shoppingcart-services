# Shopping Cart Services

Multi module microservices project to provide shopping cart related functionality

## Shopping Cart Service

Sub-project that provides all shopping cart rest endpoints

## Db Utilities

Sub-project that provides DB utilities - wipe, seed, reseed

## Postgres

Underlying DB that backs above projects

## DB Creation

createdb shoppingcart
createuser sc

postgres=# alter role sc superuser;
postgres=# create schema sc;

ALTER USER sc WITH PASSWORD 'password';