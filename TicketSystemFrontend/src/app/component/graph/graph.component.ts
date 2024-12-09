import { Component } from '@angular/core';
import { NgChartsModule } from 'ng2-charts';
import { ChartOptions, ChartData } from 'chart.js';

@Component({
  selector: 'app-graph',
  standalone: true,
  imports: [NgChartsModule],
  templateUrl: './graph.component.html',
  styleUrls: ['./graph.component.css'],
})
export class GraphComponent {
  public lineChartData: ChartData<'line'> = {
    labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun','Aug'],
    datasets: [
      {
        data: [10, 40, 30, 120, 30, 40, 10],
        label: 'Customer Retrieval Rete',
        borderColor: '#388e3c',
        backgroundColor: 'rgba(56, 142, 60, 0.1)',
        pointBackgroundColor: '#388e3c',
        pointBorderColor: '#388e3c',
        pointRadius: 5,
      }
    ]
  };

  public lineChartOptions: ChartOptions<'line'> = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        display: true,
        position: 'top',
        labels: {
          color: '#333',
        },
      },
    },
    scales: {
      x: {
        grid: {
          display: false,
          color: '#cccccc',
        },
      },
      y: {
        beginAtZero: true,
        grid: {
          display: false,
          color: '#cccccc',
        },
        ticks: {
          color: '#333',
        },
      },
    },
    elements: {
      line: {
        tension: 0.2,
      },
    },
  };

  public lineChartLegend = true;

  constructor() {}
}
