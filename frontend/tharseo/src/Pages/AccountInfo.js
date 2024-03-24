import "./account-style.css";
import React from "react";

function Accountinfo({ user }) {
  return (
    <div className="App">
      <div className="container-account">
        <div className="container-data">
          <h2> --- User Data ---</h2>
          <span>Id: {user.id}</span>
          <span>Name: {user.name}</span>
          <span>LastName: {user.lastname}</span>
          <span>Phone: {user.phoneNumber}</span>
          <span>Email: {user.email}</span>
          <span>ApiKey: {user.apiKey}</span>
          <span>SecretKey: {user.secretKey}</span>
        </div>
        <div className="container-wallet">
          <h2> --- User Wallet ---</h2>
          {user.wallet &&
            user.wallet.map((item, index) => (
              <div className="item-asset" key={index}>
                <span>Id: {item.id}</span>
                <span>Acronym: {item.acronym}</span>
                <span>Quantity: {item.quantity}</span>
              </div>
            ))}
          ;
        </div>

        <div className="container-transactions">
          <h2> --- User Transactions ---</h2>
          {user.transactions &&
            user.transactions.map((transaction, index) => (
              <div className="item-transaction" key={index}>
                <span>{transaction.orderId}</span>
                <span>{transaction.side}</span>
                <span>{transaction.asset}</span>
                <span>{transaction.origQty}</span>
                <span>$ {transaction.price}</span>
                <span>{transaction.openDate}</span>
                <span>{transaction.typeTransaction}</span>
                
              </div>
            ))}
        </div>
      </div>
    </div>
  );
}

export default Accountinfo;
