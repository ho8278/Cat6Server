package com.catsix.systemprojectcatsix.Controller;

import com.catsix.systemprojectcatsix.fileIO.FileUploadResponse;
import com.catsix.systemprojectcatsix.model.*;
import com.catsix.systemprojectcatsix.service.ChatRoomService;
import com.catsix.systemprojectcatsix.service.ClientService;
import com.catsix.systemprojectcatsix.service.FileUploadDownloadService;
import com.catsix.systemprojectcatsix.service.TeamService;
import org.apache.catalina.Server;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class Controller {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    @Autowired
    private ClientService clientService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private FileUploadDownloadService fileService;
    @RequestMapping("/") // 테스트용
    public String home() {
        return "HOME";
    }

    @CrossOrigin
    @RequestMapping("/now") // 테스트용 (DB로부터 현재 시간 요청)
    public String now() throws Exception {
        return clientService.getCurrentDateTime();
    }

    @CrossOrigin
    @RequestMapping(value = "/login", method = RequestMethod.GET) // 로그인 (회원ID, 회원 비밀번호 받아서 로그인 성공 여부 반환)
    public ServerResponse<List<Client>> login(@Param("client_ID") String client_ID, @Param("client_password") String client_password, HttpServletRequest request) {
        ServerResponse<List<Client>> serverResponse = new ServerResponse<>();
        serverResponse.setData(clientService.login(client_ID, client_password));
        if(clientService.login(client_ID, client_password).isEmpty())
        {
            serverResponse.setResult(100);
        }
        if(serverResponse.getResult() == 200) {
            HttpSession session = request.getSession();
            session.setAttribute("client_ID", client_ID);
            System.out.println(session.getAttribute("client_ID"));
            serverResponse.setCookie(session.getId());
        }
        return serverResponse;
    }

    @CrossOrigin
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();

        return "로그아웃 되었습니다.";
    }

    @CrossOrigin
    @RequestMapping(value = "/showClientInfo", method = RequestMethod.GET) // 회원정보조회 (회원ID 받아서 해당회원정보조회)
    public ServerResponse<Client> showClientInfo(@Param("client_ID") String client_ID) {
        ServerResponse<Client> serverResponse = new ServerResponse<>();
        serverResponse.setData(clientService.showClientInfo(client_ID));
        return serverResponse;
    }

    @CrossOrigin
    @RequestMapping(value = "/showMyInfo", method = RequestMethod.GET)
    public ServerResponse<Client> showMyInfo(HttpServletRequest request) {
        ServerResponse<Client> serverResponse = new ServerResponse<>();
        HttpSession session = request.getSession();
        String client_ID = session.getAttribute("client_ID").toString();
        System.out.println(client_ID);
        serverResponse.setData(clientService.showMyInfo(client_ID));
        return serverResponse;
    }

    @CrossOrigin
    @RequestMapping(value = "/createClient", method = RequestMethod.POST) // 회원등록(가입) (가입에 필요한 회원정보 받아서 회원 테이블에 추가)
    public String createClient(@Param("client_ID") String client_ID, @Param("client_password") String client_password, @Param("client_name") String client_name, @Param("client_nickname") String client_nickname, @Param("profile_picture") String profile_picture) {
        if(clientService.showClientInfo(client_ID) == null) {
            clientService.createClient(client_ID, client_password, client_name, client_nickname, profile_picture);
            return "200";
        }
        else
            return "100";
    }

    @CrossOrigin
    @RequestMapping(value = "/deleteClient", method = RequestMethod.DELETE) // 회원삭제(탈퇴) (삭제할 회원ID 받아서 해당 회원 회원 테이블에서 삭제)
    public String deleteClient(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String client_ID = session.getAttribute("client_ID").toString();
        clientService.deleteClient(client_ID);
        return "200";
    }

    @CrossOrigin
    @RequestMapping(value = "/createTeam", method = RequestMethod.POST) // 그룹 생성
    public String createTeam(@Param("team_name") String team_name, HttpServletRequest request) {
        UUID uuid = UUID.randomUUID();
        HttpSession session = request.getSession();
        String c_ID = session.getAttribute("client_ID").toString();
        teamService.createTeam(uuid.toString().replace("-", ""), team_name);
        teamService.inviteTeam(c_ID, uuid.toString().replace("-", ""));
        System.out.println(c_ID);
        System.out.println(uuid.toString());
        return uuid.toString().replace("-", "");
    }

    @CrossOrigin
    @RequestMapping(value = "/deleteTeam", method = RequestMethod.DELETE) // 그룹 삭제
    public String deleteTeam(@Param("team_ID") String team_ID) {
        teamService.deleteTeam(team_ID);
        return "200";
    }

    @CrossOrigin
    @RequestMapping(value = "/inviteTeam", method = RequestMethod.POST) // 그룹 초대
    public String inviteTeam(@Param("client_ID") String client_ID, @Param("team_ID") String team_ID) {
        teamService.inviteTeam(client_ID, team_ID);
        return "200";
    }

    @CrossOrigin
    @RequestMapping(value = "/inviteTeam/send", method = RequestMethod.POST)
    public String inviteTeam(@Param("team_ID") String team_ID, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String client_ID = session.getAttribute("client_ID").toString();
        teamService.inviteTeam(client_ID, team_ID);
        return "200";
    }

    @CrossOrigin
    @RequestMapping(value = "/viewTeams", method = RequestMethod.GET) // 소속 그룹 조회
    public ServerResponse<List<Team>> viewTeams(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String client_ID = session.getAttribute("client_ID").toString();
        ServerResponse<List<Team>> serverResponse = new ServerResponse<>();
        serverResponse.setData(teamService.viewTeams(client_ID));
        if(teamService.viewTeams(client_ID).isEmpty())
        {
            serverResponse.setResult(100);
        }
        return serverResponse;
    }

    @CrossOrigin
    @RequestMapping(value = "/viewParticipateClients/team", method = RequestMethod.GET)
    public ServerResponse<List<Client>> viewParticipateClientsTeam(@Param("team_ID") String team_ID) {
        ServerResponse<List<Client>> serverResponse = new ServerResponse<>();
        serverResponse.setData(teamService.viewParticipateClients(team_ID));
        if(teamService.viewParticipateClients(team_ID).isEmpty())
        {
            serverResponse.setResult(100);
        }
        return serverResponse;
    }

    //@RequestMapping(value = "/viewTeams", method = RequestMethod.GET) // 소속 그룹 조회
    //public ServerResponse<List<Team>> viewTeams(@Param("client_ID") String client_ID) {
    //    ServerResponse<List<Team>> serverResponse = new ServerResponse<>();
    //    serverResponse.setData(teamService.viewTeams(client_ID));
    //    return serverResponse;
    //}

    @CrossOrigin
    @RequestMapping(value = "/createChatRoom", method = RequestMethod.POST)
    public String createChatRoom(@Param("chat_room_name") String chat_room_name, @Param("team_ID") String team_ID, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String client_ID = session.getAttribute("client_ID").toString();
        UUID uuid = UUID.randomUUID();
        chatRoomService.createChatRoom(uuid.toString().replace("-", ""), chat_room_name, team_ID);
        chatRoomService.inviteChatRoom(client_ID, uuid.toString().replace("-", ""));

        return uuid.toString().replace("-", "");
    }

    @CrossOrigin
    @RequestMapping(value = "/inviteChatRoom", method = RequestMethod.POST)
    public String inviteChatRoom(@Param("client_ID") String client_ID, @Param("chat_room_ID") String chat_room_ID) {
        chatRoomService.inviteChatRoom(client_ID, chat_room_ID);
        return "200";
    }

    @CrossOrigin
    @RequestMapping(value = "/inviteChatRoom/send", method = RequestMethod.POST)
    public String inviteChatRoom(@Param("chat_room_ID") String chat_room_ID, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String client_ID = session.getAttribute("client_ID").toString();
        chatRoomService.inviteChatRoom(client_ID, chat_room_ID);
        return "200";
    }

    @CrossOrigin
    @RequestMapping(value = "/viewChatRooms", method = RequestMethod.GET) // 소속 채팅방 조회
    public ServerResponse<List<ChatRoom>> viewChatRooms(@Param("team_ID") String team_ID, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String client_ID = session.getAttribute("client_ID").toString();
        ServerResponse<List<ChatRoom>> serverResponse = new ServerResponse<>();
        serverResponse.setData(chatRoomService.viewChatRooms(client_ID, team_ID));
        if(chatRoomService.viewChatRooms(client_ID, team_ID).isEmpty())
        {
            serverResponse.setResult(100);
        }
        return serverResponse;
    }

    @CrossOrigin
    @RequestMapping(value = "/viewParticipateClients/chat_room", method = RequestMethod.GET)
    public ServerResponse<List<Client>> viewParticipateClientsChatRoom(@Param("chat_room_ID") String chat_room_ID) {
        ServerResponse<List<Client>> serverResponse = new ServerResponse<>();
        serverResponse.setData(chatRoomService.viewParticipateClients(chat_room_ID));
        if(chatRoomService.viewParticipateClients(chat_room_ID).isEmpty())
        {
            serverResponse.setResult(100);
        }
        return serverResponse;
    }

    @CrossOrigin
    @RequestMapping(value = "/viewSchedules", method = RequestMethod.GET)
    public ServerResponse<List<Schedule>> viewSchedules(@Param("team_ID") String team_ID) {
        ServerResponse<List<Schedule>> serverResponse = new ServerResponse<>();
        serverResponse.setData(teamService.viewSchedules(team_ID));
        if(teamService.viewSchedules(team_ID).isEmpty())
        {
            serverResponse.setResult(100);
        }
        return serverResponse;
    }

    @CrossOrigin
    @RequestMapping(value = "/setSchedule", method = RequestMethod.POST)
    public String setSchedule(@Param("schedule_start_date") String schedule_start_date,
                            @Param("schedule_end_date") String schedule_end_date,
                            @Param("schedule_contents") String schedule_contents,
                            @Param("schedule_team_ID") String schedule_team_ID)
    {
        UUID uuid = UUID.randomUUID();
        teamService.setSchedule(uuid.toString().replace("-", ""), schedule_start_date, schedule_end_date, schedule_contents, schedule_team_ID);
        return uuid.toString().replace("-", "");
    }

    @CrossOrigin
    @RequestMapping(value = "/deleteSchedule", method = RequestMethod.DELETE)
    public String deleteSchedule(@Param("schedule_ID") String schedule_ID) {
        teamService.deleteSchedule(schedule_ID);
        return "200";
    }

    @CrossOrigin
    @PostMapping("/uploadFile")
    public FileUploadResponse uploadFile(@RequestParam("file") MultipartFile file, @Param("team_ID") String team_ID) {
        String fileName = fileService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        String file_name = fileName;
        teamService.registFile(file_name, team_ID);

        return new FileUploadResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    //@PostMapping("/uploadMultipleFiles") // 다중 업로드
    //public List<FileUploadResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, String team_ID){
    //    return Arrays.asList(files)
    //            .stream()
    //            .map(file -> uploadFile(file, team_ID))
    //            .collect(Collectors.toList());
    //}

    @CrossOrigin
    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request){
        // Load file as Resource
        Resource resource = fileService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @CrossOrigin
    @RequestMapping(value = "/viewFiles", method = RequestMethod.GET)
    public ServerResponse<List<File>> viewFiles(@Param("team_ID") String team_ID) {
        ServerResponse<List<File>> serverResponse = new ServerResponse<>();
        serverResponse.setData(teamService.viewFiles(team_ID));
        return serverResponse;
    }

    @CrossOrigin
    @RequestMapping(value = "/updateClient", method = RequestMethod.POST) // 회원수정(로그인후 아이디제외 수정가능)
    public String updateClient(@Param("client_password") String client_password, @Param("client_name") String client_name, @Param("client_nickname") String client_nickname, @Param("profile_picture") String profile_picture, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String client_ID = session.getAttribute("client_ID").toString();
        clientService.updateClient(client_ID, client_password, client_name, client_nickname, profile_picture);
        return "200";
    }


    @CrossOrigin
    @RequestMapping(value = "/notice", method = RequestMethod.POST) //공지 등록
    public String notice(@Param("notice_contents") String notice_contents, @Param("chat_room_ID") String chat_room_ID) {
        chatRoomService.deleteNotice(chat_room_ID);
        UUID uuid = UUID.randomUUID();
        chatRoomService.notice(uuid.toString().replace("-", ""), notice_contents, chat_room_ID);
        return "200";
    }

    @CrossOrigin
    @RequestMapping(value = "/viewNotice", method = RequestMethod.GET) //공지 보기
    public ServerResponse<List<Notice>> viewNotice(@Param("chat_room_ID") String chat_room_ID) {
        ServerResponse<List<Notice>> serverResponse = new ServerResponse<>();
        serverResponse.setData(chatRoomService.viewNotice(chat_room_ID));
        return serverResponse;
    }

    @CrossOrigin
    @RequestMapping(value = "/deleteNotice", method = RequestMethod.DELETE)
    public String deleteNotice(@Param("chat_room_ID") String chat_room_ID) {
        chatRoomService.deleteNotice(chat_room_ID);
        return "200";
    }
}
