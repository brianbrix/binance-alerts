# Binance Price Alert System - Real-time Cryptocurrency Price Monitoring

A Spring Boot application that monitors cryptocurrency trading pairs on Binance and alerts users when prices reach daily highs. The system provides both a web interface and REST API for managing price alerts and tracking multiple cryptocurrency pairs simultaneously.

This application helps cryptocurrency traders and investors stay informed about market movements by automatically monitoring selected trading pairs on Binance. It features real-time price updates every minute, a user-friendly dashboard for managing tracked pairs, and an API for programmatic integration. The system stores historical price data and generates alerts when cryptocurrencies reach their 24-hour high prices.

## Repository Structure
```
.
├── src/
│   ├── main/java/com/me/binancealerts/
│   │   ├── BinanceAlertsApplication.java      # Application entry point with scheduling enabled
│   │   ├── PriceCheckScheduler.java          # Handles periodic price checks every 60 seconds
│   │   ├── controllers/                       # Web and API endpoints
│   │   ├── entities/                          # Data models for cryptocurrency pairs
│   │   ├── repos/                             # Database access layer
│   │   └── services/                          # Business logic for price monitoring
│   └── main/resources/
│       ├── application.properties             # Application configuration
│       ├── static/                            # Frontend assets
│       └── templates/                         # Thymeleaf HTML templates
├── pom.xml                                    # Maven project configuration
└── mvnw, mvnw.cmd                            # Maven wrapper scripts
```

## Usage Instructions
### Prerequisites
- Java 17 or higher
- Maven (optional, wrapper included)
- Internet connection for accessing Binance API
- Web browser for accessing the dashboard

### Installation
1. Clone the repository:
```bash
git clone <repository-url>
cd binance-alerts
```

2. Build the application:
```bash
./mvnw clean install
```

3. Run the application:
```bash
./mvnw spring-boot:run
```

### Quick Start
1. Access the dashboard at `http://localhost:8080`
2. Add a trading pair:
   - Enter the first coin symbol (e.g., "BTC")
   - Enter the second coin symbol (e.g., "USDT")
   - Click "Add Pair"
3. View tracked pairs and their current prices in the dashboard
4. Monitor the application logs for price alerts

### More Detailed Examples
#### Adding Trading Pairs via API
```bash
curl -X POST "http://localhost:8080/api/coins/add/BTC/USDT"
curl -X POST "http://localhost:8080/api/coins/add/ETH/USDT"
```

#### Filtering Trading Pairs
```bash
# Get all pairs containing BTC
curl "http://localhost:8080/api/coins/filter/BTC"

# Get all pairs containing USDT
curl "http://localhost:8080/api/coins/filter/USDT"
```

#### Removing Trading Pairs
```bash
curl -X DELETE "http://localhost:8080/api/coins/remove/BTC/USDT"
```

### Troubleshooting
#### Common Issues
1. Connection to Binance API fails
   - Error: "Error fetching data for {symbol}"
   - Check your internet connection
   - Verify the trading pair exists on Binance
   - Enable debug logging in application.properties:
     ```properties
     logging.level.com.me.binancealerts=DEBUG
     ```

2. Database access issues
   - Error: "Table 'COIN_PAIR' not found"
   - Solution: Verify H2 database configuration in application.properties
   - Check H2 console at `http://localhost:8080/h2-console`
   - Default credentials are in application.properties

3. Performance optimization
   - Monitor response times in actuator metrics
   - Access metrics at `http://localhost:8080/actuator/metrics`
   - Adjust price check interval in PriceCheckScheduler.java
   - Default interval is 60 seconds

## Data Flow
The application continuously monitors cryptocurrency prices by polling the Binance API every minute for registered trading pairs. When prices reach daily highs, the system generates alerts.

```ascii
[User/API] --> [Controller Layer]
                     |
                     v
[Binance API] <--> [Service Layer] --> [Repository Layer]
                     |                        |
                     v                        v
              [Price Alerts]            [H2 Database]
```

Key component interactions:
1. Controllers receive user requests to add/remove trading pairs
2. Service layer manages business logic and API communication
3. PriceCheckScheduler triggers price checks every 60 seconds
4. BinanceAlertService fetches current prices from Binance API
5. Repository layer handles data persistence in H2 database
6. Price alerts are logged when new highs are detected
7. Dashboard updates reflect the latest price information