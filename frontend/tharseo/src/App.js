import "./assets/css/App.css";
import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Chart from "./Components/Chart";
import Home from "./Pages/Home";
import Trade from "./Pages/Trade";
import OldTransactions from "./Pages/OldTransactions";
import OpenTrades from "./Pages/OpenTrades";
import Config from "./Pages/Config";
import Login from "./Pages/Login";
import Register from "./Pages/Register";

import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";

function App() {
  const [user, setUser] = useState([]);
  const [addressServerTharseo, setAddressServerTharseo] = useState(
    // "http://localhost:8080"
    "http://74.235.240.231:8080"
  );
  const getUser = async () => {
    try {
      const res = await fetch(
        addressServerTharseo + "/tharseo/updatedatauser/" + user.id
      );
      if (!res.ok) {
        throw new Error("Error when get user");
      }
      const userData = await res.json();
      setUser(userData);
      console.log("Return by Variable: ", userData);
    } catch (error) {
      console.error("Error User Resquest", error);
    }
  };
  const getUserByLogin = (data) => {
    setUser(data);
  };

  const firebaseConfig = {
    apiKey: "AIzaSyDudc8MmV-mpCKuMSrKYsCYt71SwRElHKQ",
    authDomain: "tharseoautomator.firebaseapp.com",
    projectId: "tharseoautomator",
    storageBucket: "tharseoautomator.appspot.com",
    messagingSenderId: "85357636130",
    appId: "1:85357636130:web:edd58dd9da252bba0df87b",
    measurementId: "G-D0TEV1WCPC",
  };

  const app = initializeApp(firebaseConfig);
  const analytics = getAnalytics(app);

  useEffect(() => {
    // getUser();
    console.log("User Effects User");
    console.log(user);
  }, []);

  return (
    <Router>
      <div className="App">
        <Routes>
          <Route
            path="/home"
            element={
              <Home
                user={user}
                addressServer={addressServerTharseo}
                getUser={getUser}
              />
            }
          />
          <Route
            path="/trade"
            element={<Trade user={user} addressServer={addressServerTharseo} />}
          />
          <Route
            path="/oldtransactions"
            element={
              <OldTransactions
                user={user}
                addressServer={addressServerTharseo}
              />
            }
          />
          <Route
            path="/opentrades"
            element={
              <OpenTrades
                user={user}
                addressServer={addressServerTharseo}
                getUser={getUser}
              />
            }
          />
          <Route
            path="/config"
            element={
              <Config
                user={user}
                addressServer={addressServerTharseo}
                getUser={getUser}
              />
            }
          />
          <Route path="/chart" element={<Chart />} />
          <Route
            exact
            path="/"
            element={
              <Login
                addressServerTharseo={addressServerTharseo}
                getUserByLogin={getUserByLogin}
              />
            }
          />
          <Route
            path="/register"
            element={<Register addressServerTharseo={addressServerTharseo} />}
          />
          {/* <Route path="/" element={""} /> */}
        </Routes>
      </div>
    </Router>
  );
}

export default App;
