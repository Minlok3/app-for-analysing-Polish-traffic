import React, { useEffect, useState } from 'react';
import axios from 'axios';

export const generateDatesFor2022 = () => {
    const dates = [];
    const year = 2022;
    const month = 0; // January

    for (let i = 1; i <= 365; i++) {
        const date = new Date(year, month, i);
        dates.push(date);
    }

    return dates;
};

// Helper function to format date in YYYY-MM-DD
export const formatDate = (date) => {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
};


export const fetchDataForDate = async (date) => {
    try {
        const response = await axios.get(`http://localhost:8080/dane/granica?date=${formatDate(date)}`, {
            headers: { 'Authorization': `Bearer ${localStorage.getItem('token')}`}
        });
        // if (!response.ok) {
        //     throw new Error(`Error fetching data for date: ${formatDate(date)}`);
        // }
        const data = await response.data;
        return data;
    } catch (error) {
        console.error(error);
        console.log(date.toISOString());
        return null; // Zwracanie null w przypadku błędu
    }
};

export const fetchDrogoweDataForDate = async (date) => {
    try {
        const response = await axios.get(`http://localhost:8080/dane/drogowe?date=${formatDate(date)}`, {
            headers: { 'Authorization': `Bearer ${localStorage.getItem('token')}`}
        });
        // if (!response.ok) {
        //     throw new Error(`Error fetching data for date: ${formatDate(date)}`);
        // }
        const data = await response.data;
        return data;
    } catch (error) {
        console.error(error);
        return null; // Zwracanie null w przypadku błędu
    }
};

export const fetchAllData = async () => {
    const dates = generateDatesFor2022();
    const chunkSize = 30; // Liczba jednoczesnych żądań
    let allData = [];

    for (let i = 0; i < dates.length; i += chunkSize) {
        const chunk = dates.slice(i, i + chunkSize);

        const promisesGranica = chunk.map(date => fetchDataForDate(date));
        const promisesDrogowe = chunk.map(date => fetchDrogoweDataForDate(date));
        
        const resultsGranica = await Promise.allSettled(promisesGranica);
        const resultsDrogowe = await Promise.allSettled(promisesDrogowe);

        const validResults = resultsGranica.map((granicaResult, index) => {
            const drogoweResult = resultsDrogowe[index];
            if (granicaResult.status === 'fulfilled' && drogoweResult.status === 'fulfilled') {
                return {
                    granica: granicaResult.value,
                    drogowe: drogoweResult.value
                };
            } else {
                return null;
            }
        }).filter(result => result !== null);

        allData = [...allData, ...validResults];

        // Dodaj timeout pomiędzy chunkami
        await new Promise(resolve => setTimeout(resolve, 100)); // 1 sekunda przerwy
    }

    return allData;
};


export const groupByWeek = (data) => {
    const weeks = {};
    data.forEach(item => {
        const week = item.granica.tydzien;
        if (!weeks[week]) {
            weeks[week] = [];
        }
        weeks[week].push(item);
    });

    return Object.keys(weeks).map(week => ({
        tydzien: week,
        ...aggregateData(weeks[week])
    }));
};

export const groupByMonth = (data) => {
    const months = {};
    data.forEach(item => {
        const month = new Date(item.granica.data).getMonth() + 1;
        if (!months[month]) {
            months[month] = [];
        }
        months[month].push(item);
    });

    return Object.keys(months).map(month => ({
        month: month,
        ...aggregateData(months[month])
    }));
};

export const aggregateData = (data) => {
    return data.reduce((acc, current) => {
        Object.keys(current.granica).forEach(key => {
            if (key !== 'id' && key !== 'data' && key !== 'tydzien') {
                acc[key] = (acc[key] || 0) + current.granica[key];
            }
        });
        Object.keys(current.drogowe).forEach(key => {
            if (key !== 'id' && key !== 'data' && key !== 'tydzien') {
                acc[key] = (acc[key] || 0) + current.drogowe[key];
            }
        });
        return acc;
    }, {});
};

export const filterDataByDateRange = (data, startDate, endDate) => {
    const start = new Date(startDate);
    const end = new Date(endDate);
    return data.filter(item => {
        const itemDate = new Date(item.granica.data);
        return itemDate >= start && itemDate <= end;
    });
};

