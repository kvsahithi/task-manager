import React, { createContext, useState, useEffect } from 'react';
import axios from 'axios';

// Create a Context for tasks
export const TaskContext = createContext();

const TaskProvider = ({ children }) => {
  const [tasks, setTasks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Fetch tasks from the API
  useEffect(() => {
    const fetchTasks = async () => {
      try {
        const response = await axios.get("https://jsonplaceholder.typicode.com/todos");
        setTasks(response.data);
        setLoading(false);
      } catch (err) {
        setError("Failed to fetch tasks");
        setLoading(false);
      }
    };

    fetchTasks();
  }, []);

  const addTask = async (newTask) => {
    try {
      const response = await axios.post("https://jsonplaceholder.typicode.com/todos", newTask);
      setTasks([...tasks, response.data]);
    } catch (err) {
      setError("Failed to add task");
    }
  };

  const updateTask = async (id, updatedTask) => {
    try {
      const response = await axios.put(`https://jsonplaceholder.typicode.com/todos/${id}`, updatedTask);
      setTasks(tasks.map(task => (task.id === id ? response.data : task)));
    } catch (err) {
      setError("Failed to update task");
    }
  };

  return (
    <TaskContext.Provider value={{ tasks, loading, error, addTask, updateTask }}>
      {children}
    </TaskContext.Provider>
  );
};

export default TaskProvider;
