import React, { useEffect, useState } from 'react';
import './myteam.css';

const Myteam = () => {
    const [teams, setTeams] = useState([]);

    useEffect(() => {
        // Fetch all teams
        fetch('http://localhost:3307/team/api/v1/list') // Replace with your actual API endpoint for listing teams
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
    const otherTeams = teams.slice(1); // The rest are the other teams

    return (
        <div className="teams-container">
            <div className="left-side">
                <div className="myteam">
                    <div className="title-bar">
                        <div className="title">EKİBİM</div>
                    </div>
                    <div className="team-leader">
                        <div className="team-leader-title-wrapper">
                            <div className="team-leader-title">Ekip Lideri:</div>
                            <div className="team-leader-special-title">Alanı:</div>
                            <div className="team-leader-name">{myTeam.teamName}</div>
                            <div className="team-leader-special-bar">
                                <div className="team-leader-special">{myTeam.description}</div>
                            </div>
                        </div>
                    </div>
                    <div className="team-members">
                        <div className="team-members-wrapper">
                            <div className="team-member-title-wrapper">
                                <div className="team-member-title">Ekip Üyesi:</div>
                                <div className="team-member-specialty-title">Alanı</div>
                            </div>
                            <div className="team-member-wrapper">
                                {myTeam.members.map((member, index) => (
                                    <div key={index} className="team-member">
                                        <div className="team-member-name">{member.userName}</div>
                                        <div className="team-member-special">
                                            {member.roles.map(role => role).join(', ')}
                                        </div>
                                    </div>
                                ))}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div className="right-side">
                <div className="other-teams">
                    {otherTeams.map((team, teamIndex) => (
                        <div key={teamIndex} className="team">
                            <div className="team-title">{team.teamName}</div>
                            <div className="team-project">{team.description}</div>
                            <div className="team-leader">
                                Takım Lideri: {team.teamName}
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
};

export default Myteam;
