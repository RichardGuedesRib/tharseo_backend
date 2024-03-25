import "./style.css";
import React from "react";
import ChartConstructor from "./ChartConstructor";


function Chart({data}) {
  return (
    <div className="App app-chart">
      <div className="container-chart" id="chart-container">
       <ChartConstructor />
      </div>
    </div>
  );
}

export default Chart;
