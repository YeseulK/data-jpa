package study.datajpa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name"})
public class Team {

    @Id @GeneratedValue
    @Column(name = "team_id")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team") //서로 매핑된 경우 mappedBy는 FK가 없는 쪽에 걸어주는 것이 좋다.
    List<Member> members = new ArrayList<>();

    public Team(String name) {
        this.name = name;
    }
}