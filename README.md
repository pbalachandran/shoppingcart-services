Postgres

createdb shoppingcart
createuser sc

postgres=# alter role sc superuser;
postgres=# create schema sc;