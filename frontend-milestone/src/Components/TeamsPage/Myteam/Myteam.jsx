import React, { useEffect, useState } from 'react';
import './myteam.css';

const Myteam = () => {
    const [teams, setTeams] = useState([]);

    useEffect(() => {
        fetch('http://localhost:3307/team/api/v1/list')
            .then(response => response.json())
            .then(data => {
                const teamDetailsPromises = data.map(team =>
                    fetch(`http://localhost:3307/team/api/v1/team-with-members/${team.teamID}`)
                        .then(response => response.json())
                );

                Promise.all(teamDetailsPromises)
                    .then(setTeams)
                    .catch(error => console.error('Error fetching team details:', error));
            })
            .catch(error => console.error('Error fetching the team list:', error));
    }, []);

    if (teams.length === 0) return <div>Loading...</div>;

    const myTeam = teams[0];

    return (
        <div className="teams-container">
            <div className="left-side">
                <div className="myteam">
                    <div className="title-bar">
                        <div className="title">EKİBİM</div>
                    </div>
                    <div className="team-leader">
                        <div className="team-leader-name">{myTeam.teamName}</div>
                        <div className="team-leader-special-bar">{myTeam.description}</div>
                    </div>
                    <div className="team-members-wrapper">
                        <div className="team-member-title-wrapper">
                            <div className="team-member-title">Ekip Üyeleri</div>
                        </div>
                        {myTeam.members.map((member, index) => (
                            <div key={index} className="team-member">
                                <div className="team-member-name">{member.userName}</div>
                                <div className="team-member-special">{member.roles.join(', ')}</div>
                            </div>
                        ))}
                    </div>
                </div>
            </div>

            <div className="right-side">
                {teams.slice(1).map((team, index) => (
                    <div key={index} className="team">
                        <div className="team-title-wrapper">
                            <div className="team-title">{team.teamName}</div>
                        </div>
                        <div className="team-project">{team.description}</div>
                        <div className="team-leader">Lider: {team.teamLeader}</div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default Myteam;
