# Shopping Cart Services
* Multi module micro services project to provide shopping cart related functionality
* Gradle

## Shopping Cart Service
* Sub-project that provides all shopping cart rest endpoints
* Gradle

### Entities
* ProductCategory creation
* Product creation
* Cart creation, retrieval & deletion
* Item creation, addition/deletion to/from cart
* TODO
    * User management and association with a cart
    * Tie user with cart operations (create, add/remove item, find)
    * Handle insufficient product inventory, at item creation
    * 

## Db Utilities
* Sub-project that provides DB utilities - wipe, seed, reseed
* Gradle

## Postgres DB
Underlying DB that backs above projects

### DB/Schema/User Details
* DB: <i>shoppingcart</i>
* Schema: <i>sc</i>
* User: <i>sc</i>

### DB Commands
* command-line# createdb shoppingcart
* command-line# createuser sc
* postgres=# alter role sc superuser;
* postgres=# create schema sc;
* postgres=# ALTER USER sc WITH PASSWORD 'password';