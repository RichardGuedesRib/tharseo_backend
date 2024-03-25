import React, { useEffect, useState } from "react";
import ApexChart from "react-apexcharts";
import useWebSocket from "react-use-websocket";

export default function ChartConstructor({data}) {
 
 
 
  const options = {
    xaxis: {
      type: "datetime",
    },
    yaxis: {
      tooltip: true,
    },
  };

  

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
