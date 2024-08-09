import React, { useState, useEffect } from 'react';
import Select from 'react-select';
import './TaskForm.css';

const TaskForm = () => {
    const [taskName, setTaskName] = useState('');
    const [analysts, setAnalysts] = useState([]);
    const [solutionArchitects, setSolutionArchitects] = useState([]);
    const [softwareArchitects, setSoftwareArchitects] = useState([]);
    const [analysisDuration, setAnalysisDuration] = useState('');
    const [developmentDuration, setDevelopmentDuration] = useState('');
    const [startDate, setStartDate] = useState('');
    const [deliveryDate, setDeliveryDate] = useState('');

    const [analystOptions, setAnalystOptions] = useState([]);
    const [solutionArchitectOptions, setSolutionArchitectOptions] = useState([]);
    const [softwareArchitectOptions, setSoftwareArchitectOptions] = useState([]);

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

    const handleSave = async () => {
        const taskData = {
            taskName,
            analysts: analysts.map(a => a.value),
            solutionArchitects: solutionArchitects.map(sa => sa.value),
            softwareArchitects: softwareArchitects.map(sa => sa.value),
            analysisDuration: parseInt(analysisDuration, 10),
            developmentDuration: parseInt(developmentDuration, 10),
            startDate,
            deliveryDate
        };

        console.log('Sending task data:', taskData);

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
                console.log('Task successfully saved:', result);
            } else {
                console.error('Failed to save task:', response.status, response.statusText);
                const errorText = await response.text(); // to capture the server error message
                console.error('Server error:', errorText);
            }
        } catch (error) {
            console.error('Error saving task:', error);
        }
    };

    return (
        <div className="task-form-container">
            <div className="task-form-header">
                <h1>Proje A</h1>
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
                            type="text"
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
                            type="text"
                            value={developmentDuration}
                            onChange={(e) => setDevelopmentDuration(e.target.value)}
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
        </div>
    );
};

export default TaskForm;
