import React from "react";
import "gantt-task-react/dist/index.css";
import { ViewMode } from "gantt-task-react";
import './viewSwitch.css'
import Button from "../Button/Button";

export const ViewSwitcher = ({ onViewModeChange, onViewListChange, isChecked }) => {
    return (
        <div className="ViewContainer">
            <button
                className="Button"
                onClick={() => onViewModeChange(ViewMode.QuarterDay)}
            >
                Çeyrek Gün
            </button>
            <button
                className="Button"
                onClick={() => onViewModeChange(ViewMode.HalfDay)}
            >
                Yarım Gün
            </button>
            <button className="Button" onClick={() => onViewModeChange(ViewMode.Day)}>
                Gün
            </button>
            <button
                className="Button"
                onClick={() => onViewModeChange(ViewMode.Week)}
            >
                Hafta
            </button>
            <button
                className="Button"
                onClick={() => onViewModeChange(ViewMode.Month)}
            >
                Ay
            </button>

            <div className="Switch">
                <label className="Switch_Toggle">
                    <input
                        type="checkbox"
                        defaultChecked={isChecked}
                        onClick={() => onViewListChange(!isChecked)}
                    />
                    <span className="Slider" />
                </label>
                Görev Listesini Göster
            </div>
        </div>
    );
};
