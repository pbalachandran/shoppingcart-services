# Shopping Cart Services



# Postgres

createdb shoppingcart
createuser sc

postgres=# alter role sc superuser;
postgres=# create schema sc;

ALTER USER sc WITH PASSWORD 'password';