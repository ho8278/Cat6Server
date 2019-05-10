package com.catsix.systemprojectcatsix.service;

import com.catsix.systemprojectcatsix.mapper.TeamMapper;
import com.catsix.systemprojectcatsix.model.ChatRoom;
import com.catsix.systemprojectcatsix.model.Schedule;
import com.catsix.systemprojectcatsix.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class TeamService {
    @Autowired
    TeamMapper teamMapper;
    

    public void createTeam(String team_ID, String team_name) {
        teamMapper.createTeam(team_ID, team_name);
    }
    public void deleteTeam(String team_ID) { teamMapper.deleteTeam(team_ID); }
    public void inviteTeam(String client_ID, String team_ID) { teamMapper.inviteTeam(client_ID, team_ID); }
    public List<Team> viewTeams(String client_ID) { return teamMapper.viewTeams(client_ID); }
    public List<Schedule> viewSchedules(String team_ID) { return teamMapper.viewSchedules(team_ID); }
    public void setSchedule(String schedule_ID,
                            Date schedule_start_date,
                            Date schedule_end_date,
                            String schedule_contents,
                            String schedule_team_ID)
    {
        teamMapper.setSchedule(schedule_ID, schedule_start_date, schedule_end_date, schedule_contents, schedule_team_ID);
    }
    public void deleteSchedule(String schedule_ID) { teamMapper.deleteSchedule(schedule_ID); }
}
