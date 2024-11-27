# CKP Lanches

**CKP Lanches** is a **customized system** for managing orders, inventory, and deliveries, developed to optimize the operation of snack bars and fast-food chains. The system offers an intuitive and functional interface, divided into **regular users** and **administrators**, with specific features for each access level.

## Table of Contents

1. [Overview](#overview)
2. [Features for Regular Users](#features-for-regular-users)
3. [Features for Administrators](#features-for-administrators)
4. [Advantages of the Customized System](#advantages-of-the-customized-system)
5. [Screenshots](#screenshots)
6. [How to Run the Project](#how-to-run-the-project)

### Overview

- **CKP Lanches** is a tailored system designed to optimize order management and inventory control in snack bars.
- It includes dedicated interfaces for **regular users** and **administrators**, with features adapted to different permission levels.
- The system includes resources for **inventory control**, **order management**, **insum report generation**, and more.

### Features for Regular Users

- **Inventory Check**: Verify the available quantity of items.
- **Sales Record**: Simple interface to record sales made.
- **Order Management**: Track orders placed by customers.
- **Delivery Management**: View delivery status and related details.

### Features for Administrators

- **Complete Inventory Management**: Administer stock items, including adding and updating products.
- **Insum Reports**: Generate detailed reports on used insums and stock of raw materials.
  - **Insum Below Average**: Identify items that are either being consumed or stocked below the expected average.
  - **Stock Quantity**: Monitor the current quantity of items in stock to ensure efficient replenishment.
- **User and Supplier Management**: Control user access to the system and manage suppliers.
- **Batch Administration**: Control product batches to ensure the correct flow of stock entries and exits.

### Advantages of the Customized System

- **Tailored to Business Needs**: Features and workflows custom-built for the specific operations of the snack bar.
- **Advanced Security**: Protection of sensitive data and authentication via JWT.
- **Ease of Use**: Intuitive interface for both regular users and administrators.
- **Scalability**: The system is developed with scalability in mind, making it easy to adapt for future functionalities.

## Screenshots

### Login Screen
![Login Screen](https://github.com/LuisGustavoDev/TCCckpLanches/blob/main/screenshots/img1.PNG?raw=true)

### **User Screens**
These screens are for regular users with limited permissions, featuring functionalities like stock queries and sales registration.

### User Dashboard
![User Dashboard](https://github.com/LuisGustavoDev/TCCckpLanches/blob/main/screenshots/user/img2.jpg?raw=true)

### Orders Screen
![Orders Screen](https://github.com/LuisGustavoDev/TCCckpLanches/blob/main/screenshots/user/img3.PNG?raw=true)

### Deliveries Screen
![Deliveries Screen](https://github.com/LuisGustavoDev/TCCckpLanches/blob/main/screenshots/user/img4.PNG?raw=true)

### Stock Screen
![Stock Screen](https://github.com/LuisGustavoDev/TCCckpLanches/blob/main/screenshots/user/img5.jpg?raw=true)

### Menu Screen
![Menu Screen](https://github.com/LuisGustavoDev/TCCckpLanches/blob/main/screenshots/user/img6.PNG?raw=true)

### **Administrator Screens**
These screens are for administrators with full permissions, including functionalities such as complete stock management, report generation, and financial performance analysis.

### Admin Dashboard
![Admin Dashboard](https://github.com/LuisGustavoDev/TCCckpLanches/blob/main/screenshots/admin/img7.jpg?raw=true)

### Orders Screen
![Orders Screen](https://github.com/LuisGustavoDev/TCCckpLanches/blob/main/screenshots/admin/img8.PNG?raw=true)

### Deliveries Screen
![Deliveries Screen](https://github.com/LuisGustavoDev/TCCckpLanches/blob/main/screenshots/admin/img9.jpg?raw=true)

### Stock Screen
![Stock Screen](https://github.com/LuisGustavoDev/TCCckpLanches/blob/main/screenshots/admin/img10.PNG?raw=true)

### Sales History Screen
![Sales History Screen](https://github.com/LuisGustavoDev/TCCckpLanches/blob/main/screenshots/admin/img11.PNG?raw=true)

### Menu Screen
![Menu Screen](https://github.com/LuisGustavoDev/TCCckpLanches/blob/main/screenshots/admin/img12.PNG?raw=true)

### Manage Users Screen
![Manage Users Screen](https://github.com/LuisGustavoDev/TCCckpLanches/blob/main/screenshots/admin/img13.PNG?raw=true)

### Suppliers Screen
![Suppliers Screen](https://github.com/LuisGustavoDev/TCCckpLanches/blob/main/screenshots/admin/img14.PNG?raw=true)

### Batches Screen
![Batches Screen](https://github.com/LuisGustavoDev/TCCckpLanches/blob/main/screenshots/admin/img15.PNG?raw=true)


## How to Run the Project

### Prerequisites
- **Java 21** or higher
- **Maven** to build the project
- **MySQL** or another compatible database configured

### Installation

1. **Clone the repository**:
    ```bash
    git clone https://github.com/LuisGustavoDev/TCCckpLanches.git
    ```

2. **Navigate to the project directory**:
    ```bash
    cd TCCckpLanches
    ```

3. **Create the Database**:
    - Before running the project, you need to create the `ckp_lanches` database in MySQL.
    - Run the following command in MySQL to create the database:
      ```sql
      CREATE DATABASE ckp_lanches;
      ```

4. **Configure the MySQL Database**:
    - Make sure that the database credentials (username and password) in the `application.properties` file are correct.
    - Here are the settings you need to adjust if necessary:

    ```properties
    # Database Settings
    spring.datasource.url=jdbc:mysql://localhost:3306/ckp_lanches?useTimezone=true&serverTimezone=UTC
    spring.datasource.username=root
    spring.datasource.password=1234
    ```

5. **Configure the `application.properties`**:
    The `application.properties` file contains project configurations, such as the database, access credentials, and JWT settings. Here are some of the properties you need to ensure are correctly set:

    ```properties
    # Database Settings
    spring.datasource.url=jdbc:mysql://localhost:3306/ckp_lanches?useTimezone=true&serverTimezone=UTC
    spring.datasource.username=root
    spring.datasource.password=1234

    # JPA (Hibernate) Settings
    spring.jpa.hibernate.ddl-auto=none  # Defines the database creation behavior
    spring.jpa.show-sql=true  # Displays SQL queries in the console

    # Database Initialization
    spring.datasource.initialization-mode=true
    spring.sql.init.mode=always

    # JWT Security Settings
    my-auth-secret-key=${JWT_SECRET:3dd97dc69115959749508d438d53e9ee}  # Secret key for JWT authentication
    ```

    **Important**: Replace `root` and `1234` with your database username and password. The `JWT_SECRET` key is used for generating JWT tokens and should be kept secret.

6. **Dependencies**:
    The project uses the following essential dependencies:

    - **Spring Boot** (Starter Data JPA, Security, Web)
    - **MySQL Connector**: For connecting to the MySQL database
    - **Lombok**: To reduce boilerplate code
    - **Spring Security**: For authentication and authorization
    - **JWT** (Java JWT): For generating JWT tokens
    - **Spring Boot DevTools**: To facilitate development

    The dependencies are defined in the Maven `pom.xml` file.

### Running the Project

To run the project, execute the following command in the terminal:

```bash
mvn spring-boot:run
```
