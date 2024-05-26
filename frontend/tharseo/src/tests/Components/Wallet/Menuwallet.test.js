import React from "react";
import { render, screen } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import { UserContext } from "../../../Services/UserDataProvider";
import Menuwallet from "../../../Components/Wallet/Menuwallet";

const mockDataWallet = {
  userProfile: { name: "Test User" },
  wallet: [
    { acronym: "USDTUSDT", quantity: 1000 },
    { acronym: "BNBUSDT", quantity: 30 },
    { acronym: "BTCUSDT", quantity: 1 },
  ],
  transactions: [],
  updateUserData: jest.fn(),
  setIDUser: jest.fn(),
};

describe("Test Scope: MenuWallet Component", () => {
  test("Component Render: MenuWallet", () => {
    <BrowserRouter>
      <UserContext.Provider value={mockDataWallet}>
        <Menuwallet />
      </UserContext.Provider>
    </BrowserRouter>
  });

  
});
