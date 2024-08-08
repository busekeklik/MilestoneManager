import React, { useState, useEffect } from 'react';
import { Chart } from "react-google-charts";
import './DashboardPage.css';

const mockData = {
    tasks: [
        { id: 1, name: 'Task 1', member: 'Wade Warren', priority: 'Düşük' },
        { id: 2, name: 'Task 2', member: 'Jane Cooper', priority: 'Orta' },
        { id: 3, name: 'Task 3', member: 'Lesslie Alexander', priority: 'Yüksek' },
    ],
    members: [
        { name: 'Wade Warren', taskCount: 12 },
        { name: 'Jane Cooper', taskCount: 10 },
    ],
};

const DashboardPage = () => {
    const [tasks, setTasks] = useState([]);
    const [members, setMembers] = useState([]);

    useEffect(() => {
        setTasks(mockData.tasks);
        setMembers(mockData.members);
    }, []);

    const data = [
        ['Task', 'Hours per Day'],
        ['Buse', 10],
        ['Sude', 2],
        ['Mehmet', 2],
        ['Erdem', 2],
        ['Etiya', 7],
        ['Proje', 2]
    ];

    const options = {
        title: 'Tasklar',
        pieHole: 0.4,
    };

    return (
        <main className="dashboard-main">
            <section className="task-table">
                <table>
                    <thead>
                    <tr>
                        <th>Tasklar</th>
                        <th>Ekip Üyesi</th>
                        <th>Önem Derecesi</th>
                    </tr>
                    </thead>
                    <tbody>
                    {tasks.map(task => (
                        <tr key={task.id}>
                            <td>{task.name}</td>
                            <td>{task.member}</td>
                            <td>{task.priority}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </section>
            <section className="chart">
                <div className="chart-container">
                    <Chart
                        chartType="PieChart"
                        width="600px"
                        height="500px"
                        data={data}
                        options={options}
                    />
                    <div className="member-task-titles">
                        <span>Ekip Üyesi</span>
                        <span>Görev Sayısı</span>
                    </div>
                    <ul className="member-task-list">
                        {members.map(member => (
                            <li key={member.name}>
                                <span>{member.name}</span>
                                <span>{member.taskCount}</span>
                            </li>
                        ))}
                    </ul>
                </div>
            </section>
        </main>
    );
};

export default DashboardPage;