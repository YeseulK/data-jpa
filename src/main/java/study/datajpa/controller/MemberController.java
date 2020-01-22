package study.datajpa.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;

    // 도메인 클래스 컨버터 사용 전
    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    // 도메인 클래스 컨버터 사용 후
    @GetMapping("/members2/{id}")
    public String findMember2(@PathVariable("id") Member member) {
        return member.getUsername();
    }

    //페이징과 정렬
    @GetMapping("/members")
    // public Page<Member> list(@PageableDefault(size=5) Pageable pageable) {
    public Page<MemberDto> list(Pageable pageable) {

        //page.map을 통해 <Member> 내부 변경 가능, <Member> list -> <MemberDto> list 변경
        return memberRepository.findAll(pageable)
                .map(MemberDto::new);

        /**  "cmd + option + n" = 인라인 (코드 깔끔)
         Page<Member> page = memberRepository.findAll(pageable);
         Page<MemberDto> map = page.map(m -> new MemberDto(m.getId(), m.getUsername(),null));
         return map;
          */
    }


    @PostConstruct
    public void init(){
        for (int i=0; i<100; i++){
            memberRepository.save(new Member("user"+ i,i));
        }
    }

}