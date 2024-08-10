import React, { useState, useEffect } from 'react';
import Select from 'react-select';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './TaskForm.css';
import { useLocation } from 'react-router-dom';  // Import useLocation to access the state

const TaskForm = () => {
    const location = useLocation();  // Use useLocation hook to get the state
    const { projectName } = location.state || {};  // Extract projectName from state

    const [taskName, setTaskName] = useState('');
    const [analysts, setAnalysts] = useState([]);
    const [solutionArchitects, setSolutionArchitects] = useState([]);
    const [softwareArchitects, setSoftwareArchitects] = useState([]);
    const [analysisDuration, setAnalysisDuration] = useState('');
    const [developmentDuration, setDevelopmentDuration] = useState('');
    const [startDate, setStartDate] = useState('');
    const [deliveryDate, setDeliveryDate] = useState('');
    const [severity, setSeverity] = useState(null);
    const [cost, setCost] = useState('');

    const [analystOptions, setAnalystOptions] = useState([]);
    const [solutionArchitectOptions, setSolutionArchitectOptions] = useState([]);
    const [softwareArchitectOptions, setSoftwareArchitectOptions] = useState([]);

    const defaultProjectId = 1;

    const severityOptions = [
        { value: 0, label: 'None (0)' },
        { value: 1, label: 'Low (1)' },
        { value: 2, label: 'Moderate (2)' },
        { value: 3, label: 'High (3)' },
        { value: 4, label: 'Extreme (4)' }
    ];

    useEffect(() => {
        const fetchRoleUsers = async (role) => {
            try {
                const response = await fetch(`http://localhost:3307/user/api/v1/role/${role}`);
                const data = await response.json();
                return data.map(user => ({ value: user.userID, label: user.userName }));
            } catch (error) {
                console.error(`Error fetching ${role} users:`, error);
                return [];
            }
        };

        fetchRoleUsers('ANALYST').then(data => setAnalystOptions(data));
        fetchRoleUsers('SOLUTION_ARCHITECT').then(data => setSolutionArchitectOptions(data));
        fetchRoleUsers('SOFTWARE_ARCHITECT').then(data => setSoftwareArchitectOptions(data));
    }, []);

    const validateForm = () => {
        if (severity === null) {
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
        if (new Date(startDate) > new Date(deliveryDate)) {
            alert('Başlangıç tarihi bitiş tarihinden sonra olamaz.');
            return false;
        }
        return true;
    };

    const handleSave = async () => {
        if (!validateForm()) {
            return;
        }

        const taskData = {
            taskName,
            startDate,
            endDate: deliveryDate,
            manDays: parseInt(analysisDuration) + parseInt(developmentDuration),
            cost: parseInt(cost),
            severity: severity.value,
            progress: 0,
            projectId: defaultProjectId,
            analysts: analysts.map(a => a.value),
            solutionArchitects: solutionArchitects.map(sa => sa.value),
            softwareArchitects: softwareArchitects.map(sa => sa.value),
        };

        try {
            const response = await fetch('http://localhost:3307/task/api/v1/create', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(taskData)
            });

            if (response.ok) {
                const result = await response.json();
                toast.success('Task oluşturuldu!', {
                    className: 'toast-success',
                    bodyClassName: 'toast-body',
                    progressClassName: 'toast-progress',
                });
                console.log('Task successfully saved:', result);
            } else {
                toast.error('Task kaydedilemedi!', {
                    className: 'toast-error',
                    bodyClassName: 'toast-body',
                    progressClassName: 'toast-progress',
                });
                console.error('Failed to save task:', response.statusText);
            }
        } catch (error) {
            toast.error('Task kaydedilirken hata oluştu!', {
                className: 'toast-error',
                bodyClassName: 'toast-body',
                progressClassName: 'toast-progress',
            });
            console.error('Error saving task:', error);
        }
    };

    return (
        <div className="task-form-container">
            <div className="task-form-header">
                <h1>{projectName || 'Proje Adı'}</h1> {/* Display dynamic project name */}
            </div>
            <div className="task-form">
                <form>
                    <div className="form-group">
                        <label>Taskın Adı:</label>
                        <input
                            type="text"
                            value={taskName}
                            onChange={(e) => setTaskName(e.target.value)}
                        />
                    </div>
                    <div className="form-group">
                        <label>Analistler:</label>
                        <Select
                            isMulti
                            options={analystOptions}
                            value={analysts}
                            onChange={setAnalysts}
                            styles={{
                                control: (base) => ({
                                    ...base,
                                    borderRadius: '20px',
                                    borderColor: '#ccc',
                                    padding: '5px'
                                })
                            }}
                            placeholder=""
                        />
                    </div>
                    <div className="form-group">
                        <label>Çözüm Mimarı:</label>
                        <Select
                            isMulti
                            options={solutionArchitectOptions}
                            value={solutionArchitects}
                            onChange={setSolutionArchitects}
                            styles={{
                                control: (base) => ({
                                    ...base,
                                    borderRadius: '20px',
                                    borderColor: '#ccc',
                                    padding: '5px'
                                })
                            }}
                            placeholder=""
                        />
                    </div>
                    <div className="form-group">
                        <label>Yazılım Mimarı:</label>
                        <Select
                            isMulti
                            options={softwareArchitectOptions}
                            value={softwareArchitects}
                            onChange={setSoftwareArchitects}
                            styles={{
                                control: (base) => ({
                                    ...base,
                                    borderRadius: '20px',
                                    borderColor: '#ccc',
                                    padding: '5px'
                                })
                            }}
                            placeholder=""
                        />
                    </div>
                    <div className="form-group">
                        <label>Analiz Süresi:</label>
                        <input
                            type="number"
                            value={analysisDuration}
                            onChange={(e) => setAnalysisDuration(e.target.value)}
                        />
                    </div>
                    <div className="form-group">
                        <label>Task Başlangıç Tarihi:</label>
                        <input
                            type="date"
                            value={startDate}
                            onChange={(e) => setStartDate(e.target.value)}
                        />
                    </div>
                    <div className="form-group">
                        <label>Geliştirme Süresi:</label>
                        <input
                            type="number"
                            value={developmentDuration}
                            onChange={(e) => setDevelopmentDuration(e.target.value)}
                        />
                    </div>
                    <div className="form-group">
                        <label>Önem Derecesi:</label>
                        <Select
                            options={severityOptions}
                            value={severity}
                            onChange={setSeverity}
                            styles={{
                                control: (base) => ({
                                    ...base,
                                    borderRadius: '20px',
                                    borderColor: '#ccc',
                                    padding: '5px'
                                })
                            }}
                            placeholder="Önem derecesini seçin"
                        />
                    </div>
                    <div className="form-group">
                        <label>Maliyet:</label>
                        <input
                            type="number"
                            value={cost}
                            onChange={(e) => setCost(e.target.value)}
                        />
                    </div>
                    <div className="form-group">
                        <label>Task Teslim Tarihi:</label>
                        <input
                            type="date"
                            value={deliveryDate}
                            onChange={(e) => setDeliveryDate(e.target.value)}
                        />
                    </div>
                    <button type="button" onClick={handleSave}>Kaydet</button>
                </form>
            </div>
            <ToastContainer /> {/* This container will display the toasts */}
        </div>
    );
};

export default TaskForm;
