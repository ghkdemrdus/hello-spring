package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    //    private  final MemberRepository memberRepository = new MemoryMemberRepository(); //test 케이스에서 다른 인스턴트가 생성되어 테스트하게됨
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) { //DI 임
        this.memberRepository = memberRepository;
    }
    /**
     * 회원 가입
     */
    public Long join(Member member) {
        // 중복 회원이 있으면 안됨
//        Optional<Member> result = memberRepository.findByName(member.getName());
//        result.ifPresent(m -> { //Optional 로 감쌌기 때문에 다양한 method를 사용할수 있음
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        });
        validateEuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateEuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMember() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
