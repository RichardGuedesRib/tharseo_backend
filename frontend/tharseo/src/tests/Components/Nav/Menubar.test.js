import React from "react";
import { render, screen } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import { UserContext } from "../../../Services/UserDataProvider";
import Menubar from "../../../Components/Nav/menubar";

const mockUserContext = {
  userProfile: { name: "Test User" },
  wallet: [{ acronym: "USDTUSDT", quantity: 100 }],
  transactions: [],
  updateUserData: jest.fn(),
  setIDUser: jest.fn(),
};

describe("Test Scope: Menubar Component", () => {
  test("Component Render: Menubar", () => {
    render(
      <BrowserRouter>
        <UserContext.Provider value={mockUserContext}>
          <Menubar />
        </UserContext.Provider>
      </BrowserRouter>
    );
  });

  describe("Test Scope: Buttons", () => {
    beforeEach(() => {
      const { getByText } = render(
        <BrowserRouter>
          <UserContext.Provider value={mockUserContext}>
            <Menubar />
          </UserContext.Provider>
        </BrowserRouter>
      );
    });

    test("Button: Dashboard", async () => {
      expect(screen.getAllByText(/Dashboard/i).textContent);
    });

    test("Button: Trade", async () => {
      expect(screen.getAllByText(/Trade/i).textContent);
    });

    test("Button: Em Andamento", async () => {
      expect(screen.getAllByText(/Em Andamento/i).textContent);
    });

    test("Button: Histórico", async () => {
      expect(screen.getAllByText(/Histórico/i).textContent);
    });

    test("Button: Configurações", async () => {
      expect(screen.getAllByText(/Configurações/i).textContent);
    });

    test("Button: Perfil", async () => {
      expect(screen.getAllByText(/Perfil/i).textContent);
    });

    test("Button: Sair", async () => {
      expect(screen.getAllByText(/Sair/i).textContent);
    });
  });

  
});
