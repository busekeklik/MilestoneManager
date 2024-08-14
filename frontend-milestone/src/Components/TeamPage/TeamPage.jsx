import React, { useEffect, useState } from 'react';
import { addDays, format, startOfWeek, subDays, isWeekend } from 'date-fns';
import { tr } from 'date-fns/locale';
import './TeamPage.css';

const TeamPage = () => {
    const [startDate, setStartDate] = useState(startOfWeek(new Date(), { weekStartsOn: 1 }));
    const [teams, setTeams] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const [teamResponse, userResponse, absenceResponse, taskResponse] = await Promise.all([
                    fetch('http://localhost:3307/team/api/v1/list'),
                    fetch('http://localhost:3307/user/api/v1/list'),
                    fetch('http://localhost:3307/absence/api/v1/list'),
                    fetch('http://localhost:3307/task/api/v1/list')
                ]);

                const teamsData = await teamResponse.json();
                const usersData = await userResponse.json();
                const absencesData = await absenceResponse.json();
                const tasksData = await taskResponse.json();

                // Map tasks and absences to users
                const teamsWithTasksAndAbsences = teamsData.map(team => {
                    const updatedMembers = team.members.map(member => {
                        const userTasks = tasksData.filter(task => {
                            return task.softwareArchitectIds.includes(member.userID);
                        }).map(task => ({
                            taskName: task.taskName,
                            progress: task.progress
                        }));

                        // Filter absences specifically for this member
                        const userAbsences = absencesData
                            .filter(absence => absence.userId === member.userID) // Ensure absences are specific to the user
                            .map(absence => ({
                                start: new Date(absence.startDate),
                                end: new Date(absence.endDate),
                                status: true
                            }));

                        return {
                            ...member,
                            tasks: userTasks,
                            activeRanges: userAbsences
                        };
                    });

                    return {
                        ...team,
                        members: updatedMembers
                    };
                });

                setTeams(teamsWithTasksAndAbsences);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        fetchData();
    }, [startDate]); // Add startDate as a dependency to refetch data when it changes

    const handlePrevWeek = () => setStartDate(prevDate => subDays(prevDate, 7));
    const handleNextWeek = () => setStartDate(prevDate => addDays(prevDate, 7));

    const renderWeekDaysHeader = () => [...Array(7)].map((_, i) => (
        <th key={i} className={isWeekend(addDays(startDate, i)) ? 'weekend' : ''}>
            {format(addDays(startDate, i), 'EEE, dd MMM', { locale: tr })}
        </th>
    ));

    const renderCustomRowCells = (member) => {
        return [...Array(7)].map((_, i) => {
            const day = addDays(startDate, i);

            // Check if the day is a weekend
            const isDayWeekend = isWeekend(day);

            // Check if the day falls on or within any absence period (including the start date)
            const isDayInAbsenceRange = member.activeRanges && member.activeRanges.some(range => {
                return day >= range.start && day <= range.end;
            });

            // If it's a weekend or within an absence range, mark as Inactive
            const isActive = !isDayWeekend && !isDayInAbsenceRange;

            return (
                <td
                    key={i}
                    className={isDayWeekend ? 'weekend' : ''}
                    style={{ backgroundColor: isActive ? '#5E83C2' : '#F08080' }} // Active: Blue, Inactive: Light Coral
                >
                    {isActive ? 'Active' : 'Inactive'}
                </td>
            );
        });
    };

    return (
        <div className="team-page">
            <div className="team-page-header">
                <h1>EKİBİM</h1>
                <div className="navigation-buttons">
                    <button onClick={handlePrevWeek}>&#8592;</button>
                    <button onClick={handleNextWeek}>&#8594;</button>
                </div>
            </div>
            <table className="team-table">
                <thead>
                <tr>
                    <th>Ekip Üyeleri</th>
                    <th>Rolü</th>
                    <th>Task</th>
                    <th>Task İlerleyiş</th>
                    {renderWeekDaysHeader()}
                </tr>
                </thead>
                <tbody>
                {teams.length > 0 ? teams.map(team =>
                    team.members.map((member, index) => (
                        <tr key={index}>
                            <td>{member.userName}</td>
                            <td>{member.roles.join(', ')}</td>
                            <td>{member.tasks.map(task => task.taskName).join(', ')}</td>
                            <td>{member.tasks.map(task => `${task.progress}%`).join(', ')}</td>
                            {renderCustomRowCells(member)}
                        </tr>
                    ))
                ) : <tr><td colSpan="11">Loading team data...</td></tr>}
                </tbody>
            </table>
        </div>
    );
};

export default TeamPage;
