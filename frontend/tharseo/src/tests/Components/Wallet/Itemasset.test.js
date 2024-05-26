import React from "react";
import { render, screen } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import Itemasset from "../../../Components/Wallet/Itemasset";
import { UserContext } from "../../../Services/UserDataProvider";

const mockAssetData = {
  symbol: "bnb",
  name: "teste",
  quantity: 1,
  value: 100,
  percent: 100,
};

describe("Test Scope: ItemAsset Component", () => {
  test("Component Render: ItemAsset", () => {
    <BrowserRouter>
      <UserContext.Provider value={mockAssetData}>
        <Itemasset
          symbol={mockAssetData.symbol}
          name={mockAssetData.name}
          quantity={mockAssetData.quantity}
          value={mockAssetData.value}
          percent={mockAssetData.percent}
        />
      </UserContext.Provider>
    </BrowserRouter>;
  });
});
