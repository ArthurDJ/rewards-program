# rewards-program

## Introduction
The Java Rewards Program is a Java-based backend service designed to calculate reward points for customers in a retail rewards program. This service is accessible via HTTP requests and calculates points based on the transaction values.

## Features
- **HTTP API Access**: Interact with the program via standard HTTP requests.
- **Reward Points Calculation**: Calculates 2 points for every dollar spent over $100, and 1 point for every dollar spent between $50 and $100 in each transaction.
- **Monthly and Total Summary**: Provides a breakdown of points earned per month and the total points over a three-month period for each customer.
- **Transaction Record Processing**: Handles records of transactions over a specified period.

## Installation
1. Ensure you have Java and a suitable web server environment installed on your system.
2. Clone the repository:

```
git clone https://github.com/ArthurDJ/rewards-program.git
```

3. Navigate to the project directory and start the server:

```
cd rewards-program
```
```
java -jar rewards-program.jar
```

## API Usage
The service runs on port `8080` and supports various endpoints for managing transactions and calculating rewards.

### Endpoints

- **POST /transaction**: Add a new transaction.
- Example: `curl -X POST http://localhost:8080/transaction -d '{"customerId": "123", "amount": "150"}'`
- **GET /rewards**: Retrieve reward points for a customer.
- Example: `curl http://localhost:8080/rewards?customerId=123`

### Response Format
Responses are in JSON format. For example, a response from the `/rewards` endpoint might look like:

{
"customerId": "123",
"monthlyRewards": {"January": 65, "February": 30, "March": 50},
"totalRewards": 145
}

## License
This project is licensed under the MIT License - see the LICENSE file for details.
