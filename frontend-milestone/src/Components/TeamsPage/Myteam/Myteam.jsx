import React, { useEffect, useState } from 'react';
import './myteam.css';

const Myteam = () => {
    const [teams, setTeams] = useState([]);

    useEffect(() => {
        // Fetch all teams
        fetch('http://localhost:3307/team/api/v1/list')
            .then(response => response.json())
            .then(data => {
                const teamDetailsPromises = data.map(team =>
                    fetch(`http://localhost:3307/team/api/v1/team-with-members/${team.teamID}`)
                        .then(response => response.json())
                );

                // Wait for all team details to be fetched
                Promise.all(teamDetailsPromises)
                    .then(setTeams)
                    .catch(error => console.error('Error fetching team details:', error));
            })
            .catch(error => console.error('Error fetching the team list:', error));
    }, []);

    if (teams.length === 0) return <div>Loading...</div>;

    const myTeam = teams[0]; // Assuming the first team is your own team

    return (
        <div className="teams-container">
            <div className="myteam">
                <div className="title-bar">
                    <div className="title">EKİBİM</div>
                </div>
                <div className="team-info-wrapper">
                    <div className="team-info-title">Ekip Lideri:</div>
                    <div className="team-info-content">{myTeam.teamName}</div>
                </div>
                <div className="team-info-wrapper">
                    <div className="team-info-title">Alanı:</div>
                    <div className="team-special-content">{myTeam.description}</div>
                </div>
                <div className="team-info-wrapper">
                    <div className="team-info-title">Ekip Üyeleri:</div>
                    <div className="team-info-content"></div>
                </div>
                {myTeam.members.map((member, index) => (
                    <div key={index} className="team-info-wrapper">
                        <div className="team-info-content">{member.userName}</div>
                        <div className="team-special-content">{member.roles.join(', ')}</div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default Myteam;
