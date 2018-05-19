<!DOCTYPE html>
<html>
<head>

</head>
<body>
  
  
My Retail Service API Documentation
>Setup Instructions: 
1.	Install MongoDB in your system - https://docs.mongodb.com/manual/installation/
2.	Run MongoDB - Run 'mongod.exe' in order to start Mongodb
3.	Clone the code from git repository - https://github.com/mahipal62/restapi.git 
4.	Build the project using maven clean install
5.	Get the generated jar from project target folder and run on the Tomcat Instance.
      -	To run on the Tomcat Instance->Open Terminal and go the JAR location and run the following command
      -	java -jar myretailservice-0.0.1-SNAPSHOT.jar
6.	Open browser and visit Swagger. http://localhost:8082/swagger-ui.html
7.	Test the service using swagger or postman

>	Technologies:
  •	Java 8
  •	Maven-3.2
  •	Spring boot-1.5.3
  •	AOP-4.3.8
  •	Swagger-2.7.0
  •	MongoDB

 

> Operation 1: Retrieve the product information by Product ID:

<table >

  <tr>
    <td>Title</td>
    <td>Retrieve product information</td>
  </tr>
  <tr>
    <td>URL</td>
    <td>http://localhost:8082/product/{id}
</td>

  </tr>
  <tr>
    <td>Method</td>
    <td>GET</td>

  </tr>
  <tr>
    <td>URL Parameters</td>
    <td>Required: id=[integer]</td>

  </tr>
  <tr>
    <td>Headers</td>
    <td>Content-Type: application/json
</td>

  </tr>
  <tr>
    <td>Success Response</td>
    <td>Code: 200
Content: {
    "id": 13860428,
    "name": "The Big Lebowski (Blu-ray)",
    "price": {
        "price": 125,
        "currencyCode": "USD"
    }}</td>
  
  </tr>
  <tr>
    <td>Error Response</td>
    <td>Code: 404 Not Found
      Content:
{
    "message": "the product is not available in the Target Rest API 13860427",
    "details": "the product is not available in the Target Rest API 13860427"
}
</td>

  </tr>
  <tr>
    <td>Sample Request</td>
    <td>/product/13860428</td>
  
  </tr>
</table>

>Operation 2: Update the price information in the database

<table >

  <tr>
    <td>Title</td>
    <td>Update Product price</td>
  </tr>
  <tr>
    <td>URL</td>
    <td>http://localhost:8082/product/{id}
</td>

  </tr>
  <tr>
    <td>Method</td>
    <td>PUT</td>

  </tr>
  <tr>
    <td>URL Parameters</td>
    <td>Required: id=[integer]</td>

  </tr>
  <tr>
    <td>Headers</td>
    <td>Content-Type: application/json
</td>

  </tr>
  <tr>
    <td>Body</td>
    <td>{"id": {id}, "price": { "price": {price}, "currencyCode": {currencyCode} } }
Required: 
id=[integer]
price=[BigDecimal]
currencyCode=[String]</td>

  </tr>
  <tr>
    <td>Success Response</td>
    <td>
Code: 201 Created
Content:  {
    "id": 13860428,
    "name": "The Big Lebowski (Blu-ray)",
    "price": {
        "price": 128,
        "currencyCode": "EURO"
    }
} 
</td>
  
  </tr>
  <tr>
    <td>Error Response</td>
    <td>Code: 400 Bad Request
Content:
{
    "message": " Product price cannot be updated, request body json should have matching id with path variable.",
    "details": " Product price cannot be updated, request body json should have matching id with path variable."
}

</td>

  </tr>
  <tr>
    <td>Sample Request</td>
    <td>/product/13860428

body={ { "id": 13860428, "price": { "price": 128.00, "currencyCode": "EURO" } } }</td>
  
  </tr>
</table>

>Response Codes 

•	200 OK - the request was successful.

•	201 Created - the request was successful and a resource was created.

•	400 Bad Request - the request could not be understood or was missing required parameters.

•	401 Unauthorized - authentication failed or user doesn't have permissions for requested operation.

•	403 Forbidden - access denied.

•	404 Not Found - resource was not found.

•	405 Method Not Allowed - requested method is not supported for resource.

•	500 Internal server Error

</body>
</html>

