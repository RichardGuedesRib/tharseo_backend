import "./App.css";
import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Route, Link, Routes } from "react-router-dom";
import Chart from "./Pages/Chart";
import OrdersSetup from "./Pages/OrdersSetup";
import Accountinfo from "./Pages/AccountInfo";
import Home from "./Pages/Home";

function App() {
  const [user, setUser] = useState([]);

  useEffect(() => {
    const getUser = async () => {
      try {
        const res = await fetch("http://localhost:8080/users/1");
        if (!res.ok) {
          throw new Error("Error when get user");
        }
        const userData = await res.json();
        setUser(userData);
        console.log("Return by Variable: ", userData);
        return user;
      } catch (error) {
        console.error("Error User Resquest", error);
      }
    };

    getUser();

    
  }, []);

  return (
    <Router>
      <div className="App">
        <Routes>
          <Route exact path="/" element={<Home />} />
          <Route path="/chart" element={<Chart />} />
          <Route path="/accountinfo" element={<Accountinfo user={user} />} />
          <Route path="/orderssetup" element={<OrdersSetup />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
