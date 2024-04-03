import "./assets/css/App.css";
import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Chart from "./Components/Chart";
import OrdersSetup from "./Pages/OrdersSetup";
import Accountinfo from "./Pages/AccountInfo";
import Home from "./Pages/Home";
import Trade from "./Pages/Trade";
import OldTransactions from "./Pages/OldTransactions";

function App() {
  const [user, setUser] = useState([]);
  const [addressServerTharseo, setAddressServerTharseo] = useState(
    "http://localhost:8080"
  );

  useEffect(() => {
    const getUser = async () => {
      try {
        const res = await fetch(
          addressServerTharseo + "/tharseo/updatedatauser/1"
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
    getUser();
  }, []);

  console.log("Return by Variable: ", user);

  return (
    <Router>
      <div className="App">
        <Routes>
          <Route
            exact
            path="/"
            element={<Home user={user} addressServer={addressServerTharseo} />}
          />
           <Route
            path="/trade"
            element={<Trade user={user} addressServer={addressServerTharseo} />}
          />
           <Route
            path="/oldtransactions"
            element={<OldTransactions user={user} addressServer={addressServerTharseo} />}
          />
          <Route path="/chart" element={<Chart />} />
          <Route path="/accountinfo" element={<Accountinfo user={user} />} />
          <Route path="/orderssetup" element={<OrdersSetup />} />
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
