import React from "react";
import Modal from "react-modal";
import Button from "../Button/Button";
import "./taskFormModal.css";

const TaskFormModal = ({
                           isOpen,
                           onRequestClose,
                           newTaskName,
                           setNewTaskName,
                           newTaskStart,
                           setNewTaskStart,
                           newTaskEnd,
                           setNewTaskEnd,
                           addNewTask
                       }) => {
    return (
        <Modal isOpen={isOpen} onRequestClose={onRequestClose} className="modal">
            <h3>Add New Task</h3>
            <input
                type="text"
                placeholder="Task Name"
                value={newTaskName}
                onChange={(e) => setNewTaskName(e.target.value)}
            />
            <input
                type="date"
                placeholder="Start Date"
                value={newTaskStart}
                onChange={(e) => setNewTaskStart(e.target.value)}
            />
            <input
                type="date"
                placeholder="End Date"
                value={newTaskEnd}
                onChange={(e) => setNewTaskEnd(e.target.value)}
            />
            <Button onClick={addNewTask}>Add Task</Button>
            <Button onClick={onRequestClose}>Close</Button>
        </Modal>
    );
};

export default TaskFormModal;
