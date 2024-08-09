import React, { useState, useEffect } from 'react';
import './myteam.css';

const Myteam = ({ teamId }) => {
    const [teamLeader, setTeamLeader] = useState(null);
    const [teamMembers, setTeamMembers] = useState([]);

    const authToken = 'your-authentication-token'; // Replace with your actual token

    useEffect(() => {
        if (!teamId) return;

        // Fetch the team leader data by teamId
        const fetchTeamLeader = async () => {
            try {
                const response = await fetch(`http://localhost:3307/user/api/v1/role/TEAM_LEADER?teamId=${teamId}`, {
                    headers: {
                        'Authorization': `Bearer ${authToken}`
                    }
                });
                const data = await response.json();
                if (data.length > 0) {
                    setTeamLeader(data[0]); // Assuming only one team leader per team
                }
            } catch (error) {
                console.error('Error fetching team leader:', error);
            }
        };

        // Fetch the team members data by teamId
        const fetchTeamMembers = async () => {
            try {
                const response = await fetch(`http://localhost:3307/user/api/v1/role/TEAM_MEMBER?teamId=${teamId}`, {
                    headers: {
                        'Authorization': `Bearer ${authToken}`
                    }
                });
                const data = await response.json();
                setTeamMembers(data);
            } catch (error) {
                console.error('Error fetching team members:', error);
            }
        };

        fetchTeamLeader();
        fetchTeamMembers();
    }, [teamId]);

    return (
        <div className='myteam'>
            <div className='title-bar'>
                <div className='title'>EKİBİM</div>
            </div>
            {teamLeader && (
                <div className='team-leader'>
                    <div className='team-leader-title-wrapper'>
                        <div className='team-leader-title'>Ekip Lideri:</div>
                        <div className='team-leader-special-title'>Alanı:</div>
                        <div className='team-leader-name'>{teamLeader.userName}</div>
                        <div className='team-leader-special-bar'>
                            <div className='team-leader-special'>{teamLeader.specialty}</div>
                        </div>
                    </div>
                </div>
            )}
            <div className='team-members'>
                <div className='team-members-wrapper'>
                    <div className='team-member-title-wrapper'>
                        <div className='team-member-title'>Ekip Üyesi:</div>
                        <div className='team-member-specialty-title'>Alanı</div>
                    </div>
                    <div className='team-member-wrapper'>
                        {teamMembers.map((member, index) => (
                            <div key={index} className='team-member'>
                                <div className='team-member-name'>{member.userName}</div>
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
