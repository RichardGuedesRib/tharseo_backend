import React from "react";
import ChartConstructor from "./ChartConstructor";

function Chart({ data }) {
  return (
    <div className="app-chart">
      <div className="container-header-data">
        <div className="chartData">
          <div className="chart-icon-data">
            <span class="material-symbols-outlined" style={{ fontSize: 35 }}>
              payments
            </span>
          </div>
          <div className="chart-item-data">
            <span className="title-data">Valor Total</span>
            <span className="value-data">$ 87.743</span>
          </div>
        </div>
        <div className="chartData">
          <div className="chart-icon-data">
            <span class="material-symbols-outlined" style={{ fontSize: 35 }}>
              monetization_on
            </span>
          </div>
          <div className="chart-item-data">
            <span className="title-data">Profit</span>
            <span className="value-data">$ 8.743</span>
          </div>
        </div>
        <div className="chartData">
          <div className="chart-icon-data">
            <span class="material-symbols-outlined" style={{ fontSize: 35 }}>
              finance_mode
            </span>
          </div>
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
              <span className="name-asset-btn">BTCUSDT</span>
              <span className="select-asset-btn">
                <span class="material-symbols-outlined">expand_more</span>
              </span>
            </div>
            <div className="type-chart">
              <span class="material-symbols-outlined" style={{ fontSize: 40 }}>
                candlestick_chart
              </span>
            </div>
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
