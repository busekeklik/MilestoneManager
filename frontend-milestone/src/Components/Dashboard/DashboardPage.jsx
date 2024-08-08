import React, { useState, useEffect } from 'react';
import { Chart } from "react-google-charts";
import axios from 'axios';
import './DashboardPage.css';

const DashboardPage = () => {
    const [tasks, setTasks] = useState([]);
    const [members, setMembers] = useState([]);
    const [userTasks, setUserTasks] = useState([]);

    useEffect(() => {
        // Fetch tasks from the API
        axios.get('http://localhost:3307/task/api/v1/list')
            .then(response => {
                console.log("Tasks:", response.data);  // Log the API response
                setTasks(response.data);
            })
            .catch(error => {
                console.error('Error fetching tasks:', error);
            });

        // Fetch team members from the API
        axios.get('http://localhost:3307/user/api/v1/list')
            .then(response => {
                console.log("Members:", response.data);  // Log the API response
                setMembers(response.data);
            })
            .catch(error => {
                console.error('Error fetching members:', error);
            });

        // Fetch user-task mappings from the API
        axios.get('http://localhost:3307/user_task/api/v1/list')
            .then(response => {
                console.log("UserTasks:", response.data);  // Log the API response
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

    const calculateTaskCounts = () => {
        const taskCounts = {};
        tasks.forEach(task => {
            const memberName = getMemberName(task.taskID);
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
                        <tr key={task.taskID}>
                            <td>{task.taskName}</td>
                            <td>{getMemberName(task.taskID)}</td>
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
                        width="600px"
                        height="500px"
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
