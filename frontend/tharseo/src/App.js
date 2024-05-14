import "./assets/css/App.css";
import React, { useContext, useState, useEffect } from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Chart from "./Components/Chart/Chart";
import Home from "./Pages/Home";
import Trade from "./Pages/Trade";
import OldTransactions from "./Pages/OldTransactions";
import OpenTrades from "./Pages/OpenTrades";
import Config from "./Pages/Config";
import Login from "./Pages/Login";
import Register from "./Pages/Register";
import {
  UserContext,
  UserDataProvider,
  useUserData,
} from "./Services/UserDataProvider";

function App() {
 
 

 

  useEffect(() => {
  
  }, []);

  return (
    <UserDataProvider>
      <Router>
        <div className="App">
          <Routes>
            <Route
              path="/home"
              element={
                <Home
                     />
              }
            />
            <Route
              path="/trade"
              element={
                <Trade  />
              }
            />
            <Route
              path="/oldtransactions"
              element={
                <OldTransactions
                 
                />
              }
            />
            <Route
              path="/opentrades"
              element={
                <OpenTrades
                
                />
              }
            />
            <Route
              path="/config"
              element={
                <Config
               
                />
              }
            />
            <Route path="/chart" element={<Chart />} />
            <Route
              exact
              path="/"
              element={
                <Login
                 
                />
              }
            />
            <Route
              path="/register"
              element={<Register />}
            />
            {/* <Route path="/" element={""} /> */}
          </Routes>
        </div>
      </Router>
    </UserDataProvider>
  );
}

export default App;
