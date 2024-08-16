import React, { useState, useEffect } from "react";
import { Chart } from "react-google-charts";
import { useNavigate, useLocation } from "react-router-dom";
import { toast, ToastContainer } from 'react-toastify';
import Modal from 'react-modal';
import Select from 'react-select';
import 'react-toastify/dist/ReactToastify.css';
import './projecttask.css';
import { fetchTasks, updateTask, fetchUsers } from "./Data"; // Import your data fetching functions

const ProjectTask = () => {
    const [tasks, setTasks] = useState([]);
    const [users, setUsers] = useState([]);
    const [selectedTask, setSelectedTask] = useState(null);
    const [updatedTask, setUpdatedTask] = useState({});
    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [confirmModalIsOpen, setConfirmModalIsOpen] = useState(false);
    const [confirmationInput, setConfirmationInput] = useState('');
    const navigate = useNavigate();
    const location = useLocation();

    const { projectId, projectName } = location.state || {};

    const severityOptions = [
        { value: 0, label: 'None (0)' },
        { value: 1, label: 'Low (1)' },
        { value: 2, label: 'Moderate (2)' },
        { value: 3, label: 'High (3)' },
        { value: 4, label: 'Extreme (4)' }
    ];

    useEffect(() => {
        if (projectId) {
            const loadTasks = async () => {
                const fetchedTasks = await fetchTasks(projectId);
                setTasks(fetchedTasks.filter(task => !task.deleted)); // Filter out deleted tasks
            };
            const loadUsers = async () => {
                const fetchedUsers = await fetchUsers();
                setUsers(fetchedUsers);
            };
            loadTasks();
            loadUsers();
        }
    }, [projectId]);

    const mapIdsToOptions = (ids, options) => {
        return ids.map(id => options.find(option => option.value === id)).filter(option => option);
    };

    const handleTaskSelect = (e) => {
        const taskID = parseInt(e.target.value);
        const task = tasks.find(task => task.taskID === taskID);
        setSelectedTask(task);

        const analystIds = task.analystIds || [];
        const solutionArchitectIds = task.solutionArchitectIds || [];
        const softwareArchitectIds = task.softwareArchitectIds || [];
        const dependencies = task.dependencyIds || [];

        setUpdatedTask({
            ...task,
            severity: severityOptions.find(option => option.value === task.severity) || severityOptions[0],
            analystIds: mapIdsToOptions(analystIds, users),
            solutionArchitectIds: mapIdsToOptions(solutionArchitectIds, users),
            softwareArchitectIds: mapIdsToOptions(softwareArchitectIds, users),
            dependencies: mapIdsToOptions(dependencies, tasks.map(t => ({ value: t.taskID, label: t.taskName }))),
        });
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setUpdatedTask(prevTask => ({
            ...prevTask,
            [name]: value
        }));
    };

    const handleSelectChange = (selected, name) => {
        setUpdatedTask(prevTask => ({
            ...prevTask,
            [name]: Array.isArray(selected)
                ? selected.map(option => option)
                : selected
        }));
    };

    const validateForm = () => {
        const { severity, analysisDuration, developmentDuration, startDate, endDate, cost } = updatedTask;

        if (!severity) {
            alert('Önem derecesi seçin!');
            return false;
        }
        if (parseInt(analysisDuration) < 0 || parseInt(developmentDuration) < 0) {
            alert('Analiz süresi ve geliştirme süresi negatif olamaz.');
            return false;
        }
        const manDays = parseInt(analysisDuration) + parseInt(developmentDuration);
        if (manDays < 0) {
            alert('Man days negatif olamaz.');
            return false;
        }
        if (parseInt(cost) < 0) {
            alert('Maliyet negatif olamaz.');
            return false;
        }
        if (new Date(startDate) > new Date(endDate)) {
            alert('Başlangıç tarihi bitiş tarihinden sonra olamaz.');
            return false;
        }
        return true;
    };

    const saveTask = async () => {
        if (!validateForm()) {
            return;
        }

        const taskData = {
            taskID: selectedTask.taskID,
            taskName: updatedTask.taskName,
            startDate: updatedTask.startDate,
            endDate: updatedTask.endDate,
            manDays: parseInt(updatedTask.analysisDuration) + parseInt(updatedTask.developmentDuration),
            cost: parseInt(updatedTask.cost),
            severity: updatedTask.severity?.value,
            progress: selectedTask.progress,
            projectId: projectId,
            analystIds: updatedTask.analystIds.map(a => a.value),
            solutionArchitectIds: updatedTask.solutionArchitectIds.map(sa => sa.value),
            softwareArchitectIds: updatedTask.softwareArchitectIds.map(sa => sa.value),
            dependencyIds: updatedTask.dependencies.map(d => d.value),
        };

        try {
            await updateTask(taskData.taskID, taskData);
            setTasks(prevTasks =>
                prevTasks.map(task =>
                    task.taskID === taskData.taskID ? { ...task, ...taskData } : task
                )
            );
            setModalIsOpen(false);
            toast.success('Görev başarıyla güncellendi!');
        } catch (error) {
            toast.error('Görev güncellenemedi.');
            console.error(error);
        }
    };

    const openConfirmDeleteModal = () => {
        setConfirmationInput('');
        setConfirmModalIsOpen(true);
    };

    const confirmDeleteTask = async () => {
        if (confirmationInput !== selectedTask.taskName) {
            toast.error('Yanlış görev adı girdiniz. Lütfen tekrar deneyin.');
            return;
        }

        try {
            // Mark the task as deleted in the database
            const updatedTaskData = { ...selectedTask, deleted: true };
            await updateTask(selectedTask.taskID, updatedTaskData);

            // Remove the task from the list in the frontend
            setTasks(prevTasks => prevTasks.filter(task => task.taskID !== selectedTask.taskID));

            // Clear the selectedTask state
            setSelectedTask(null);
            toast.success('Görev başarıyla silindi!');
        } catch (error) {
            toast.error('Görev silinemedi.');
            console.error(error);
        }

        setConfirmModalIsOpen(false);
    };

    const formatDate = (date) => {
        if (!date) return '';
        const d = new Date(date);
        const month = ('0' + (d.getMonth() + 1)).slice(-2);
        const day = ('0' + d.getDate()).slice(-2);
        return `${d.getFullYear()}-${month}-${day}`;
    };

    const severityColors = {
        0: '#d9edf7', // None (default)
        1: '#5bc0de', // Low
        2: '#f0ad4e', // Moderate
        3: '#d9534f', // High
        4: '#d43f3a'  // Extreme
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
            { type: "string", label: "Color" },
        ],
        ...tasks.map((task) => [
            task.taskID.toString(),
            task.taskName,
            null,
            new Date(task.startDate),
            new Date(task.endDate),
            null,
            task.progress,
            Array.isArray(task.dependencyIds) ? task.dependencyIds.join(', ') : '',
            severityColors[task.severity]
        ]),
    ];

    const today = new Date();

    const options = {
        height: 350,
        gantt: {
            trackHeight: 60,
            criticalPathEnabled: true,
            criticalPathStyle: {
                stroke: "#e64a19",
                strokeWidth: 5,
            },
            arrow: {
                angle: 999,
                width: 1,
                color: "red",
                radius: 999,
            },
            innerGridTrack: { fill: "#e3f2fd" },
            innerGridDarkTrack: { fill: "#bbdefb" },
            innerGridHorizLine: {
                stroke: "#90caf9",
                strokeWidth: 5,
            },
            todayMarker: {
                color: '#ff0000', // Bugünü gösteren çizgi için kırmızı renk
                thickness: 1, // Çizginin kalınlığı
            },
        },
        hAxis: {
            format: 'MMM d, yyyy',
            gridlines: { count: 15 }
        }
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

            {/* Gantt Chart Section */}
            <div className="chart-wrapper">
                <Chart
                    chartType="Gantt"
                    width="100%"
                    height="350px"
                    data={data}
                    options={options}
                />
            </div>

            {/* Task Editor Section */}
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

                {!selectedTask && (
                    <button className="redirect-button" onClick={handleButtonClick}>
                        Yeni Görev Ekle
                    </button>
                )}
                {selectedTask && (
                    <div className="task-action-buttons">
                        <button onClick={() => setModalIsOpen(true)} className="task-button">
                            Düzenle
                        </button>
                        <button onClick={openConfirmDeleteModal} className="task-button delete-button">
                            Sil
                        </button>
                    </div>
                )}
            </div>

            {/* Modals for Editing and Deleting Tasks */}
            <Modal
                isOpen={modalIsOpen}
                onRequestClose={() => setModalIsOpen(false)}
                className="small-modal"
                overlayClassName="overlay"
            >
                <h2 className="modal-header">Görev Düzenleme</h2>
                <div className="modal-body">
                    {/* Form Inputs */}
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
                        value={formatDate(updatedTask.startDate)}
                        onChange={handleInputChange}
                    />
                    <input
                        type="date"
                        name="endDate"
                        value={formatDate(updatedTask.endDate)}
                        onChange={handleInputChange}
                    />
                    <input
                        type="number"
                        name="analysisDuration"
                        value={updatedTask.analysisDuration || ''}
                        onChange={handleInputChange}
                        placeholder="Analiz Süresi"
                    />
                    <input
                        type="number"
                        name="developmentDuration"
                        value={updatedTask.developmentDuration || ''}
                        onChange={handleInputChange}
                        placeholder="Geliştirme Süresi"
                    />
                    <Select
                        name="severity"
                        options={severityOptions}
                        value={updatedTask.severity}
                        onChange={(selected) => handleSelectChange(selected, 'severity')}
                        placeholder="Önem derecesini seçin"
                    />
                    <input
                        type="number"
                        name="cost"
                        value={updatedTask.cost || ''}
                        onChange={handleInputChange}
                        placeholder="Maliyet"
                    />
                    <Select
                        isMulti
                        name="analystIds"
                        options={users.map(user => ({ value: user.userID, label: user.userName }))}
                        value={updatedTask.analystIds}
                        onChange={(selected) => handleSelectChange(selected, 'analystIds')}
                        placeholder="Analistleri Seçin"
                    />
                    <Select
                        isMulti
                        name="solutionArchitectIds"
                        options={users.map(user => ({ value: user.userID, label: user.userName }))}
                        value={updatedTask.solutionArchitectIds}
                        onChange={(selected) => handleSelectChange(selected, 'solutionArchitectIds')}
                        placeholder="Çözüm Mimarlarını Seçin"
                    />
                    <Select
                        isMulti
                        name="softwareArchitectIds"
                        options={users.map(user => ({ value: user.userID, label: user.userName }))}
                        value={updatedTask.softwareArchitectIds}
                        onChange={(selected) => handleSelectChange(selected, 'softwareArchitectIds')}
                        placeholder="Yazılım Mimarlarını Seçin"
                    />
                    <Select
                        isMulti
                        name="dependencies"
                        options={tasks.map(task => ({ value: task.taskID, label: task.taskName }))}
                        value={updatedTask.dependencies}
                        onChange={(selected) => handleSelectChange(selected, 'dependencies')}
                        placeholder="Bağlı Taskları Seçin"
                    />
                    <button className="save-button" onClick={saveTask}>
                        Kaydet
                    </button>
                </div>
            </Modal>

            <Modal
                isOpen={confirmModalIsOpen}
                onRequestClose={() => setConfirmModalIsOpen(false)}
                className="small-modal"
                overlayClassName="overlay"
            >
                <h2 className="modal-header">Görevi Sil</h2>
                <div className="modal-body">
                    <p>Bu görevi silmek istediğinizden emin misiniz? Onaylamak için görev adını yazın:</p>
                    <input
                        type="text"
                        value={confirmationInput}
                        onChange={(e) => setConfirmationInput(e.target.value)}
                        placeholder="Görev Adı"
                    />
                    <div className="delete-confirm-buttons">
                        <button className="save-button delete-button" onClick={confirmDeleteTask}>
                            Sil
                        </button>
                        <button className="close-button" onClick={() => setConfirmModalIsOpen(false)}>İptal</button>
                    </div>
                </div>
            </Modal>

            <ToastContainer />
        </div>
    );
}

export default ProjectTask;
