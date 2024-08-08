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
        // Fetch analyst options
        fetch('https://your-api-endpoint.com/analysts')
            .then(response => response.json())
            .then(data => setAnalystOptions(data))
            .catch(error => console.error('Error fetching analysts:', error));

        // Fetch solution architect options
        fetch('https://your-api-endpoint.com/solution-architects')
            .then(response => response.json())
            .then(data => setSolutionArchitectOptions(data))
            .catch(error => console.error('Error fetching solution architects:', error));

        // Fetch software architect options
        fetch('https://your-api-endpoint.com/software-architects')
            .then(response => response.json())
            .then(data => setSoftwareArchitectOptions(data))
            .catch(error => console.error('Error fetching software architects:', error));
    }, []);

    const handleSave = async () => {
        const taskData = {
            taskName,
            analysts: analysts.map(a => a.value),
            solutionArchitects: solutionArchitects.map(sa => sa.value),
            softwareArchitects: softwareArchitects.map(sa => sa.value),
            analysisDuration,
            developmentDuration,
            startDate,
            deliveryDate
        };

        try {
            const response = await fetch('https://your-api-endpoint.com/tasks', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(taskData)
            });

            if (response.ok) {
                const result = await response.json();
                console.log('Task successfully saved:', result);
                // Optionally, you can reset the form or show a success message here
            } else {
                console.error('Failed to save task:', response.statusText);
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
