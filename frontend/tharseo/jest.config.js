module.exports = {
  testEnvironment: "jest-environment-jsdom",
  transform: {
    "^.+\\.(js|jsx)$": "babel-jest",
  },
  moduleNameMapper: {
    "\\.(jpg|jpeg|png|gif|svg)$": "<rootDir>/__mocks__/fileMock.js",
  },
  transformIgnorePatterns: [
    'node_modules/(?!(react-crypto-icons)/)'  
  ],
  setupFilesAfterEnv: ["<rootDir>/src/setupTests.js"],
};
