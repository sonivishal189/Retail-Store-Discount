# Retail-Store-Discount

## Description
The Retail Store Discount project is a Java Spring Boot application designed to manage and apply discounts in a retail store setting. This system ensures that customers receive appropriate discounts based on their status (Employee, Affiliate, or Regular customer) and purchase details.

## Features
- **Discount Management**: Manage discount rules based on customer types and purchase details.
- **API Endpoints**: RESTful API endpoints for managing customers, items and bills.
- **Spring Boot Integration**: Built with Spring Boot for easy setup and deployment.

## Discount Calculation Conditions
1. **Employee Discount**: Customers who are employees receive a 30% discount.
2. **Affiliate Discount**: Customers who are affiliates receive a 10% discount.
3. **Regular Customer Discount**: Customers who have been regular customers for more than 2 years receive a 5% discount.
4. **Bill-Based Discount**: For every $100 on the bill, there is a $5 discount (e.g., for a $990 bill, you get a $45 discount).
5. **Groceries Exclusion**: Percentage-based discounts do not apply to groceries for any type of customer.
6. **Exclusive Discount Types**: A customer can either be an Employee, Affiliate, or Regular customer, and is eligible for only one of the percentage-based discounts.

## Technology Stack
- Java 11
- Spring Boot 2.7.9
- Maven 3.9.6
- Swagger UI for API documentation (http://localhost:8080/swagger-ui/index.html)

## Installation
1. Clone the repository:
    ```sh
    git clone https://github.com/sonivishal189/Retail-Store-Discount.git
    ```
2. Navigate to the project directory:
    ```sh
    cd Retail-Store-Discount
    ```
3. Build the project using Maven:
    ```sh
    mvn clean install
    ```
4. Run the application:
    ```sh
    mvn spring-boot:run
    ```
5. Check code coverage:
    ```sh
    mvn clean test
    mvn verify
   # After the tests complete, view the generated code coverage report at target/site/jacoco/index.html.
    ```
6. Play with APIs:
- Once application is running, open your browser and navigate to http://localhost:8080/swagger-ui/index.html to access the Swagger UI for API documentation and usage. 
- Swagger UI can be used to perform CRUD operations on customers, items and bills.

## Usage
- Use GET /customer/all and GET /item/all endpoints to fetch all the pre added customers and items respectively.
- Existing customer and item details can be used for creating bills.
- User can create customer and item using the endpoints as well.

