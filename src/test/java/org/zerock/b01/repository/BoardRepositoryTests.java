package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.b01.domain.Board;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2

//runwith같은거 안넣고 springboottest 하나 넣으면 됨
class BoardRepositoryTests {
    @Autowired
    private BoardRepository boardRepository;

    @Test
     public void testInsert(){
                IntStream.rangeClosed(1,100).forEach(i->{
                    Board board = Board.builder().
                    title("title......" + i).
                    content("content...." + i).
                    writer("user"+(i%10)).
                    build(); // application.properties에 spring.jpa.show-sql=true 이게 있어야 콘솔에서 db 들어가는게 보여줌.

            Board result = boardRepository.save(board);
            log.info("bno  : " + result.getBno());
        });
    }


    @Test
    public void testRead() {
        Long bno = 3L;

        Optional<Board> byId = boardRepository.findById(bno);

        Board board = byId.orElseThrow();
        log.info(board);
    }

    @Test
    public void testTitle() {
        String title = "title......70";
        Board byTitle = boardRepository.findByTitle(title);
        log.info(byTitle);
    }

    @Test
    public void testPaging() {
        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());
        Page<Board> all = boardRepository.findAll(pageable);

        all.getContent().forEach(content -> System.out.println(content));
    }



}