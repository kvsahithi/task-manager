
import React from 'react';
import { Link } from 'react-router-dom';
import './NavBar.css';

const NavBar = () => {
  return (
    <nav>
      <ul>
        <li>
          <Link to="/">Home</Link>
        </li>
        <li>
          <Link to="/add-task">Add Task</Link>
        </li>
        <li>
          <Link to="/edit-task/1">Edit Task</Link> 
        </li>
      </ul>
    </nav>
  );
};

export default NavBar;
