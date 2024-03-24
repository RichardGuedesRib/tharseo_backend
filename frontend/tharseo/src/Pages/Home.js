import "./style.css";
import React from "react";
import { Link } from "react-router-dom";

function Chart() {
  return (
    <div className="App">
      <div className="container">
        <div className="container-menu">
          <Link to="/chart" className="btn">
            Chart
          </Link>

          <Link to="/accountinfo" className="btn">
            Account Info
          </Link>
          <Link to="/orderssetup" className="btn">
            Orders Setup
          </Link>
        </div>
      </div>
    </div>
  );
}

export default Chart;
