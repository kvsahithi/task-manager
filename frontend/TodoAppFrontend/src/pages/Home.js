import React, { useContext } from "react";
import { TaskContext } from "../context/TaskContext";
import './Home.css';

const Home = () => {
  const { tasks, loading, error } = useContext(TaskContext);

  if (loading) return <p>Loading tasks...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div>
      <h1>Task List</h1>
      <ul>
        {tasks.map((task) => (
          <li key={task.id}>
            <h3>{task.title}</h3>
             
            <p>{task.completed ? "Completed" : "Not Completed"}</p>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Home;
