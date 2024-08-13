import React, { useState, useEffect } from "react";
import { Chart } from "react-google-charts";
import { useNavigate, useLocation } from "react-router-dom";
import { toast, ToastContainer } from 'react-toastify';
import Modal from 'react-modal'; // Import Modal
import 'react-toastify/dist/ReactToastify.css';
import './projecttask.css';
import { fetchTasks, updateTask, fetchUsers } from "./Data"; // Updated to import fetchUsers

const ProjectTask = () => {
    const [tasks, setTasks] = useState([]);
    const [users, setUsers] = useState([]); // State to store users
    const [selectedTask, setSelectedTask] = useState(null); // Track the selected task
    const [updatedTask, setUpdatedTask] = useState({}); // Track the updated task fields
    const [modalIsOpen, setModalIsOpen] = useState(false); // State to control modal visibility
    const navigate = useNavigate();
    const location = useLocation();

    const { projectId, projectName } = location.state || {};

    useEffect(() => {
        if (projectId) {
            const loadTasks = async () => {
                const fetchedTasks = await fetchTasks(projectId);
                setTasks(fetchedTasks);
            };
            const loadUsers = async () => {
                const fetchedUsers = await fetchUsers();
                setUsers(fetchedUsers);
            };
            loadTasks();
            loadUsers(); // Load users
        }
    }, [projectId]);

    const handleTaskSelect = (e) => {
        const taskID = parseInt(e.target.value);
        const task = tasks.find(task => task.taskID === taskID);
        setSelectedTask(task);
        setUpdatedTask(task); // Initialize the updatedTask state with the selected task's data
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;

        // Convert date fields back to Date objects
        const updatedValue = (name === 'startDate' || name === 'endDate') ? new Date(value) : value;

        setUpdatedTask(prevTask => ({
            ...prevTask,
            [name]: updatedValue
        }));
    };

    const handleUserChange = (e, role) => {
        const selectedIds = Array.from(e.target.selectedOptions, option => parseInt(option.value));
        setUpdatedTask(prevTask => ({
            ...prevTask,
            [role]: selectedIds
        }));
    };

    const saveTask = async () => {
        if (selectedTask) {
            try {
                await updateTask(selectedTask.taskID, updatedTask);
                setTasks(prevTasks =>
                    prevTasks.map(task =>
                        task.taskID === selectedTask.taskID ? { ...task, ...updatedTask } : task
                    )
                );
                setModalIsOpen(false); // Close the modal after saving
                toast.success('Görev başarıyla güncellendi!');
            } catch (error) {
                toast.error('Görev güncellenemedi.');
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
            <div className="task-editor">
                <h3>Görev Düzenleme</h3>
                <select value={selectedTask?.taskID || ''} onChange={handleTaskSelect}>
                    <option value="" disabled>Görev Seçin</option>
                    {tasks.map(task => (
                        <option key={task.taskID} value={task.taskID}>
                            {task.taskName}
                        </option>
                    ))}
                </select>
                {selectedTask && (
                    <button onClick={() => setModalIsOpen(true)}>
                        Düzenle
                    </button>
                )}
            </div>

            <Modal
                isOpen={modalIsOpen}
                onRequestClose={() => setModalIsOpen(false)}
                className="small-modal"
                overlayClassName="overlay"
            >
                <h2 className="modal-header">Görev Düzenleme</h2>
                <div className="modal-body">
                    <input
                        type="text"
                        name="taskName"
                        value={updatedTask.taskName || ''}
                        onChange={handleInputChange}
                        placeholder="Görev Adı"
                    />
                    <input
                        type="date"
                        name="startDate"
                        value={updatedTask.startDate ? updatedTask.startDate.toISOString().split('T')[0] : ''}
                        onChange={handleInputChange}
                    />
                    <input
                        type="date"
                        name="endDate"
                        value={updatedTask.endDate ? updatedTask.endDate.toISOString().split('T')[0] : ''}
                        onChange={handleInputChange}
                    />
                    <input
                        type="number"
                        name="progress"
                        value={updatedTask.progress || 0}
                        onChange={handleInputChange}
                        min="0"
                        max="100"
                        placeholder="İlerleme (%)"
                    />
                    <input
                        type="number"
                        name="severity"
                        value={updatedTask.severity || 1}
                        onChange={handleInputChange}
                        min="1"
                        max="5"
                        placeholder="Önem Derecesi"
                    />
                    <div className="user-selectors">
                        <div className="form-group">
                            <label>Analistler:</label>
                            <select
                                name="analystIds"
                                multiple
                                value={updatedTask.analystIds || []}
                                onChange={(e) => handleUserChange(e, "analystIds")}
                            >
                                {users.map(user => (
                                    <option key={user.userID} value={user.userID}>
                                        {user.userName}
                                    </option>
                                ))}
                            </select>
                        </div>
                        <div className="form-group">
                            <label>Çözüm Mimarları:</label>
                            <select
                                name="solutionArchitectIds"
                                multiple
                                value={updatedTask.solutionArchitectIds || []}
                                onChange={(e) => handleUserChange(e, "solutionArchitectIds")}
                            >
                                {users.map(user => (
                                    <option key={user.userID} value={user.userID}>
                                        {user.userName}
                                    </option>
                                ))}
                            </select>
                        </div>
                        <div className="form-group">
                            <label>Yazılım Mimarları:</label>
                            <select
                                name="softwareArchitectIds"
                                multiple
                                value={updatedTask.softwareArchitectIds || []}
                                onChange={(e) => handleUserChange(e, "softwareArchitectIds")}
                            >
                                {users.map(user => (
                                    <option key={user.userID} value={user.userID}>
                                        {user.userName}
                                    </option>
                                ))}
                            </select>
                        </div>
                    </div>
                    <button className="save-button" onClick={saveTask}>
                        Kaydet
                    </button>
                </div>
                <button className="close-button" onClick={() => setModalIsOpen(false)}>Kapat</button>
            </Modal>
            <ToastContainer />
        </div>
    );
};

export default ProjectTask;
