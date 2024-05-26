import React from "react";
import { render, fireEvent, screen } from "@testing-library/react";
import Header from "../../../Components/Nav/Header";


describe("Test Scope: Header Component", () => {
  test("Header Render", () => {
    render(<Header />);
  });

  test("Header Username Show", () => {
    const { getByText } = render(<Header />);
  });

  
});
