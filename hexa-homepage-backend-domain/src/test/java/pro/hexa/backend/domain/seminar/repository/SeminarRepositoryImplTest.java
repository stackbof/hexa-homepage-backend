package pro.hexa.backend.domain.seminar.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pro.hexa.backend.domain.attachment.domain.Attachment;
import pro.hexa.backend.domain.attachment.domain.QAttachment;
import pro.hexa.backend.domain.seminar.domain.QSeminar;
import pro.hexa.backend.domain.seminar.domain.Seminar;
import pro.hexa.backend.domain.user.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SeminarRepositoryImplTest {

    @Mock
    private JPAQueryFactory queryFactory;

    @InjectMocks
    private SeminarRepositoryImpl seminarRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllByQuery() {
        // 가상의 데이터 생성
        String searchText = "test";
        Integer year = 2023;
        Integer pageNum = 0;
        Integer pageSize = 10;

        // 가상의 세미나 목록 생성
        List<Seminar> mockSeminarList = createMockSeminarList();

        // QueryDSL에서 사용할 가상의 QSeminar 객체 생성
        QSeminar qSeminar = QSeminar.seminar;

        // QueryDSL가 findAllByQuery 메서드 호출 시 예상되는 결과를 설정
        JPAQuery<Seminar> jpaQuery = mock(JPAQuery.class);
        when(queryFactory.selectFrom(qSeminar)).thenReturn(jpaQuery);
        when(jpaQuery.leftJoin(qSeminar.attachments, QAttachment.attachment)).thenReturn(jpaQuery);
        when(jpaQuery.where(any(Predicate.class))).thenReturn(jpaQuery);
        when(jpaQuery.offset(anyInt())).thenReturn(jpaQuery);
        when(jpaQuery.limit(anyInt())).thenReturn(jpaQuery);
        when(jpaQuery.fetch()).thenReturn(mockSeminarList);

        // findAllByQuery 메서드 호출
        List<Seminar> result = seminarRepository.findAllByQuery(searchText, year, pageNum, pageSize);

        // 결과 검증
        assertNotNull(result);
        assertEquals(2, result.size()); // 예상되는 페이지 크기와 일치하는지 확인
    }

    // 가상의 세미나 목록 생성 (테스트에 사용할 데이터를 가정하여 생성)
    private List<Seminar> createMockSeminarList() {
        List<Seminar> seminars = new ArrayList<>();

        // 가상의 세미나 객체들 생성하여 리스트에 추가
        Seminar seminar1 = new Seminar();
        seminar1.setDate(LocalDateTime.of(2023, 8, 1, 10, 0));
        seminars.add(seminar1);

        Seminar seminar2 = new Seminar();
        seminar2.setDate(LocalDateTime.of(2023, 8, 5, 14, 0));
        seminars.add(seminar2);

        return seminars;
    }
}