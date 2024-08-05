import React, { useState } from 'react';
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

    const handleSave = () => {
        console.log('Task kaydedildi:', { taskName, analysts, solutionArchitects, softwareArchitects, analysisDuration, developmentDuration, startDate, deliveryDate });
    };

    const analystOptions = [
        { value: 'Analyst 1', label: 'Analist 1' },
        { value: 'Analyst 2', label: 'Analist 2' },
        { value: 'Analyst 3', label: 'Analist 3' }
    ];

    const solutionArchitectOptions = [
        { value: 'Solution Architect 1', label: 'Çözüm Mimarı 1' },
        { value: 'Solution Architect 2', label: 'Çözüm Mimarı 2' }
    ];

    const softwareArchitectOptions = [
        { value: 'Software Architect 1', label: 'Yazılım Mimarı 1' },
        { value: 'Software Architect 2', label: 'Yazılım Mimarı 2' }
    ];

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
