package study.datajpa.dto;

import lombok.Data;
import study.datajpa.entity.Member;

@Data //엔티티에는 왠만하면 @Data쓰면 안됨. @Getter @Setter로 나눠쓸것
public class MemberDto {

    private Long id;
    private String username;
    private String teamName;

    public MemberDto(Long id, String username, String teamName) {
        this.id = id;
        this.username = username;
        this.teamName = teamName;
    }

    public MemberDto(Member member){
        this.id = member.getId();
        this.username = member.getUsername();
    }


}
