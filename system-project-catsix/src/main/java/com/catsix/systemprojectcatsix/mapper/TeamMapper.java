package com.catsix.systemprojectcatsix.mapper;

import com.catsix.systemprojectcatsix.model.Client;
import com.catsix.systemprojectcatsix.model.File;
import com.catsix.systemprojectcatsix.model.Schedule;
import com.catsix.systemprojectcatsix.model.Team;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.util.List;

@Mapper
public interface TeamMapper {
    void createTeam(@Param("team_ID") String team_ID, @Param("team_name") String team_name);
    void deleteTeam(@Param("team_ID") String team_ID);
    void inviteTeam(@Param("client_ID") String client_ID, @Param("team_ID") String team_ID);
    List<Team> viewTeams(@Param("client_ID") String client_ID);
    List<Schedule> viewSchedules(@Param("team_ID") String team_ID);
    void setSchedule(@Param("schedule_ID") String schedule_ID,
                     @Param("schedule_start_date") String schedule_start_date,
                     @Param("schedule_end_date") String schedule_end_date,
                     @Param("schedule_contents") String schedule_contents,
                     @Param("schedule_team_ID") String schedule_team_ID);
    void deleteSchedule(@Param("schedule_ID") String schedule_ID);
    void registFile(@Param("file_name") String file_name, @Param("file_team_ID") String file_team_ID);
    List<File> viewFiles(@Param("file_team_ID") String file_team_ID);
    List<Client> viewParticipateClients(@Param("team_ID") String team_ID);
}