import "./style.css";
import React from "react";
import ChartConstructor from "./ChartConstructor";

function Chart({ data }) {
  return (
    <div className="app-chart">

      


      <div className="container-header-data">
        <div className="chartData">
          <div className="chart-icon-data"></div>
          <div className="chart-item-data">
            <span className="title-data">Valor Total</span>
            <span className="value-data">$ 87.743</span>
          </div>
        </div>
        <div className="chartData">
          <div className="chart-icon-data"></div>
          <div className="chart-item-data">
            <span className="title-data">Profit</span>
            <span className="value-data">$ 8.743</span>
          </div>
        </div>
        <div className="chartData">
          <div className="chart-icon-data"></div>
          <div className="chart-item-data">
            <span className="title-data">Profit</span>
            <span className="value-data">+12.56%</span>
          </div>
        </div>
      </div>
      <div className="container-header-options">
        <div className="container-header-options-top">
          <span className="title-header-options">Performance do Ativo</span>
        </div>
        <div className="container-header-options-bottom">
          <div className="container-header-options-bottom-left">
            <div className="button-asset">
              <span className="icon-asset-btn"></span>
              <span className="name-asset-btn">ETH</span>
              <span className="select-asset-btn">D</span>
            </div>
            <div className="type-chart"></div>
          </div>
          <div className="container-header-options-bottom-right">
            <span className="btn-time-chart">1M</span>
            <span className="btn-time-chart">5M</span>
            <span className="btn-time-chart">15M</span>
          </div>
        </div>
      </div>

      <div className="container-chart" id="chart-container">
        <ChartConstructor data={data} />
      </div>
    </div>
  );
}

export default Chart;
