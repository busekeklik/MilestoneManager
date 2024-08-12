import React, { useState, useEffect } from 'react';
import { Chart } from "react-google-charts";
import axios from 'axios';
import { CSVLink } from "react-csv";
import { FaFileCsv, FaFileDownload } from 'react-icons/fa'; // Import icons
import './DashboardPage.css';

const DashboardPage = () => {
    const [tasks, setTasks] = useState([]);
    const [members, setMembers] = useState([]);
    const [userTasks, setUserTasks] = useState([]);
    const [sortConfig, setSortConfig] = useState({ key: null, direction: 'ascending' });

    useEffect(() => {
        axios.get('http://localhost:3307/task/api/v1/list')
            .then(response => {
                setTasks(response.data);
            })
            .catch(error => {
                console.error('Error fetching tasks:', error);
            });

        axios.get('http://localhost:3307/user/api/v1/list')
            .then(response => {
                setMembers(response.data);
            })
            .catch(error => {
                console.error('Error fetching members:', error);
            });

        axios.get('http://localhost:3307/user_task/api/v1/list')
            .then(response => {
                setUserTasks(response.data);
            })
            .catch(error => {
                console.error('Error fetching user-task mappings:', error);
            });
    }, []);

    const getMemberName = (taskId) => {
        const userTask = userTasks.find(ut => ut.taskID === taskId);
        if (userTask) {
            const member = members.find(member => member.userID === userTask.userID);
            return member ? member.userName : 'Unknown';
        }
        return 'Unknown';
    };

    const processedTasks = tasks.map(task => ({
        ...task,
        memberName: getMemberName(task.taskID),
    }));

    const calculateTaskCounts = () => {
        const taskCounts = {};
        processedTasks.forEach(task => {
            const memberName = task.memberName;
            if (taskCounts[memberName]) {
                taskCounts[memberName]++;
            } else {
                taskCounts[memberName] = 1;
            }
        });
        return taskCounts;
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
                if (sortConfig.key === 'taskName' || sortConfig.key === 'memberName') {
                    return a[sortConfig.key].localeCompare(b[sortConfig.key]);
                }
                return a[sortConfig.key] - b[sortConfig.key];
            } else {
                if (sortConfig.key === 'taskName' || sortConfig.key === 'memberName') {
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
                        <th onClick={() => handleSort('memberName')}>
                            Ekip Üyesi {getHeaderIcon('memberName')}
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
                            <td>{task.memberName}</td>
                            <td>{task.severity}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>

                {/* Centered Buttons for CSV and JSON download */}
                <div className="download-buttons">
                    <CSVLink data={sortedTasks} filename={"tasks.csv"} className="download-button">
                        <FaFileCsv /> CSV
                    </CSVLink>
                    <button onClick={downloadJSON} className="download-button">
                        <FaFileDownload /> JSON
                    </button>
                </div>
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
                        {Object.entries(taskCounts).map(([memberName, taskCount]) => (
                            <li key={memberName}>
                                <span>{memberName}</span>
                                <span>{taskCount}</span>
                            </li>
                        ))}
                    </ul>
                </div>
            </section>
        </main>
    );
};

export default DashboardPage;
