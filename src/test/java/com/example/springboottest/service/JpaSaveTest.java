package com.example.springboottest.service;

import com.example.springboottest.domain.Member;
import com.example.springboottest.domain.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JpaSaveTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void initial_save_merge_test() {
        Member member = new Member();
        member.setId("M-1"); // @Id not null
        member.setName("andy");
        memberRepository.save(member); // select -> insert
    }

    @Test
    void update_save_merge_test() {
        String id = "M-1";
        Member newMember = getNewMember(id, "danny"); // @Id not null

        memberRepository.findById(id).ifPresent(member -> {
            newMember.setName(member.getName());
        });

        memberRepository.save(newMember); // select
    }

    @Test
    void update_save_merge_test_2() {
        String id = "M-1";
        Member newMember = getNewMember(id, "danny"); // @Id not null

        memberRepository.save(newMember); // select -> update
    }

    public Member getNewMember(String id, String name) {
        Member member = new Member();
        member.setId(id);
        member.setName(name);
        return member;
    }
}
