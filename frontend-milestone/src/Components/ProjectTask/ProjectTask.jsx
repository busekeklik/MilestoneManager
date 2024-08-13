import React, { useState, useEffect } from "react";
import { Chart } from "react-google-charts";
import { useNavigate, useLocation } from "react-router-dom";
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './projecttask.css';
import { fetchTasks, updateTaskProgress } from "./Data";

const ProjectTask = () => {
    const [tasks, setTasks] = useState([]);
    const [selectedTask, setSelectedTask] = useState(null); // Track the selected task
    const [newProgress, setNewProgress] = useState(0); // Track the new progress
    const navigate = useNavigate();
    const location = useLocation();

    const { projectId, projectName } = location.state || {};

    useEffect(() => {
        if (projectId) {
            const loadTasks = async () => {
                const fetchedTasks = await fetchTasks(projectId);
                setTasks(fetchedTasks);
            };
            loadTasks();
        }
    }, [projectId]);

    const handleTaskSelect = (e) => {
        const taskID = parseInt(e.target.value);
        const task = tasks.find(task => task.taskID === taskID);
        setSelectedTask(task);
        setNewProgress(task.progress); // Set the current progress as the initial value
    };

    const handleProgressChange = (e) => {
        setNewProgress(parseInt(e.target.value));
    };

    const saveProgress = async () => {
        if (selectedTask) {
            try {
                await updateTaskProgress(selectedTask.taskID, newProgress);
                setTasks(prevTasks =>
                    prevTasks.map(task =>
                        task.taskID === selectedTask.taskID ? { ...task, progress: newProgress } : task
                    )
                );
                toast.success('İlerleme başarıyla güncellendi!');
            } catch (error) {
                toast.error('Güncelleme yapılamadı.');
                console.error(error);
            }
        }
    };

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
            task.dependencies,
        ]),
    ];

    const options = {
        height: 500,
        gantt: {
            trackHeight: 40,
            criticalPathEnabled: true,
            criticalPathStyle: {
                stroke: "#e64a19",
                strokeWidth: 5,
            },
            arrow: {
                angle: 100,
                width: 5,
                color: "green",
                radius: 0,
            },
            innerGridTrack: { fill: "#e3f2fd" },
            innerGridDarkTrack: { fill: "#bbdefb" },
            innerGridHorizLine: {
                stroke: "#90caf9",
                strokeWidth: 2,
            },
        },
    };

    const handleButtonClick = () => {
        navigate("/TaskForm", { state: { projectId, projectName } });
    };

    return (
        <div>
            <div className='tasks-title-wrapper'>
                <div className='tasks-title-bar'>
                    <div className='tasks-title'>{projectName || 'Unknown Project'}</div>
                </div>
            </div>
            <button className="redirect-button" onClick={handleButtonClick}>
                Yeni Görev Ekle
            </button>
            <Chart
                chartType="Gantt"
                width="100%"
                height="500px"
                data={data}
                options={options}
            />
            <div className="task-progress-editor">
                <h3>Task İlerlemesi Düzenleme</h3>
                <div className="task-progress-controls">
                    <select value={selectedTask?.taskID || ''} onChange={handleTaskSelect}>
                        <option value="" disabled>Task Seçin</option>
                        {tasks.map(task => (
                            <option key={task.taskID} value={task.taskID}>
                                {task.taskName}
                            </option>
                        ))}
                    </select>
                    {selectedTask && (
                        <>
                            <input
                                type="number"
                                value={newProgress}
                                onChange={handleProgressChange}
                                min="0"
                                max="100"
                            />
                            <button onClick={saveProgress}>
                                Kaydet
                            </button>
                        </>
                    )}
                </div>
            </div>
            <ToastContainer />
        </div>
    );
};

export default ProjectTask;
