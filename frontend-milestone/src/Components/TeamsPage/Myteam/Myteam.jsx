import React from 'react';
import './myteam.css';
import { teamLeader, teamMembers } from './Data';

const Myteam = () => {
    return (
        <div className='myteam'>
            <div className='title-bar'>
                <div className='title'>EKİBİM</div>
            </div>
            <div className='team-leader'>
                <div className='team-leader-title-wrapper'>
                    <div className='team-leader-title'>Ekip Lideri:</div>
                    <div className='team-leader-special-title'>Alanı:</div>
                    <div className='team-leader-name'>{teamLeader.name}</div>
                    <div className='team-leader-special-bar'>
                        <div className='team-leader-special'>{teamLeader.specialty}</div>
                    </div>
                </div>
            </div>
            <div className='team-members'>
                <div className='team-members-wrapper'>
                <div className='team-member-title-wrapper'>
                    <div className='team-member-title'>Ekip Üyesi:</div>
                    <div className='team-member-specialty-title'>Alanı</div>
                </div>
                <div className='team-member-wrapper'>
                {teamMembers.map((member, index) => (
                    <div key={index} className='team-member'>
                        <div className='team-member-name'>{member.name}</div>
                        <div className='team-member-special'>{member.specialty}</div>
                    </div>
                ))}
                </div>
            </div>
            </div>
        </div>
    );
};

export default Myteam;
