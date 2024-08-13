import React, { useState } from 'react';
import { addDays, format, startOfWeek, subDays, isWeekend, isSameDay, startOfDay } from 'date-fns';
import './TeamPage.css';
import { tr } from 'date-fns/locale';

const TeamPage = () => {
    const [startDate, setStartDate] = useState(startOfWeek(new Date(), { weekStartsOn: 1 }));

    const handlePrevWeek = () => {
        setStartDate(subDays(startDate, 14));
    };

    const handleNextWeek = () => {
        setStartDate(addDays(startDate, 14));
    };

    const renderHeader = () => {
        const days = [];
        const today = startOfDay(new Date());
        for (let i = 0; i < 14; i++) {
            const date = addDays(startDate, i);
            const weekendClass = isWeekend(date) ? 'weekend' : '';
            const todayClass = isSameDay(date, today) ? 'today' : '';
            days.push(
                <th key={i} className={`${weekendClass} ${todayClass}`}>
                    {format(date, 'MMM d', { locale: tr })}
                </th>
            );
        }
        return days;
    };

    const renderCustomRowCells = (ranges) => {
        const today = startOfDay(new Date());
        return Array.from({ length: 14 }, (_, i) => {
            const date = addDays(startDate, i);
            const weekendClass = isWeekend(date) ? 'weekend' : '';
            const todayClass = isSameDay(date, today) ? 'today' : '';
            const isActive = ranges.some(range => i >= range.start && i <= range.end);
            const style = isActive ? { backgroundColor: '#5E83C2' } : {};
            return <td key={i} className={`${weekendClass} ${todayClass}`} style={style}></td>;
        });
    };

    return (
        <div className="team-page">
            <div className="team-page-header">
                <h1>EKİBİM</h1>
            </div>
            <div className="navigation-buttons">
                <button onClick={handlePrevWeek}>&#8592;</button>
                <button onClick={handleNextWeek}>&#8594;</button>
            </div>
            <div className="team-table-container">
                <table className="team-table">
                    <thead>
                    <tr>
                        <th>Ekip Üyeleri</th>
                        <th>Rolü</th>
                        <th>Task</th>
                        <th>Task İlerleyiş</th>
                        {renderHeader()}
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>Sude Yenidünya</td>
                        <td>Frontend Dev</td>
                        <td>Task A</td>
                        <td>Devam Ediyor</td>
                        {renderCustomRowCells([{start: 0, end: 3}])}
                    </tr>
                    <tr>
                        <td>Buse Keklik</td>
                        <td>Backend Dev</td>
                        <td>Task B</td>
                        <td>Devam Ediyor</td>
                        {renderCustomRowCells([{start: 8, end: 10}])}
                    </tr>
                    <tr>
                        <td>Erdem Önal</td>
                        <td>Backend Dev</td>
                        <td>Task C</td>
                        <td>Devam Ediyor</td>
                        {renderCustomRowCells([{start: 2, end: 4}])}
                    </tr>
                    <tr>
                        <td>Mehmet Emin Emre</td>
                        <td>Frontend Dev</td>
                        <td>Task D</td>
                        <td>Devam Ediyor</td>
                        {renderCustomRowCells([{start: 7, end: 10}])}
                    </tr>

                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default TeamPage;
