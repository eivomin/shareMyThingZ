package com.hanghae.sharemythingz;

import com.hanghae.sharemythingz.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        public void dbInit1() {

            Board board1 = createBoard("movie");
            Board board2 = createBoard("music");
            Board board3 = createBoard("ani");
            Board board4 = createBoard("health");
            Board board5 = createBoard("book");

            em.persist(board1);
            em.persist(board2);
            em.persist(board3);
            em.persist(board4);
            em.persist(board5);

        }

        private Board createBoard(String name) {
            Board board = new Board();
            board.setBoard_name(name);
            return board;
        }
    }
}
