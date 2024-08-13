import React, { useState, useEffect } from 'react';
import { Chart } from "react-google-charts";
import axios from 'axios';
import { CSVLink } from "react-csv";
import { FaFileCsv, FaFileDownload } from 'react-icons/fa';
import './DashboardPage.css';

const DashboardPage = () => {
    const [tasks, setTasks] = useState([]);
    const [members, setMembers] = useState([]);
    const [sortConfig, setSortConfig] = useState({ key: null, direction: 'ascending' });

    useEffect(() => {
        axios.get('http://localhost:3307/task/api/v1/list')
            .then(response => {
                console.log('Tasks:', response.data);
                setTasks(response.data);
            })
            .catch(error => {
                console.error('Error fetching tasks:', error);
            });

        axios.get('http://localhost:3307/user/api/v1/list')
            .then(response => {
                console.log('Members:', response.data);
                setMembers(response.data);
            })
            .catch(error => {
                console.error('Error fetching members:', error);
            });
    }, []);

    const getMemberNames = (task) => {
        const memberIds = [
            ...(task.analystIds || []).map(id => ({ id, role: 'A' })),
            ...(task.solutionArchitectIds || []).map(id => ({ id, role: 'ÇM' })),
            ...(task.softwareArchitectIds || []).map(id => ({ id, role: 'YM' }))
        ];

        const memberNames = memberIds.map(({ id, role }) => {
            const member = members.find(member => member.userID === id);
            return member ? `${member.userName} (${role})` : 'Unknown';
        });

        // Remove duplicates
        const uniqueMemberNames = [...new Set(memberNames)];

        return uniqueMemberNames.length ? uniqueMemberNames.join(', ') : 'Unknown';
    };

    const processedTasks = tasks.map(task => ({
        ...task,
        memberNames: getMemberNames(task),
    }));

    const calculateTaskCounts = () => {
        const taskCounts = {};

        processedTasks.forEach(task => {
            const uniqueMembers = new Set([
                ...(task.analystIds || []),
                ...(task.solutionArchitectIds || []),
                ...(task.softwareArchitectIds || [])
            ]);

            uniqueMembers.forEach(id => {
                const member = members.find(member => member.userID === id);
                if (member) {
                    const memberNameWithRole = `${member.userName} (${getRoleById(id, task)})`;
                    taskCounts[memberNameWithRole] = (taskCounts[memberNameWithRole] || 0) + 1;
                }
            });
        });

        return taskCounts;
    };

    const getRoleById = (id, task) => {
        if (task.analystIds.includes(id)) return 'A';
        if (task.solutionArchitectIds.includes(id)) return 'ÇM';
        if (task.softwareArchitectIds.includes(id)) return 'YM';
        return '';
    };

    const taskCounts = calculateTaskCounts();

    const chartData = [
        ['Ekip Üyesi', 'Görev Sayısı'],
        ...Object.entries(taskCounts)
    ];

    const options = {
        title: 'Tasklar',
        pieHole: 0.4,
    };

    const handleSort = (key) => {
        let direction = 'ascending';
        if (sortConfig.key === key && sortConfig.direction === 'ascending') {
            direction = 'descending';
        }
        setSortConfig({ key, direction });
    };

    const sortedTasks = [...processedTasks].sort((a, b) => {
        if (sortConfig.key) {
            if (sortConfig.direction === 'ascending') {
                if (sortConfig.key === 'taskName' || sortConfig.key === 'memberNames') {
                    return a[sortConfig.key].localeCompare(b[sortConfig.key]);
                }
                return a[sortConfig.key] - b[sortConfig.key];
            } else {
                if (sortConfig.key === 'taskName' || sortConfig.key === 'memberNames') {
                    return b[sortConfig.key].localeCompare(a[sortConfig.key]);
                }
                return b[sortConfig.key] - a[sortConfig.key];
            }
        }
        return 0;
    });

    const getHeaderIcon = (key) => {
        if (sortConfig.key === key) {
            return sortConfig.direction === 'ascending' ? '▲' : '▼';
        }
        return '~';
    };

    const downloadJSON = () => {
        const jsonString = `data:text/json;charset=utf-8,${encodeURIComponent(
            JSON.stringify(sortedTasks)
        )}`;
        const link = document.createElement("a");
        link.href = jsonString;
        link.download = "tasks.json";
        link.click();
    };

    return (
        <main className="dashboard-main">
            <section className="task-table">
                <table>
                    <thead>
                    <tr>
                        <th onClick={() => handleSort('taskName')}>
                            Tasklar {getHeaderIcon('taskName')}
                        </th>
                        <th onClick={() => handleSort('memberNames')}>
                            Ekip Üyesi {getHeaderIcon('memberNames')}
                        </th>
                        <th onClick={() => handleSort('severity')}>
                            Önem Derecesi {getHeaderIcon('severity')}
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    {sortedTasks.map(task => (
                        <tr key={task.taskID}>
                            <td>{task.taskName}</td>
                            <td>{task.memberNames}</td>
                            <td>{task.severity}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </section>
            <section className="chart">
                <div className="chart-container">
                    <Chart
                        chartType="PieChart"
                        width="730px"
                        height="630px"
                        data={chartData}
                        options={options}
                    />
                    <div className="member-task-titles">
                        <span>Ekip Üyesi</span>
                        <span>Görev Sayısı</span>
                    </div>
                    <ul className="member-task-list">
                        {Object.entries(taskCounts).map(([memberNames, taskCount]) => (
                            <li key={memberNames}>
                                <span>{memberNames}</span>
                                <span>{taskCount}</span>
                            </li>
                        ))}
                    </ul>
                </div>
            </section>
            <section className="download-buttons">
                <CSVLink data={sortedTasks} filename={"tasks.csv"} className="download-button">
                    <FaFileCsv/> CSV
                </CSVLink>
                <button onClick={downloadJSON} className="download-button">
                    <FaFileDownload/> JSON
                </button>
            </section>
        </main>
    );
};

export default DashboardPage;
