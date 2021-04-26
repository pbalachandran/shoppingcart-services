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
* Item creation, addition/removal/deletion to/from cart
* Insufficient product inventory
* TODO
    * User management and association with a cart
    * Tie user with cart operations (create, add/remove item, find)

## Db Utilities
* Sub-project that provides DB utilities - wipe, seed, reseed
* Gradle

## Postgres DB
Underlying DB that backs above projects

### DB/Schema/User Details
* DB: <i>shoppingcart</i>
* Schema: <i>shoppingcart</i>
* User: <i>shoppingcart-user</i>

### DB Commands
* command-line# createdb shoppingcart
* command-line# createuser shoppingcart_user
* postgres=# alter role shoppingcart_user superuser;
* postgres=# create schema shoppingcart;
* postgres=# ALTER USER shoppingcart_user WITH PASSWORD 'password';