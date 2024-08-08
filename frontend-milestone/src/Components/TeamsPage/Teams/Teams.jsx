import React from 'react';
import './teams.css';
import { teamData } from "./TeamData";

const Teams = () => {
    return (
        <div className='teams'>
            {teamData.map((team, index) => (
                <div key={index} className='teams-item'>
                    <div className='teams-rectangle'>
                        <div className='teams-name'>{team.name}</div>
                        <div className='teams-project'>{team.project}</div>
                        <div className='teams-leader'>Takım Lideri: {team.leader}</div>
                    </div>
                </div>
            ))}
        </div>
    );
};

export default Teams;