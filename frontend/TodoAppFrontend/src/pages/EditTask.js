import React, { useState, useEffect, useContext } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { TaskContext } from "../context/TaskContext";
import './EditTask.css';

const EditTask = () => {
  const { id } = useParams();
  const { tasks, updateTask } = useContext(TaskContext);
  const navigate = useNavigate();

  const task = tasks.find((task) => task.id === parseInt(id));
  const [completed, setCompleted] = useState(task ? task.completed : false);

  useEffect(() => {
    if (task) {
      setCompleted(task.completed);
    }
  }, [task]);

  const handleSubmit = (e) => {
    e.preventDefault();
    updateTask(id, { completed });
    navigate("/"); // Redirect to Home after updating task
  };

  if (!task) return <p>Task not found</p>;

  return (
    <div>
      <h1>Edit Task</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Status</label>
          <select value={completed} onChange={(e) => setCompleted(e.target.value === "true")}>
            <option value="false">Not Completed</option>
            <option value="true">Completed</option>
          </select>
        </div>
        <button type="submit">Update Task</button>
      </form>
    </div>
  );
};

export default EditTask;
