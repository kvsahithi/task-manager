import React, { useState, useContext } from "react";
import { TaskContext } from "../context/TaskContext";
import { useNavigate } from "react-router-dom";
import './AddTask.css';

const AddTask = () => {
  const { addTask } = useContext(TaskContext);
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();

    const newTask = {
      title,
      description,
      completed: false,
    };

    addTask(newTask);
    navigate("/"); 
  };

  return (
     <div>
      <h1>Add New Task</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Title</label>
          <input
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Description</label>
          <textarea
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            required
          />
        </div>
        <button type="submit">Add Task</button>
      </form>
    </div>
  );
};

export default AddTask;
