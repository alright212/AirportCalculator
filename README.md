## 1. Clone the Repository

First, clone the repository to your local machine. Open your terminal and run:

```bash
git clone https://github.com/alright212/AirportCalculator.git
cd AirportCalculator
```

## 2. Set Up and Run the Backend

Navigate to the airportCalculatorback directory:

```bash
cd airportCalculatorback  # Ensure this is the correct directory name
```

Next, clean the Gradle project to ensure there are no leftover build artifacts:

```bash
gradle clean
```

Run the backend using Gradle:

```bash
./gradlew bootRun
```

This will start the backend server.

## 3. Set Up and Run the Frontend

Open a new terminal window or tab, and navigate to the airport_calc_vue directory:

```bash
cd airport_calculator_front/airport_calc_vue  # Confirm the correct directory path
```

Install the necessary Node.js dependencies:

```bash
npm install
```

Run the frontend development server:

```bash
npm run dev
```

This will start the frontend server, typically accessible at http://localhost:5173.

## Maybe useful information

If you need to use a different port,
adjust the CORS settings in your backend application by modifying the allowedOrigins method in the WebConfig.java file:

```java
            public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
        .allowedOrigins("http://localhost:5173")  // Specify the allowed origin
        .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
        .allowedHeaders("*")
        .allowCredentials(true);
        }
        };
        }
```

This adjustment helps to handle Cross-Origin Resource Sharing (CORS) issues, ensuring the frontend can successfully
communicate with the backend.


Also if the backend is running on a different port, adjust the frontend's axios configuration in the "
airport_calculator_front/airport_calc_vue/src/services/api.ts" file:

```javascript
const API_URL = 'http://localhost:8080/api/airports';
```


https://github.com/alright212/AirportCalculator/assets/99361502/ee7d1416-afa3-4a74-919e-7adb0361c9c9

