package com.catsix.systemprojectcatsix.Controller;

import com.catsix.systemprojectcatsix.SystemProjectCatsixApplication;
import com.catsix.systemprojectcatsix.model.ChatRoom;
import com.catsix.systemprojectcatsix.model.Client;
import com.catsix.systemprojectcatsix.model.Schedule;
import com.catsix.systemprojectcatsix.model.Team;
import com.catsix.systemprojectcatsix.service.ChatRoomService;
import com.catsix.systemprojectcatsix.service.ClientService;
import com.catsix.systemprojectcatsix.service.TeamService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

@RestController
public class Controller {
    @Autowired
    private ClientService clientService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private ChatRoomService chatRoomService;

    @RequestMapping("/") // 테스트용
    public String home() {
        return "HOME";
    }

    @RequestMapping("/now") // 테스트용 (DB로부터 현재 시간 요청)
    public String now() throws Exception {
        return clientService.getCurrentDateTime();
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET) // 로그인 (회원ID, 회원 비밀번호 받아서 로그인 성공 여부 반환)
    public String login(@Param("client_ID") String client_ID, @Param("client_password") String client_password, HttpServletRequest request) {
        if(clientService.login(client_ID, client_password) != null) {
            HttpSession session = request.getSession();
            session.setAttribute("client_ID", client_ID);
            System.out.println(client_ID);
            System.out.println(session.getId());
            return "success";
        }
        else {
            return "fail";
        }
    }
    
    @RequestMapping(value = "/showClientInfo", method = RequestMethod.GET) // 회원정보조회 (회원ID 받아서 해당회원정보조회)
    public Client showClientInfo(@Param("client_ID") String client_ID) {
        return clientService.showClientInfo(client_ID);
    }

    @RequestMapping(value = "/createClient", method = RequestMethod.POST) // 회원등록(가입) (가입에 필요한 회원정보 받아서 회원 테이블에 추가)
    public void createClient(@Param("client_ID") String client_ID, @Param("client_password") String client_password, @Param("client_name") String client_name, @Param("client_nickname") String client_nickname, @Param("profile_picture") String profile_picture) {
        clientService.createClient(client_ID, client_password, client_name, client_nickname, profile_picture);
    }

    @RequestMapping(value = "/deleteClient", method = RequestMethod.DELETE) // 회원삭제(탈퇴) (삭제할 회원ID 받아서 해당 회원 회원 테이블에서 삭제)
    public void deleteClient(@Param("client_ID") String client_ID) {
        clientService.deleteClient(client_ID);
    }

    @RequestMapping(value = "/createTeam", method = RequestMethod.POST) // 그룹 생성
    public void createTeam(@Param("team_name") String team_name, HttpServletRequest request) {
        UUID uuid = UUID.randomUUID();
        HttpSession session = request.getSession();
        String c_ID = session.getAttribute("client_ID").toString();
        teamService.createTeam(uuid.toString().replace("-", ""), team_name);
        teamService.inviteTeam(c_ID, uuid.toString().replace("-", ""));
        System.out.println(c_ID);
        System.out.println(uuid.toString());
    }

    @RequestMapping(value = "/deleteTeam", method = RequestMethod.DELETE) // 그룹 삭제
    public void deleteTeam(@Param("team_ID") String team_ID) {
        teamService.deleteTeam(team_ID);
    }

    @RequestMapping(value = "/inviteTeam", method = RequestMethod.POST) // 그룹 초대
    public void inviteTeam(@Param("client_ID") String client_ID, @Param("team_ID") String team_ID) {
        teamService.inviteTeam(client_ID, team_ID);
    }

    @RequestMapping(value = "/inviteTeam/send", method = RequestMethod.POST)
    public void inviteTeam(@Param("team_ID") String team_ID, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String client_ID = session.getAttribute("client_ID").toString();
        teamService.inviteTeam(client_ID, team_ID);
    }

    @RequestMapping(value = "/viewTeams", method = RequestMethod.GET) // 소속 그룹 조회
    public List<Team> viewTeams(@Param("client_ID") String client_ID) {
        return teamService.viewTeams(client_ID);
    }

    @RequestMapping(value = "/createChatRoom", method = RequestMethod.POST)
    public void createChatRoom(@Param("chat_room_name") String chat_room_name) {
        UUID uuid = UUID.randomUUID();
        chatRoomService.createChatRoom(uuid.toString().replace("-", ""), chat_room_name);
    }

    @RequestMapping(value = "/inviteChatRoom", method = RequestMethod.POST)
    public void inviteChatRoom(@Param("client_ID") String client_ID, @Param("team_ID") String team_ID, @Param("chat_room_ID") String chat_room_ID) {
        chatRoomService.inviteChatRoom(client_ID, team_ID, chat_room_ID);
    }

    @RequestMapping(value = "/inviteChatRoom/send", method = RequestMethod.POST)
    public void inviteChatRoom(@Param("team_ID") String team_ID, @Param("chat_room_ID") String chat_room_ID, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String client_ID = session.getAttribute("client_ID").toString();
        chatRoomService.inviteChatRoom(client_ID, team_ID, chat_room_ID);
    }

    @RequestMapping(value = "/viewChatRooms", method = RequestMethod.GET) // 소속 채팅방 조회
    public List<ChatRoom> viewChatRooms(@Param("client_ID") String client_ID, @Param("team_ID") String team_ID) { return chatRoomService.viewChatRooms(client_ID, team_ID); }

    @RequestMapping(value = "/viewSchedules", method = RequestMethod.GET)
    public List<Schedule> viewSchedules(@Param("team_ID") String team_ID) {
        return teamService.viewSchedules(team_ID);
    }

    @RequestMapping(value = "/setSchedule", method = RequestMethod.POST)
    public void setSchedule(@Param("schedule_start_date") Date schedule_start_date,
                            @Param("schedule_end_date") Date schedule_end_date,
                            @Param("schedule_contents") String schedule_contents,
                            @Param("schedule_team_ID") String schedule_team_ID)
    {
        UUID uuid = UUID.randomUUID();
        teamService.setSchedule(uuid.toString().replace("-", ""), schedule_start_date, schedule_end_date, schedule_contents, schedule_team_ID);
    }

    @RequestMapping(value = "/deleteSchedule", method = RequestMethod.DELETE)
    public void deleteSchedule(@Param("schedule_ID") String schedule_ID) { teamService.deleteSchedule(schedule_ID); }
}
