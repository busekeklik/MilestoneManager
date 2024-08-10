import React, { useState } from "react";
import './projecttask.css';
import { Gantt, ViewMode } from "gantt-task-react";
import { initTasks, getStartEndDateForProject } from "./Data/Data";
import { ViewSwitcher } from "./ViewSwitcher/ViewSwitcher";
import Button from "./Button/Button";
import TaskFormModal from "./TaskFormModal/TaskFormModal";
import Modal from "react-modal";
import { useLocation, useNavigate } from "react-router-dom";

Modal.setAppElement("#root");

const ProjectTask = () => {
    const [view, setView] = useState(ViewMode.Day);
    const [tasks, setTasks] = useState(initTasks());
    const [isChecked, setIsChecked] = useState(true);
    const [newTaskName, setNewTaskName] = useState("");
    const [newTaskStart, setNewTaskStart] = useState("");
    const [newTaskEnd, setNewTaskEnd] = useState("");
    const [isFormVisible, setIsFormVisible] = useState(false);

    const navigate = useNavigate();
    const location = useLocation();
    const { projectName } = location.state || {};  // Get projectName from state

    let columnWidth = 60;
    if (view === ViewMode.Month) {
        columnWidth = 300;
    } else if (view === ViewMode.Week) {
        columnWidth = 250;
    }

    const handleTaskChange = (task) => {
        let newTasks = tasks.map((t) => (t.id === task.id ? task : t));
        if (task.project) {
            const [start, end] = getStartEndDateForProject(newTasks, task.project);
            const project = newTasks.find((t) => t.id === task.project);
            if (
                project.start.getTime() !== start.getTime() ||
                project.end.getTime() !== end.getTime()
            ) {
                const changedProject = { ...project, start, end };
                newTasks = newTasks.map((t) =>
                    t.id === task.project ? changedProject : t
                );
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
                    <div className='tasks-title'>{projectName}</div> {/* Display projectName */}
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
            <Button onClick={() => navigate('/taskform', { state: { projectName } })}>
                Add New Task
            </Button> {/* Pass projectName when navigating */}
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
