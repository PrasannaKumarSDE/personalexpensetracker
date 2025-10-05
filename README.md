# Personal Expense Tracker

## Features
- Add, edit, delete personal expenses
- Track expenses with date, note, category, amount
- Summary reports: total spent, grouped by category
- Charts: pie chart (by category), bar chart (monthly trend)
- Frontend uses HTML/JS with Chart.js
- Backend: Spring Boot + MySQL

## Requirements
- Java 17+
- Maven
- MySQL
- Browser (for frontend)
- Frontend

Open frontend/index.html in browser

Add, edit, delete expenses. Charts and summary update automatically.

### **Short Documentation **



## Assumptions
- Date stored as `YYYY-MM-DD`
- Amount is numeric
- Categories: Food, Travel, Bills, Shopping, Entertainment, Other
- Frontend fetches directly from backend API

## Design
- Backend: Spring Boot REST API
- Frontend: HTML + JavaScript
- Charts: Chart.js
- Summary cards computed in frontend

## Sample Inputs / Outputs
### Input
```json
{
  "date":"2025-10-04",
  "note":"Lunch",
  "category":"Food",
  "amount":250.50
}
Output (GET /api/expenses)
json
Copy code
[
  {
    "id":1,
    "date":"2025-10-04",
    "note":"Lunch",
    "category":"Food",
    "amount":250.5
  }
]

restApis
GET all: GET /api/expenses

GET by id: GET /api/expenses/{id}

POST create: POST /api/expenses with JSON:
{ "amount": 10.5, "date": "2025-10-01", "note": "Lunch", "category": "Food" }

PUT update: PUT /api/expenses/{id}

DELETE: DELETE /api/expenses/{id}

Summary by category: GET /api/expenses/summary/category

Summary by month: GET /api/expenses/summary/month
