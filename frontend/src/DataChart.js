import React, { useEffect, useState } from 'react';
import { Line, Bar, Pie } from 'react-chartjs-2';
import {
    Chart as ChartJS,
    LineElement,
    PointElement,
    LinearScale,
    Title,
    Tooltip,
    Legend,
    CategoryScale,
    BarElement,
    ArcElement,
    Filler
} from 'chart.js';
import { generateDatesFor2022, columns, fetchAllData, groupByWeek, groupByMonth, formatDate, months } from './Functions';

ChartJS.register(
    LineElement,
    PointElement,
    LinearScale,
    Title,
    Tooltip,
    Legend,
    CategoryScale,
    Filler,
    BarElement,
    ArcElement
);

const DataChart = ({ currentUser }) => {
    const [allData, setAllData] = useState(null);
    const [filteredData, setFilteredData] = useState([]);
    const [period, setPeriod] = useState('week');
    const [selectedColumns, setSelectedColumns] = useState(['sumaPrzyjazduOgolem', 'sredniaStrefaGranicaUA']);
    const [startDate, setStartDate] = useState('2022-01-01');
    const [endDate, setEndDate] = useState('2022-12-31');
    const [filteredDates, setFilteredDates] = useState(generateDatesFor2022());
    const [showColumns, setShowColumns] = useState(false);

    useEffect(() => {
        const loadData = async () => {
            const data = await fetchAllData();
            setAllData(data);
            filterDataByDateRange(data, startDate, endDate);
        };

        loadData();
    }, []);

    useEffect(() => {
        if (allData) {
            filterDataByDateRange(allData, startDate, endDate);
        }
    }, [startDate, endDate, allData]);

    const filterDataByDateRange = (data, startDate, endDate) => {
        const start = new Date(startDate);
        const end = new Date(endDate);
        const filteredData = data.filter(item => {
            const itemDate = new Date(item.granica.data);
            return itemDate >= start && itemDate <= end;
        });
        const filteredDates = generateDatesFor2022().filter(date => {
            const currentDate = new Date(date);
            return currentDate >= start && currentDate <= end;
        });
        setFilteredData(filteredData);
        setFilteredDates(filteredDates);
    };

    if (!allData) {
        return <div className='mt10 ml5vw'><h1>Ładowanie...</h1></div>;
    }

    let aggregatedData;
    if (period === 'week') {
        aggregatedData = groupByWeek(filteredData);
    } else if (period === 'month') {
        aggregatedData = groupByMonth(filteredData);
    } else {
        aggregatedData = filteredData.map(item => ({
            data: item.granica.data,
            ...item.granica,
            ...item.drogowe
        }));
    }

    const labels = period === 'day'
        ? filteredDates.map(date => formatDate(date))
        : aggregatedData.map(item => period === 'week' ? `Tydzień ${item.tydzien}` : months[item.month - 1]);

    const granicaColumns = selectedColumns.filter(col => col.startsWith('sumaPrzyjazdu') || col.startsWith('sumaWyjazdu'));
    const drogoweColumns = selectedColumns.filter(col => col.startsWith('srednia'));

    const lineDatasets = [
        ...granicaColumns.map(column => ({
            label: columns.find(col => col.value === column).label,
            data: aggregatedData.map(item => item[column]),
            fill: false,
            backgroundColor: getRandomColor(),
            borderColor: getRandomColor(),
            yAxisID: 'y-granica',
        })),
        ...drogoweColumns.map(column => ({
            label: columns.find(col => col.value === column).label,
            data: aggregatedData.map(item => item[column]),
            fill: false,
            backgroundColor: getRandomColor(),
            borderColor: getRandomColor(),
            yAxisID: 'y-drogowe',
        }))
    ];

    const barData = {
        labels,
        datasets: granicaColumns.map(column => ({
            label: columns.find(col => col.value === column).label,
            data: aggregatedData.map(item => item[column]),
            backgroundColor: getRandomColor(),
        }))
    };

    const pieData = {
        labels: drogoweColumns.map(column => columns.find(col => col.value === column).label),
        datasets: [{
            data: drogoweColumns.map(column => aggregatedData.reduce((acc, item) => acc + item[column], 0)),
            backgroundColor: drogoweColumns.map(() => getRandomColor()),
        }]
    };

    const lineOptions = {
        scales: {
            'y-granica': {
                type: 'linear',
                position: 'left',
                title: {
                    display: true,
                    text: 'Granica'
                }
            },
            'y-drogowe': {
                type: 'linear',
                position: 'right',
                title: {
                    display: true,
                    text: 'Drogowe'
                },
                grid: {
                    drawOnChartArea: false,
                },
            },
        },
        responsive: true,
        maintainAspectRatio: false
    };

    const options = {
        responsive: true,
        maintainAspectRatio: false
    }

    const handlePeriodChange = (e) => {
        setPeriod(e.target.value);
    };

    const handleColumnChange = (e) => {
        const value = e.target.value;
        setSelectedColumns(
            selectedColumns.includes(value)
                ? selectedColumns.filter(col => col !== value)
                : [...selectedColumns, value]
        );
    };

    const exportToJson = () => {
        const exportData = aggregatedData.map((item, index) => {
            const exportedItem = {};

            if (period === 'day') {
                exportedItem['Data'] = formatDate(new Date(item.data));
            } else if (period === 'week') {
                exportedItem['Okres'] = `Tydzień ${item.tydzien}`;
            } else if (period === 'month') {
                exportedItem['Okres'] = months[item.month - 1];
            }

            selectedColumns.forEach(col => {
                const label = columns.find(column => column.value === col).label;
                exportedItem[label] = item[col];
            });

            return exportedItem;
        });

        const dataStr = "data:text/json;charset=utf-8," + encodeURIComponent(JSON.stringify(exportData, null, 2));
        const downloadAnchorNode = document.createElement('a');
        downloadAnchorNode.setAttribute("href", dataStr);
        downloadAnchorNode.setAttribute("download", "exported_data.json");
        document.body.appendChild(downloadAnchorNode);
        downloadAnchorNode.click();
        downloadAnchorNode.remove();
    };

    return (
        <div>
            <h2>Wykres Danych Granica i Drogowe 2022</h2>
            <div className='inputy'>
                <div>
                    <label>
                        Okres:
                    </label>
                    <select value={period} onChange={handlePeriodChange}>
                        {currentUser && (<option value="day">Dni</option>)}
                        <option value="week">Tygodnie</option>
                        <option value="month">Miesiące</option>
                    </select>

                </div>
                <div>
                    <label>
                        Data początkowa:
                    </label>
                    <input
                        type="date"
                        value={startDate}
                        min="2022-01-01"
                        max="2022-12-31"
                        onChange={(e) => setStartDate(e.target.value)}
                    />
                </div>
                <div>
                    <label>
                        Data końcowa:
                    </label>
                    <input
                        type="date"
                        value={endDate}
                        min="2022-01-01"
                        max="2022-12-31"
                        onChange={(e) => setEndDate(e.target.value)}
                    />
                </div>
            </div>

            <button onClick={() => setShowColumns(!showColumns)}>
                {showColumns ? 'Ukryj kolumny' : 'Pokaż kolumny'}
            </button>
            {showColumns && (
                <div className='d-flex'>
                    <div>
                        <h3>Granica</h3>
                        {columns.filter(col => col.value.startsWith('sumaPrzyjazdu') || col.value.startsWith('sumaWyjazdu')).map(column => (
                            <div key={column.value}>
                                <input
                                    type="checkbox"
                                    value={column.value}
                                    checked={selectedColumns.includes(column.value)}
                                    onChange={handleColumnChange}
                                />
                                {column.label}
                            </div>
                        ))}
                    </div>
                    <div>
                        <h3>Drogowe</h3>
                        {columns.filter(col => col.value.startsWith('srednia')).map(column => (
                            <div key={column.value}>
                                <input
                                    type="checkbox"
                                    value={column.value}
                                    checked={selectedColumns.includes(column.value)}
                                    onChange={handleColumnChange}
                                />
                                {column.label}
                            </div>
                        ))}
                    </div>
                </div>
            )}
            <div className='d-flex'>
                <div className='l-col'>
                    <Line data={{ labels, datasets: lineDatasets }} options={lineOptions} />
                    <button onClick={exportToJson}>Eksportuj dane do JSON</button>
                </div>
                <div className='r-col'>
                    <Bar data={barData} options={options} />
                    <Pie data={pieData} className='h100vh' />
                </div>
            </div>
        </div>
    );
};

function getRandomColor() {
    const letters = '0123456789ABCDEF';
    let color = '#';
    for (let i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
}

export default DataChart;
