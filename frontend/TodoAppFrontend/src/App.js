import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './pages/Home';
import AddTask from './pages/AddTask';
import EditTask from './pages/EditTask';
import TaskProvider from './context/TaskContext';
import NavBar from './components/NavBar'; 

function App() {
  return (
   <TaskProvider>
    <Router>
    <NavBar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/add-task" element={<AddTask />} />
        <Route path="/edit-task/:id" element={<EditTask />} />
      </Routes>
    </Router>
  </TaskProvider>
  );
}

export default App;
