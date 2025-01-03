# task-manager
## Project Goal

The main goal of this project is to create a functional and responsive **To-Do List Manager** application that allows users to efficiently manage their tasks. The project consists of two main components:

### 1. Backend
A **REST API** service for managing tasks, including:
- Creating tasks
- Retrieving tasks
- Updating tasks
- Deleting tasks

### 2. Frontend
A user-friendly **web interface** for interacting with the to-do list, including:
- Viewing tasks
- Adding new tasks
- Editing task statuses
## Project Structure

### 1. Backend

The backend is implemented using **Java Servlets** running on a **Tomcat** server with **PostgreSQL** as the database. It provides the following REST API endpoints:

- **POST /tasks**: Create a new task with the following attributes:
  - `title` (String)
  - `description` (String)
  - `status` (Default: "pending")
  
- **GET /tasks**: Fetch all tasks.

- **GET /tasks/:id**: Fetch a specific task by its ID.

- **PUT /tasks/:id**: Update the status of a task. Possible statuses are:
  - `"pending"`
  - `"in-progress"`
  - `"completed"`

- **DELETE /tasks/:id**: Delete a specific task by its ID.

#### Backend Structure:

The backend contains a **Servlet project** which includes the **TodoAppBackend** project. Within this, we have written two servlets to handle the tasks: one for performing CRUD operations and another for managing the database connection.

- **CreateTaskServlet.java**: This servlet handles the task-related API endpoints and performs the following operations:
  - `doGet`: Fetches all tasks or a specific task based on the ID.
  - `doPost`: Creates a new task and stores it in the database.
  - `doPut`: Updates the status of an existing task.
  - `doDelete`: Deletes a task based on its ID.
  
- **DatabaseConnection.js**: Contains the code that establishes a connection with the PostgreSQL database, allowing the backend to interact with the database for creating, retrieving, updating, and deleting tasks.

### 2. Frontend

The frontend is built using **React.js** and communicates with the backend through REST API calls. It includes the following pages for interacting with tasks:

- **Home Page**: Displays a list of tasks with each task’s `title`, `description`, and `status`. The tasks are fetched from the backend via the `GET /tasks` endpoint.

- **Add Task Page**: Provides a form for users to create a new task by entering the task's `title`, `description`, and selecting its `status`. It uses the `POST /tasks` endpoint to send data to the backend.

- **Edit Task Page**: Allows users to edit the status of an existing task. It uses the `PUT /tasks/:id` endpoint to update the task’s status.

#### Frontend Features

- **State Management**: The application uses **Context API** for efficient state management across all components. Redux stores the tasks and task status updates, ensuring a seamless user experience.

- **API Requests**: API requests to the backend are made using the **Axios** library. The frontend communicates with the backend through the following API endpoints:
  - `POST /tasks`: To create a new task.
  - `GET /tasks`: To retrieve the list of tasks.
  - `PUT /tasks/:id`: To update a specific task's status.
  - `DELETE /tasks/:id`: To delete a task.

## Tech Stack

### Backend:
- **Language**: Java
- **Framework**: Servlets
- **Server**: Tomcat
- **Database**: PostgreSQL

### Frontend:
- **Language**: JavaScript (ES6+)
- **Framework**: React.js
- **State Management**: Context API
- **API Requests**: Axios

## How to Run the Project

### Prerequisites
Before running the project, ensure you have the following installed:

- **Java Development Kit (JDK)**
- **Apache Tomcat server**
- **PostgreSQL** and a database set up
- **Node.js** and **npm** for the frontend

### Steps to Run Backend

1. **Clone the repository**:
   ```bash
   git clone https://github.com/kvsahithi/task-manager.git
   cd task-manager/backend
    

2. **Import the backend project** into IntelliJ IDEA or your preferred IDE.

3. **Configure the PostgreSQL database connection** in your project. Ensure your `DatabaseConnection.js` is set up correctly with your PostgreSQL credentials.

4. **Deploy the backend project** to the **Tomcat server**.

5. **Start the Tomcat server** to launch the backend service.

### Steps to Run Frontend

1. **Navigate to the frontend folder**:
   ```bash
   cd ../frontend
   
2. **Install dependencies**:
   ```bash
   npm install
   
3.**Start the development server**:
   ```bash
   npm start
  ```

**Open your browser and navigate to [http://localhost:3000](http://localhost:3000) to access the application**.



