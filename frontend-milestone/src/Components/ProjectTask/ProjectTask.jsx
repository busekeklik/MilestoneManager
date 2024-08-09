import React, { useState, useEffect } from "react";
import './projecttask.css';
import { Gantt, ViewMode } from "gantt-task-react";
import { fetchTasks, getStartEndDateForProject } from "./Data/Data";
import { ViewSwitcher } from "./ViewSwitcher/ViewSwitcher";
import Button from "./Button/Button";
import TaskFormModal from "./TaskFormModal/TaskFormModal";
import Modal from "react-modal";
import { useNavigate } from 'react-router-dom';

Modal.setAppElement("#root");

const ProjectTask = () => {
    const [view, setView] = useState(ViewMode.Day);
    const [tasks, setTasks] = useState([]);
    const [isChecked, setIsChecked] = useState(true);

    const [newTaskName, setNewTaskName] = useState("");
    const [newTaskStart, setNewTaskStart] = useState("");
    const [newTaskEnd, setNewTaskEnd] = useState("");
    const [isFormVisible, setIsFormVisible] = useState(false);

    const navigate = useNavigate();

    useEffect(() => {
        const loadTasks = async () => {
            const taskData = await fetchTasks();
            if (taskData.every(task => task.start && task.end)) {
                setTasks(taskData);
            } else {
                console.error("Some tasks are missing start or end dates", taskData);
            }
        };

        loadTasks();
    }, []);

    let columnWidth = 60;
    if (view === ViewMode.Month) {
        columnWidth = 300;
    } else if (view === ViewMode.Week) {
        columnWidth = 250;
    }

    const handleTaskChange = (task) => {
        if (!task || !task.start || !task.end) {
            console.error("Task is missing start or end dates:", task);
            return;
        }

        let newTasks = tasks.map((t) => (t.id === task.id ? task : t));

        if (task.project) {
            const [start, end] = getStartEndDateForProject(newTasks, task.project);
            const projectIndex = newTasks.findIndex((t) => t.id === task.project);
            const project = newTasks[projectIndex];

            if (project && (project.start.getTime() !== start.getTime() || project.end.getTime() !== end.getTime())) {
                const changedProject = { ...project, start, end };
                newTasks[projectIndex] = changedProject;
            }
        }

        setTasks(newTasks);
    };

    const handleTaskDelete = (task) => {
        const conf = window.confirm("Are you sure about " + task.name + " ?");
        if (conf) {
            setTasks(tasks.filter((t) => t.id !== task.id));
        }
        return conf;
    };

    const handleProgressChange = (task) => {
        setTasks(tasks.map((t) => (t.id === task.id ? task : t)));
    };

    const handleDblClick = (task) => {
        alert("On Double Click event Id:" + task.id);
    };

    const handleSelect = (task, isSelected) => {
        console.log(task.name + " has " + (isSelected ? "selected" : "unselected"));
    };

    const handleExpanderClick = (task) => {
        setTasks(tasks.map((t) => (t.id === task.id ? task : t)));
    };

    const addNewTask = () => {
        if (!newTaskName || !newTaskStart || !newTaskEnd) {
            console.error("New task is missing required information");
            return;
        }

        const newTask = {
            start: new Date(newTaskStart),
            end: new Date(newTaskEnd),
            name: newTaskName,
            id: "Task " + tasks.length,
            progress: 0,
            type: "task",
        };
        setTasks([...tasks, newTask]);
        setIsFormVisible(false);
    };

    return (
        <div>
            <div className='tasks-title-wrapper'>
                <div className='tasks-title-bar'>
                    <div className='tasks-title'>Görevler</div>
                </div>
            </div>
            <ViewSwitcher
                onViewModeChange={setView}
                onViewListChange={setIsChecked}
                isChecked={isChecked}
            />
            <Gantt
                tasks={tasks}
                viewMode={view}
                onDateChange={handleTaskChange}
                onDelete={handleTaskDelete}
                onProgressChange={handleProgressChange}
                onDoubleClick={handleDblClick}
                onSelect={handleSelect}
                onExpanderClick={handleExpanderClick}
                listCellWidth={isChecked ? "155px" : ""}
                columnWidth={columnWidth}
            />
            <Button onClick={() => navigate('/taskform')}>Add New Task</Button>
            <TaskFormModal
                isOpen={isFormVisible}
                onRequestClose={() => setIsFormVisible(false)}
                newTaskName={newTaskName}
                setNewTaskName={setNewTaskName}
                newTaskStart={newTaskStart}
                setNewTaskStart={setNewTaskStart}
                newTaskEnd={newTaskEnd}
                setNewTaskEnd={setNewTaskEnd}
                addNewTask={addNewTask}
            />
        </div>
    );
};

export default ProjectTask;
