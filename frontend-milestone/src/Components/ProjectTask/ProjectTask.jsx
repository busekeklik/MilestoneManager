import React, { useState, useEffect } from "react";
import { Chart } from "react-google-charts";
import { useHistory } from "react-router-dom";
import './projecttask.css';
import { fetchTasks } from "./Data";

const ProjectTask = () => {
    const [tasks, setTasks] = useState([]);
    const [isChecked, setIsChecked] = useState(true);
    const history = useHistory();

    useEffect(() => {
        const loadTasks = async () => {
            const fetchedTasks = await fetchTasks();
            setTasks(fetchedTasks);
        };
        loadTasks();
    }, []);

    const data = [
        [
            { type: "string", label: "Task ID" },
            { type: "string", label: "Task Name" },
            { type: "string", label: "Resource" },
            { type: "date", label: "Start Date" },
            { type: "date", label: "End Date" },
            { type: "number", label: "Duration" },
            { type: "number", label: "Percent Complete" },
            { type: "string", label: "Dependencies" },
        ],
        ...tasks.map((task) => [
            task.taskID.toString(),
            task.taskName,
            null,
            new Date(task.startDate),
            new Date(task.endDate),
            null,
            task.progress,
            null,
        ]),
    ];

    const options = {
        height: 400,
        gantt: {
            trackHeight: 30,
        },
    };

    const handleButtonClick = () => {
        history.push("/TaskForm");
    };

    return (
        <div>
            <div className='tasks-title-wrapper'>
                <div className='tasks-title-bar'>
                    <div className='tasks-title'>Görevler</div>
                </div>
            </div>
            <button className="redirect-button" onClick={handleButtonClick}>
                Yeni Görev Ekle
            </button>
            <Chart
                chartType="Gantt"
                width="100%"
                height="400px"
                data={data}
                options={options}
            />
        </div>
    );
};

export default ProjectTask;