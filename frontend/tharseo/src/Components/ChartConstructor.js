import ApexChart from "react-apexcharts";

export default function ChartConstructor({ data }) {
  const options = {
    xaxis: {
      type: "datetime",
    },
    yaxis: {
      tooltip: true,
    },
  };

  const candles = [
    {
      data: data,
    },
  ];

  return (
    <ApexChart
      options={options}
      series={candles}
      type="candlestick"
      width={730}
      height={330}
    />
  );
}