export const columns = [
    { label: 'Suma Przyjazdu Ogółem', value: 'sumaPrzyjazduOgolem' },
    { label: 'Suma Wyjazdu Ogółem', value: 'sumaWyjazduOgolem' },
    { label: 'Suma Przyjazdu Polska', value: 'sumaPrzyjazduPolska' },
    { label: 'Suma Wyjazdu Polska', value: 'sumaWyjazduPolska' },
    { label: 'Suma Przyjazdu Ukraina', value: 'sumaPrzyjazduUkraina' },
    { label: 'Suma Wyjazdu Ukraina', value: 'sumaWyjazduUkraina' },
    { label: 'Suma Przyjazdu Niemcy', value: 'sumaPrzyjazduNiemcy' },
    { label: 'Suma Wyjazdu Niemcy', value: 'sumaWyjazduNiemcy' },
    { label: 'Suma Przyjazdu Stany Zjednoczone', value: 'sumaPrzyjazduStanyZjednoczone' },
    { label: 'Suma Wyjazdu Stany Zjednoczone', value: 'sumaWyjazduStanyZjednoczone' },
    { label: 'Suma Przyjazdu Rumunia', value: 'sumaPrzyjazduRumunia' },
    { label: 'Suma Wyjazdu Rumunia', value: 'sumaWyjazduRumunia' },
    { label: 'Suma Przyjazdu Wielka Brytania', value: 'sumaPrzyjazduWielkaBrytania' },
    { label: 'Suma Wyjazdu Wielka Brytania', value: 'sumaWyjazduWielkaBrytania' },
    { label: 'Suma Przyjazdu Mołdawia', value: 'sumaPrzyjazduMoldawia' },
    { label: 'Suma Wyjazdu Mołdawia', value: 'sumaWyjazduMoldawia' },
    { label: 'Suma Przyjazdu Inne', value: 'sumaPrzyjazduInne' },
    { label: 'Suma Wyjazdu Inne', value: 'sumaWyjazduInne' },
    { label: 'Suma Przyjazdu Oddział Nadbużański', value: 'sumaPrzyjazduOddzialNadbuzanski' },
    { label: 'Suma Wyjazdu Oddział Nadbużański', value: 'sumaWyjazduOddzialNadbuzanski' },
    { label: 'Suma Przyjazdu Oddział Bieszczadzki', value: 'sumaPrzyjazduOddzialBieszczadzki' },
    { label: 'Suma Wyjazdu Oddział Bieszczadzki', value: 'sumaWyjazduOddzialBieszczadzki' },
    { label: 'Suma Przyjazdu PG Drogowe', value: 'sumaPrzyjazduPgDrogowe' },
    { label: 'Suma Wyjazdu PG Drogowe', value: 'sumaWyjazduPgDrogowe' },
    { label: 'Suma Przyjazdu PG Kolejowe', value: 'sumaPrzyjazduPgKolejowe' },
    { label: 'Suma Wyjazdu PG Kolejowe', value: 'sumaWyjazduPgKolejowe' },
    { label: 'Średnia Strefa Granica UA', value: 'sredniaStrefaGranicaUA' },
    { label: 'Średnia Strefa Granica BYLT', value: 'sredniaStrefaGranicaBYLT' },
    { label: 'Średnia Strefa Granica RU', value: 'sredniaStrefaGranicaRU' },
    { label: 'Średnia Strefa Granica MB', value: 'sredniaStrefaGranicaMB' },
    { label: 'Średnia Strefa Granica DE', value: 'sredniaStrefaGranicaDE' },
    { label: 'Średnia Strefa Granica CZ', value: 'sredniaStrefaGranicaCZ' },
    { label: 'Średnia Strefa Granica SK', value: 'sredniaStrefaGranicaSK' },
    { label: 'Średnia Strefa Centralna', value: 'sredniaStrefaCentralna' },
    { label: 'Średnia Woj. DS', value: 'sredniaWojDS' },
    { label: 'Średnia Woj. KP', value: 'sredniaWojKP' },
    { label: 'Średnia Woj. LB', value: 'sredniaWojLB' },
    { label: 'Średnia Woj. LS', value: 'sredniaWojLS' },
    { label: 'Średnia Woj. LD', value: 'sredniaWojLD' },
    { label: 'Średnia Woj. MP', value: 'sredniaWojMP' },
    { label: 'Średnia Woj. MZ', value: 'sredniaWojMZ' },
    { label: 'Średnia Woj. OP', value: 'sredniaWojOP' },
    { label: 'Średnia Woj. PK', value: 'sredniaWojPK' },
    { label: 'Średnia Woj. PL', value: 'sredniaWojPL' },
    { label: 'Średnia Woj. PM', value: 'sredniaWojPM' },
    { label: 'Średnia Woj. SL', value: 'sredniaWojSL' },
    { label: 'Średnia Woj. SK', value: 'sredniaWojSK' },
    { label: 'Średnia Woj. WM', value: 'sredniaWojWM' },
    { label: 'Średnia Woj. WP', value: 'sredniaWojWP' },
    { label: 'Średnia Woj. ZP', value: 'sredniaWojZP' },
];


export const months = [
    'Styczeń', 'Luty', 'Marzec', 'Kwiecień', 'Maj', 'Czerwiec', 'Lipiec', 'Sierpień', 'Wrzesień', 'Październik', 'Listopad', 'Grudzień'
]
