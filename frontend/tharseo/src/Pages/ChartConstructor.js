import React, { useEffect, useState } from "react";
import ApexChart from "react-apexcharts";
import useWebSocket from "react-use-websocket";

export default function ChartConstructor() {
  const [symbol, setSymbol] = useState("BTCUSDT");
  const [interval, setInterval] = useState("1m");
  const [data, setData] = useState([]);
 
  const options = {
    xaxis: {
      type: "datetime",
    },
    yaxis: {
      tooltip: true,
    },
  };

   const { lastJsonMessage } = useWebSocket(
    `wss://stream.binance.com:9443/ws/${symbol.toLowerCase()}@kline_${interval}`,
    {
      onOpen: () => console.log("Conected to Binance Stream"),
      onError: (err) => console.log(err),
      shouldReconnect: () => true,
      reconnectInterval: 3000,
      onMessage: () => {
        if (lastJsonMessage) {
          const newCandle = {
            x: new Date(lastJsonMessage.k.t),
            y: [
              parseFloat(lastJsonMessage.k.o),
              parseFloat(lastJsonMessage.k.h),
              parseFloat(lastJsonMessage.k.l),
              parseFloat(lastJsonMessage.k.c),
            ],
            z: lastJsonMessage.k.x,
          };
         
          setData(prevData => {
            const newData = [...prevData]; 
            if (lastJsonMessage.k.x === false) {
              newData[newData.length - 1] = newCandle;
            } else {
              newData.push(newCandle); 
            }
            return newData; 
          });

               }
      },
    }
  );

  const candles = [
    {
      data: data,
    },
  ];

  return (
    <ApexChart
      options={options}
      series={candles}
      type="candlestick"
      width={640}
      height={480}
    />
  );
}
