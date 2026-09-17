# Smart Inventory Management System

## Overview

Smart Inventory Management System is a command-line based Java application for managing products, stock, orders, transactions, and inventory reports.

The project uses CSV files for data storage and a simple object-oriented design.

## Features

- Add, view, search, update, and delete products
- Stock-in and stock-out operations
- Low-stock detection
- Inventory value calculation
- Place customer orders
- Check product availability
- Maintain transaction records
- Generate inventory reports
- CSV file storage
- Input validation and exception handling

## Technologies Used

- Java
- Object-Oriented Programming
- Java Collections
- Exception Handling
- File I/O
- Multithreading concepts
- CSV file storage

## Requirements

- JDK 17 or later
- Terminal / Command Prompt

No external libraries or database are required.

## Project Structure

```text
SmartInventoryManagementSystem/
├── data/
├── src/
│   ├── Main.java
│   ├── Product.java
│   ├── Inventory.java
│   ├── Order.java
│   ├── Transaction.java
│   └── InventoryFile.java
├── tests/
│   └── InventoryTest.java
├── README.md
└── .gitignore
```

## How to Run

### macOS / Linux

```bash
rm -rf out
mkdir -p out
javac -d out src/*.java
java -cp out Main
```

### Windows PowerShell

```powershell
Remove-Item -Recurse -Force out -ErrorAction SilentlyContinue
New-Item -ItemType Directory out | Out-Null
javac -d out (Get-ChildItem src -Filter *.java).FullName
java -cp out Main
```

## Running Tests

### macOS / Linux

```bash
rm -rf out
mkdir -p out
javac -d out src/*.java tests/*.java
java -cp out InventoryTest
```

### Windows PowerShell

```powershell
Remove-Item -Recurse -Force out -ErrorAction SilentlyContinue
New-Item -ItemType Directory out | Out-Null
javac -d out (Get-ChildItem src,tests -Filter *.java).FullName
java -cp out InventoryTest
```

## Data Storage

The application stores product, order, and transaction information in CSV files inside the `data` directory.

## Author

Ayush  
B.Tech CSE AIML  
Programming in Java – CSE2006
