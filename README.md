# eShop

#Spring boot REST backend
Import the source code in IntelliJ and run the application

before running this spring boot app, run mysql server from xampp
please change the mysql user and password appropriately in the application.properties 
and the database name is given as "db_2". By default, mysql port is 3306.
the service runs in "http://localhost:8080/"
for swagger access this "http://localhost:8080/swagger-ui.html"

REST end points:

GET/inventory/orders/ 
- Get list of all orders made by the customers

POST/inventory/orders/
- Insert the order details made by the customer

GET/inventory/products/
- Get list of products from the inventory

POST/inventory/products/
- Add product to the inventory

PUT/inventory/products/
- Update the existing product if present in the inventory

GET/inventory/products/{id}
- Search for the product using product id

DELETE/inventory/products/{id}
- Removes the particular product from the inventory

GET/inventory/products/orderByPrice/color/{color}
- Get list of products using the color but order by price

GET/inventory/products/price/greater/{price}
- Get list of products from the inventory for which the price is greater than the given price


# For React UI
step 1: unzip "e-shop-ui-master.zip"
step 1: navigate to the folder containing package.json from the command line 
step 2: run "npm install" and wait for installation to complete
step 3: then run "npm start"
and then access "http://localhost:3000/"