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
    "http://localhost:8080"
    // "http://52.147.195.35:8080"
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
    apiKey: "AIzaSyDWgCy5TuKHRgrAV6X0O8K4izz-DMJ4wmw",
    authDomain: "tharseofrontend.firebaseapp.com",
    projectId: "tharseofrontend",
    storageBucket: "tharseofrontend.appspot.com",
    messagingSenderId: "187000717412",
    appId: "1:187000717412:web:14590f489380e9eb561f71",
    measurementId: "G-ZXMP13TVF9",
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

//WebSocket Comentado para evitar reinicios consecutivos e bloqueio
// const { lastJsonMessage } = useWebSocket(
//   `wss://stream.binance.com:9443/ws/${symbol.toLowerCase()}@kline_${interval}`,
//   {
//     onOpen: () => console.log("Conected to Binance Stream"),
//     onError: (err) => console.log(err),
//     shouldReconnect: () => true,
//     reconnectInterval: 3000,
//     onMessage: () => {
//       if (lastJsonMessage) {
//         const newCandle = {
//           x: new Date(lastJsonMessage.k.t),
//           y: [
//             parseFloat(lastJsonMessage.k.o),
//             parseFloat(lastJsonMessage.k.h),
//             parseFloat(lastJsonMessage.k.l),
//             parseFloat(lastJsonMessage.k.c),
//           ],
//           z: lastJsonMessage.k.x,
//         };

//         setData((prevData) => {
//           const newData = [...prevData];
//           if (lastJsonMessage.k.x === false) {
//             newData[newData.length - 1] = newCandle;
//           } else {
//             if (newData.length < 60) {
//               newData.push(newCandle);
//             } else {
//               newData.shift();
//               newData.push(newCandle);
//             }
//           }
//           return newData;
//         });
//       }
//     },
//   }
// );

// const { lastJsonMessage } = useWebSocket(
//   "ws://localhost:8080/websocket",
//   {
//     onOpen: () => console.log("Conectado ao servidor WebSocket"),
//     onError: (err) => console.log(err),
//     shouldReconnect: () => true,
//     reconnectInterval: 3000,
//     onMessage: (msg) => {
//       console.log(msg);
//     },
//   }
// );

// const updatePrice = async () => {

//   // setInterval(updatePrice, 5000);
// };
